package com.raizesnordeste.project_backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_auditoria")
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String acao;

    @Column(nullable = false)
    private String entidade;

    @Column(name = "entidade_id")
    private Long entidadeId;

    @Column(nullable = false)
    private String usuario;

    @Column
    private String detalhes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public LogAuditoria() {}

    public LogAuditoria(String acao, String entidade, Long entidadeId, String usuario, String detalhes) {
        this.acao = acao;
        this.entidade = entidade;
        this.entidadeId = entidadeId;
        this.usuario = usuario;
        this.detalhes = detalhes;
    }

    public Long getId() { return id; }
    public String getAcao() { return acao; }
    public String getEntidade() { return entidade; }
    public Long getEntidadeId() { return entidadeId; }
    public String getUsuario() { return usuario; }
    public String getDetalhes() { return detalhes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
