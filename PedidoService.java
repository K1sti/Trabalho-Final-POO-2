import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obterPedido(long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizarPedido(long id, Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(id).orElse(null);
        if (pedidoExistente != null) {
            pedido.setId(id);
            return pedidoRepository.save(pedido);
        } else {
            return null;
        }
    }

    public boolean deletarPedido(long id) {
        Pedido pedidoExistente = pedidoRepository.findById(id).orElse(null);
        if (pedidoExistente != null) {
            pedidoRepository.delete(pedidoExistente);
            return true;
        } else {
            return false;
        }
    }
}
