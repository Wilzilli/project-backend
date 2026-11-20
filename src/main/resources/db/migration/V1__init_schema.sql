-- Criação da tabela de Usuários (com roles e LGPD)
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL, -- CLIENTE, ATENDENTE, GERENTE, ADMIN
    consentimento_lgpd BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação da tabela de Unidades/Franquias da rede
CREATE TABLE unidades (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    ativa BOOLEAN DEFAULT TRUE
);

-- Criação da tabela de Produtos do Cardápio
CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco_base DECIMAL(10,2) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

-- Controle de Estoque por Unidade (Relação N:M)
CREATE TABLE estoques (
    id SERIAL PRIMARY KEY,
    unidade_id INT NOT NULL REFERENCES unidades(id),
    produto_id INT NOT NULL REFERENCES produtos(id),
    quantidade_disponivel INT NOT NULL DEFAULT 0,
    CONSTRAINT uk_unidade_produto UNIQUE (unidade_id, produto_id)
);

-- Tabela principal de pedidos e seus dados comerciais.
CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL REFERENCES usuarios(id),
    unidade_id INT NOT NULL REFERENCES unidades(id),
    canal_pedido VARCHAR(50) NOT NULL, -- APP, TOTEM, BALCAO, PICKUP, WEB
    status VARCHAR(50) NOT NULL, -- AGUARDANDO_PAGAMENTO, PAGO, COZINHA, PRONTO, ENTREGUE, CANCELADO
    valor_total DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Itens do Pedido
CREATE TABLE itens_pedido (
    id SERIAL PRIMARY KEY,
    pedido_id INT NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    produto_id INT NOT NULL REFERENCES produtos(id),
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL
);

-- Tabela de Pagamento (Mock desacoplado)
CREATE TABLE pagamentos (
    id SERIAL PRIMARY KEY,
    pedido_id INT NOT NULL REFERENCES pedidos(id),
    forma_pagamento VARCHAR(50) NOT NULL, -- PIX, CREDITO, MOCK
    status_transacao VARCHAR(50) NOT NULL, -- APROVADO, RECUSADO
    gateway_payload_retorno TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);