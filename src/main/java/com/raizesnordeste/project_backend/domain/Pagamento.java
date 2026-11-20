package com.raizesnordeste.project_backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "forma_pagamento", nullable = false)
    private String formaPagamento;

    @Column(name = "status_transacao", nullable = false)
    private String statusTransacao;

    @Column(name = "gateway_payload_retorno")
    private String gatewayPayloadRetorno;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Pagamento() {}

    public Long getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public String getStatusTransacao() { return statusTransacao; }
    public void setStatusTransacao(String statusTransacao) { this.statusTransacao = statusTransacao; }
    public String getGatewayPayloadRetorno() { return gatewayPayloadRetorno; }
    public void setGatewayPayloadRetorno(String gatewayPayloadRetorno) { this.gatewayPayloadRetorno = gatewayPayloadRetorno; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
