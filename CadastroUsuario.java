import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO) {
        // Validação do payload
        if (userDTO.getNome() == null || userDTO.getEmail() == null) {
            return ResponseEntity.badRequest().body("Nome e email são obrigatórios.");
        }

        // Verifica se o email já está cadastrado
        if (userService.emailExists(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("O email fornecido já está em uso.");
        }

        // Criação do usuário
        User user = new User();
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());
        String senhaGerada = generateRandomPassword();
        user.setSenha(senhaGerada);

        // Salva o usuário no banco de dados ou realiza alguma ação desejada
        userService.createUser(user);

        return ResponseEntity.ok(senhaGerada);
    }

    private String generateRandomPassword() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();

    int length = 8; // Defina o comprimento da senha conforme necessário

    for (int i = 0; i < length; i++) {
        int index = random.nextInt(characters.length());
        sb.append(characters.charAt(index));
    }

    return sb.toString();
}

}
