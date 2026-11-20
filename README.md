# API Raízes do Nordeste

API REST desenvolvida com Java 17 e Spring Boot para gerenciamento de pedidos e simulação de pagamentos em ambiente multicanal.

## Tecnologias

- Java 17
- Spring Boot 4.1.2
- Spring Data JPA / Hibernate
- PostgreSQL 15
- Flyway
- Spring Security com Basic Auth
- Bean Validation
- Springdoc OpenAPI / Swagger UI
- Maven
- Docker Compose

## Pré-requisitos

- Java 17
- Maven, ou o wrapper `mvnw`
- Docker Desktop

## Banco de dados

O PostgreSQL pode ser iniciado com:

```powershell
docker compose up -d
```

O serviço utiliza o banco `raizes_db` na porta `5432`. As configurações padrão estão disponíveis em `.env.example`.

Para iniciar a aplicação:

```powershell
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Documentação

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

Documento OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

## Documentação acadêmica

- Requisitos funcionais e não funcionais: `docs/requisitos.md`
- LGPD, segurança e auditoria: `docs/lgpd-seguranca.md`
- Plano de testes: `docs/plano-de-testes.md`
- Diagramas: `docs/diagramas.md`

## Autenticação

A API utiliza autenticação HTTP Basic. Usuários disponíveis para testes:

| Usuário | Senha | Perfil |
|---|---|---|
| admin | 123456 | ADMIN |
| gerente | 123456 | GERENTE |
| atendente | 123456 | ATENDENTE |
| cliente | 123456 | CLIENTE |

As credenciais acima são destinadas ao ambiente de desenvolvimento e devem ser substituídas em produção.

## Perfis de acesso

- `CLIENTE`: cria pedidos e consulta pedidos.
- `ATENDENTE`: consulta pedidos e processa pagamentos.
- `GERENTE`: consulta pedidos e processa pagamentos.
- `ADMIN`: possui acesso administrativo aos recursos configurados.

## Endpoints principais

### Pedidos

Criar pedido:

```text
POST /pedidos
```

```json
{
  "clienteId": 1,
  "unidadeId": 1,
  "canalPedido": "APP",
  "valorTotal": 89.90
}
```

Canais aceitos:

- `APP`
- `TOTEM`
- `BALCAO`
- `PICKUP`
- `WEB`

Consultar pedidos:

```text
GET /pedidos
```

Filtrar por canal:

```text
GET /pedidos?canalPedido=APP
```

### Pagamentos

Processar pagamento mock:

```text
POST /pedidos/{pedidoId}/pagamentos
```

```json
{
  "formaPagamento": "PIX",
  "forcarResultado": "APROVADO"
}
```

Formas aceitas:

- `PIX`
- `CREDITO`
- `MOCK`

Resultados simulados:

- `APROVADO`
- `RECUSADO`

Consultar pagamentos de um pedido:

```text
GET /pedidos/{pedidoId}/pagamentos
```

### Auditoria

Consultar registros de ações críticas:

```text
GET /auditoria
```

Disponível apenas para o perfil `ADMIN`. Os registros incluem ação, entidade, identificador, usuário e data/hora.

## Status HTTP

- `200 OK`: consulta realizada com sucesso.
- `201 Created`: pedido ou pagamento criado.
- `400 Bad Request`: dados ou parâmetros inválidos.
- `401 Unauthorized`: credenciais ausentes ou inválidas.
- `403 Forbidden`: usuário sem autorização para a operação.
- `404 Not Found`: pedido não encontrado.
- `409 Conflict`: pedido já foi finalizado ou operação incompatível.

## Migrações

O Flyway aplica os scripts versionados presentes em:

```text
src/main/resources/db/migration/
```

## Testes no Postman

A tabela formal dos cenários está em:

```text
docs/plano-de-testes.md
```

A coleção exportada e as evidências estão em:

```text
docs/PRINT de teste do trabalho/
```

O plano contém 10 cenários:

1. Criar pedido válido.
2. Consultar todos os pedidos.
3. Filtrar pedidos por canal.
4. Processar pagamento aprovado.
5. Consultar pagamentos de um pedido.
6. Consultar pedidos com perfil cliente.
7. Criar pedido com canal inválido.
8. Criar pedido com valor inválido.
9. Cliente tentar processar pagamento.
10. Tentar processar novamente um pedido já pago.

Cada evidência deve mostrar o método, URL, autenticação, entrada, resposta e código HTTP esperado.

## Executar testes automatizados

```powershell
./mvnw test
```

## Empacotar a aplicação

```powershell
./mvnw clean package
```

O arquivo executável será gerado em `target/`.

## Segurança e configurações

Não utilizar senhas reais em arquivos versionados. Para diferentes ambientes, utilize variáveis de ambiente ou um gerenciador de segredos.

Exemplo:

```text
DB_HOST=localhost
DB_PORT=5432
DB_NAME=raizes_db
DB_USER=postgres
DB_PASSWORD=sua_senha
```

## Finalização do trabalho

- Conclusão: `docs/conclusao.md`
- Referências: `docs/referencias.md`
- Checklist da entrega: `docs/checklist-entrega.md`
