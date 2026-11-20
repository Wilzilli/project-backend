package com.raizesnordeste.project_backend.api;

import com.raizesnordeste.project_backend.application.PagamentoRequest;
import com.raizesnordeste.project_backend.application.PagamentoService;
import com.raizesnordeste.project_backend.domain.Pagamento;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/pedidos/{pedidoId}/pagamentos")
@Tag(name = "Pagamentos", description = "Processamento e consulta de pagamentos mock")
@SecurityRequirement(name = "basicAuth")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<Pagamento> processar(
        @PathVariable Long pedidoId,
        @RequestBody @Valid PagamentoRequest request,
        Principal principal
    ) {
        Pagamento pagamento = pagamentoService.processar(pedidoId, request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @GetMapping
    public List<Pagamento> listar(@PathVariable Long pedidoId) {
        return pagamentoService.listarPorPedido(pedidoId);
    }
}
