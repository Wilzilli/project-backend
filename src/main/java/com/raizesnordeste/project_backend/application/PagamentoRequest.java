package com.raizesnordeste.project_backend.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PagamentoRequest(
    @NotBlank(message = "formaPagamento é obrigatório")
    @Pattern(regexp = "PIX|CREDITO|MOCK", message = "formaPagamento deve ser PIX, CREDITO ou MOCK")
    String formaPagamento,

    @Pattern(regexp = "APROVADO|RECUSADO", message = "forcarResultado deve ser APROVADO ou RECUSADO")
    String forcarResultado
) {}
