package com.raizesnordeste.project_backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "clienteId é obrigatório")
    @Positive(message = "clienteId deve ser positivo")
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @NotNull(message = "unidadeId é obrigatório")
    @Positive(message = "unidadeId deve ser positivo")
    @Column(name = "unidade_id", nullable = false)
    private Long unidadeId;

    @NotBlank(message = "canalPedido é obrigatório")
    @Pattern(regexp = "APP|TOTEM|BALCAO|PICKUP|WEB", message = "canalPedido deve ser APP, TOTEM, BALCAO, PICKUP ou WEB")
    @Column(name = "canal_pedido", nullable = false)
    private String canalPedido;

    @Column(nullable = false)
    private String status;

    @NotNull(message = "valorTotal é obrigatório")
    @Positive(message = "valorTotal deve ser maior que zero")
    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Pedido() {}

    public Pedido(Long clienteId, Long unidadeId, String canalPedido, String status, Double valorTotal) {
        this.clienteId = clienteId;
        this.unidadeId = unidadeId;
        this.canalPedido = canalPedido;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getUnidadeId() { return unidadeId; }
    public void setUnidadeId(Long unidadeId) { this.unidadeId = unidadeId; }

    public String getCanalPedido() { return canalPedido; }
    public void setCanalPedido(String canalPedido) { this.canalPedido = canalPedido; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}