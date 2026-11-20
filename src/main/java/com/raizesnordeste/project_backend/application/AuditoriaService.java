package com.raizesnordeste.project_backend.application;

import com.raizesnordeste.project_backend.domain.LogAuditoria;
import com.raizesnordeste.project_backend.infrastructure.LogAuditoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaService {

    private final LogAuditoriaRepository repository;

    public AuditoriaService(LogAuditoriaRepository repository) {
        this.repository = repository;
    }

    public void registrar(String acao, String entidade, Long entidadeId, String usuario, String detalhes) {
        repository.save(new LogAuditoria(acao, entidade, entidadeId, usuario, detalhes));
    }

    public List<LogAuditoria> listar() {
        return repository.findAll();
    }
}
