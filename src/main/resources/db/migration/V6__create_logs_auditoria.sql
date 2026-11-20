CREATE TABLE logs_auditoria (
    id BIGSERIAL PRIMARY KEY,
    acao VARCHAR(50) NOT NULL,
    entidade VARCHAR(50) NOT NULL,
    entidade_id BIGINT,
    usuario VARCHAR(100) NOT NULL,
    detalhes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
