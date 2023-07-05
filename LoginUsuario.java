import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserDTO userDTO) {
        // Validação do payload
        if (userDTO.getEmail() == null || userDTO.getSenha() == null) {
            return ResponseEntity.badRequest().body("Email e senha são obrigatórios.");
        }

        // Verifica se as credenciais do usuário são válidas
        if (!userService.isValidUser(userDTO.getEmail(), userDTO.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }

        // Geração do token JWT
        String token = generateJWTToken(userDTO.getEmail());

        return ResponseEntity.ok(token);
    }

    private String generateJWTToken(String email) {
        String secretKey = "seu_secret_key"; // Chave secreta para assinar o token
        long expirationTime = 86400000; // Tempo de expiração do token (24 horas)

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationTime);

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }
}
