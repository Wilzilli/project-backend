package com.raizesnordeste.project_backend.application;

import com.raizesnordeste.project_backend.domain.Pagamento;
import com.raizesnordeste.project_backend.domain.Pedido;
import com.raizesnordeste.project_backend.infrastructure.PagamentoRepository;
import com.raizesnordeste.project_backend.infrastructure.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
public class PagamentoService {

    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final AuditoriaService auditoriaService;

    public PagamentoService(PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository, AuditoriaService auditoriaService) {
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public Pagamento processar(Long pedidoId, PagamentoRequest request, String usuario) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if ("PAGO".equals(pedido.getStatus()) || "CANCELADO".equals(pedido.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pedido já foi finalizado");
        }

        String forma = request.formaPagamento() == null ? "MOCK" : request.formaPagamento().toUpperCase(Locale.ROOT);
        if (!List.of("PIX", "CREDITO", "MOCK").contains(forma)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forma de pagamento inválida");
        }

        String resultado = request.forcarResultado() == null
            ? "APROVADO"
            : request.forcarResultado().toUpperCase(Locale.ROOT);

        if (!List.of("APROVADO", "RECUSADO").contains(resultado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resultado do mock inválido");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(forma);
        pagamento.setStatusTransacao(resultado);
        pagamento.setGatewayPayloadRetorno("{\"mock\":true,\"resultado\":\"" + resultado + "\"}");

        if ("APROVADO".equals(resultado)) {
            pedido.setStatus("PAGO");
        } else {
            pedido.setStatus("AGUARDANDO_PAGAMENTO");
        }

        pedidoRepository.save(pedido);
        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        auditoriaService.registrar(
            "PROCESSAR_PAGAMENTO",
            "PAGAMENTO",
            pagamentoSalvo.getId(),
            usuario,
            "Pedido=" + pedidoId + "; Forma=" + forma + "; Resultado=" + resultado
        );
        return pagamentoSalvo;
    }

    public List<Pagamento> listarPorPedido(Long pedidoId) {
        if (!pedidoRepository.existsById(pedidoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }
        return pagamentoRepository.findByPedidoId(pedidoId);
    }
}
