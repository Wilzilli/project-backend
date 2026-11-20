package com.raizesnordeste.project_backend.api;

import com.raizesnordeste.project_backend.domain.Pedido;
import com.raizesnordeste.project_backend.infrastructure.PedidoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Criação e consulta de pedidos")
@SecurityRequirement(name = "basicAuth")
public class PedidoController {

    private static final Set<String> CANAIS = Set.of("APP", "TOTEM", "BALCAO", "PICKUP", "WEB");
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody @Valid Pedido pedido) {
        // Todo pedido criado começa aguardando o pagamento.
        pedido.setStatus("AGUARDANDO_PAGAMENTO");
        Pedido novoPedido = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @GetMapping
    public List<Pedido> listarPedidos(@RequestParam(required = false) String canalPedido) {
        if (canalPedido != null && !canalPedido.isEmpty()) {
            // Normaliza o filtro para evitar diferenças entre letras maiúsculas e minúsculas.
            String canal = canalPedido.toUpperCase(Locale.ROOT);
            if (!CANAIS.contains(canal)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Canal de pedido inválido");
            }
            return pedidoRepository.findByCanalPedido(canal);
        }
        return pedidoRepository.findAll();
    }
}