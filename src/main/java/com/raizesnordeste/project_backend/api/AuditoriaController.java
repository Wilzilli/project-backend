package com.raizesnordeste.project_backend.api;

import com.raizesnordeste.project_backend.application.AuditoriaService;
import com.raizesnordeste.project_backend.domain.LogAuditoria;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
@Tag(name = "Auditoria", description = "Consulta dos registros de ações críticas")
@SecurityRequirement(name = "basicAuth")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    public List<LogAuditoria> listar() {
        return auditoriaService.listar();
    }
}
