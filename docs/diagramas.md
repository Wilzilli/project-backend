# Diagramas do Sistema

## Diagrama de casos de uso

```mermaid
flowchart LR
    Cliente((Cliente))
    Atendente((Atendente))
    Gerente((Gerente))

    CriarPedido([Criar pedido])
    ConsultarPedidos([Consultar pedidos])
    FiltrarCanal([Filtrar pedidos por canal])
    ProcessarPagamento([Processar pagamento mock])
    ConsultarPagamentos([Consultar pagamentos])

    Cliente --> CriarPedido
    Cliente --> ConsultarPedidos
    Cliente --> FiltrarCanal

    Atendente --> ConsultarPedidos
    Atendente --> FiltrarCanal
    Atendente --> ProcessarPagamento
    Atendente --> ConsultarPagamentos

    Gerente --> ConsultarPedidos
    Gerente --> FiltrarCanal
    Gerente --> ProcessarPagamento
    Gerente --> ConsultarPagamentos
```

## Diagrama de sequência: pagamento aprovado

```mermaid
sequenceDiagram
    actor Atendente
    participant API as Pedido/Pagamento Controller
    participant Service as PagamentoService
    participant PedidoRepo as PedidoRepository
    participant PagamentoRepo as PagamentoRepository
    participant Banco as PostgreSQL

    Atendente->>API: POST /pedidos/{id}/pagamentos
    API->>Service: processar(id, dados)
    Service->>PedidoRepo: findById(id)
    PedidoRepo->>Banco: SELECT pedido
    Banco-->>PedidoRepo: pedido encontrado
    PedidoRepo-->>Service: Pedido

    Service->>PedidoRepo: save(pedido PAGO)
    PedidoRepo->>Banco: UPDATE pedido
    Service->>PagamentoRepo: save(pagamento)
    PagamentoRepo->>Banco: INSERT pagamento
    Banco-->>PagamentoRepo: pagamento registrado
    PagamentoRepo-->>Service: Pagamento
    Service-->>API: Pagamento
    API-->>Atendente: 201 Created
```

## Diagrama de sequência: pagamento recusado

```mermaid
sequenceDiagram
    actor Atendente
    participant API as PagamentoController
    participant Service as PagamentoService
    participant Repositorio as PagamentoRepository
    participant Banco as PostgreSQL

    Atendente->>API: POST com forcarResultado RECUSADO
    API->>Service: processar pedido
    Service->>Repositorio: salvar pagamento recusado
    Repositorio->>Banco: INSERT pagamento
    Banco-->>Repositorio: registro criado
    Repositorio-->>Service: Pagamento
    Service-->>API: Pagamento
    API-->>Atendente: 201 Created
```

O pedido permanece em `AGUARDANDO_PAGAMENTO` quando o resultado do mock é recusado.

## Diagrama de classes

```mermaid
classDiagram
    class Pedido {
        +Long id
        +Long clienteId
        +Long unidadeId
        +String canalPedido
        +String status
        +Double valorTotal
        +LocalDateTime createdAt
    }

    class Pagamento {
        +Long id
        +Pedido pedido
        +String formaPagamento
        +String statusTransacao
        +String gatewayPayloadRetorno
        +LocalDateTime createdAt
    }

    class PedidoController {
        +criarPedido(Pedido) ResponseEntity
        +listarPedidos(String) List
    }

    class PagamentoController {
        +processar(Long, PagamentoRequest) ResponseEntity
        +listar(Long) List
    }

    class PagamentoService {
        +processar(Long, PagamentoRequest) Pagamento
        +listarPorPedido(Long) List
    }

    class PedidoRepository
    class PagamentoRepository

    Pagamento "1" --> "1" Pedido
    PedidoController --> PedidoRepository
    PagamentoController --> PagamentoService
    PagamentoService --> PedidoRepository
    PagamentoService --> PagamentoRepository
    PagamentoRepository --> Pagamento
    PedidoRepository --> Pedido
```

## Diagrama entidade-relacionamento simplificado

```mermaid
erDiagram
    USUARIOS ||--o{ PEDIDOS : realiza
    UNIDADES ||--o{ PEDIDOS : recebe
    PEDIDOS ||--o{ PAGAMENTOS : possui
    UNIDADES ||--o{ ESTOQUES : controla
    PRODUTOS ||--o{ ESTOQUES : esta_em
    PEDIDOS ||--o{ ITENS_PEDIDO : contem
    PRODUTOS ||--o{ ITENS_PEDIDO : referencia

    USUARIOS {
        bigint id PK
        varchar nome
        varchar email UK
        varchar senha_hash
        varchar perfil
        boolean consentimento_lgpd
        timestamp created_at
    }

    UNIDADES {
        bigint id PK
        varchar nome
        varchar cidade
        boolean ativa
    }

    PRODUTOS {
        bigint id PK
        varchar nome
        text descricao
        decimal preco_base
        boolean ativo
    }

    PEDIDOS {
        bigint id PK
        bigint cliente_id FK
        bigint unidade_id FK
        varchar canal_pedido
        varchar status
        double valor_total
        timestamp created_at
    }

    PAGAMENTOS {
        bigint id PK
        bigint pedido_id FK
        varchar forma_pagamento
        varchar status_transacao
        text gateway_payload_retorno
        timestamp created_at
    }

    LOGS_AUDITORIA {
        bigint id PK
        varchar acao
        varchar entidade
        bigint entidade_id
        varchar usuario
        text detalhes
        timestamp created_at
    }

    ITENS_PEDIDO {
        bigint id PK
        bigint pedido_id FK
        bigint produto_id FK
        int quantidade
        decimal preco_unitario
    }

    ESTOQUES {
        bigint id PK
        bigint unidade_id FK
        bigint produto_id FK
        int quantidade_disponivel
    }
```
