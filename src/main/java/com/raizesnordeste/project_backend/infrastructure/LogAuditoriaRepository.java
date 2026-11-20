package com.raizesnordeste.project_backend.infrastructure;

import com.raizesnordeste.project_backend.domain.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {
}
