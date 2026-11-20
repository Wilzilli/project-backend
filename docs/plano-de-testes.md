# Plano de Testes da API

## Ambiente de teste

- API: `http://localhost:8080`
- Banco de dados: PostgreSQL 15
- Autenticação: HTTP Basic Auth
- Documentação: Swagger/OpenAPI
- Coleção exportada: `Raizes do Nordeste - Plano de Testes.postman_collection.json`
- Evidências: capturas realizadas no Postman

## Usuários utilizados

| Usuário | Senha | Perfil |
|---|---|---|
| atendente | 123456 | ATENDENTE |
| cliente | 123456 | CLIENTE |

## Casos de teste

| ID | Objetivo | Pré-condição | Método e URL | Entrada | Resultado esperado | Evidência |
|---|---|---|---|---|---|---|
| CT01 | Criar um pedido válido | API em execução, cliente e unidade existentes e usuário autenticado | `POST /pedidos` | `clienteId: 1`, `unidadeId: 1`, `canalPedido: "APP"`, `valorTotal: 89.90` | `201 Created`; pedido criado com status `AGUARDANDO_PAGAMENTO` | `PRINT de teste do trabalho/CT01 - Criar pedido válido - status 200.png` |
| CT02 | Consultar todos os pedidos | Pelo menos um pedido cadastrado | `GET /pedidos` | Nenhuma | `200 OK`; retorna a lista de pedidos | `PRINT de teste do trabalho/CT02 - Consultar todos os pedidos 200.png` |
| CT03 | Filtrar pedidos por canal | Pelo menos um pedido com canal `APP` | `GET /pedidos?canalPedido=APP` | `canalPedido=APP` | `200 OK`; retorna somente pedidos do canal solicitado | `PRINT de teste do trabalho/CT03 - Filtrar pedidos por canal 200.png` |
| CT04 | Simular pagamento aprovado | Pedido existente em estado `AGUARDANDO_PAGAMENTO` e perfil ATENDENTE autenticado | `POST /pedidos/{pedidoId}/pagamentos` | `formaPagamento: "PIX"`, `forcarResultado: "APROVADO"` | `201 Created`; status do pedido atualizado para `PAGO` | `PRINT de teste do trabalho/CT04 - Pagamento aprovado 200.png` |
| CT05 | Consultar pagamentos de um pedido | Pedido já associado a pelo menos um pagamento | `GET /pedidos/{pedidoId}/pagamentos` | Nenhuma no Body | `200 OK`; retorna o histórico de pagamentos do pedido | `PRINT de teste do trabalho/CT05 - Consultar pagamentos do pedido 200.png` |
| CT06 | Verificar consulta por perfil cliente | Usuário `cliente` autenticado | `GET /pedidos` | Nenhuma | `200 OK`; cliente consegue consultar pedidos | `PRINT de teste do trabalho/CT06 - Cliente consultar pedidos 200.png` |
| CT07 | Rejeitar canal de pedido inválido | Usuário autenticado e dados válidos, exceto o canal | `POST /pedidos` | `canalPedido: "WHATSAPP"`, `valorTotal: 49.90` | `400 Bad Request`; pedido não é criado | `PRINT de teste do trabalho/CT07 - Canal inválido 400.png` |
| CT08 | Rejeitar valor total inválido | Usuário autenticado e dados válidos, exceto o valor | `POST /pedidos` | `valorTotal: 0` | `400 Bad Request`; pedido não é criado | `PRINT de teste do trabalho/CT08 - Valor inválido 400.png` |
| CT09 | Impedir cliente de processar pagamento | Usuário `cliente` autenticado; pedido existente | `POST /pedidos/{pedidoId}/pagamentos` | Dados de pagamento enviados no Body | `403 Forbidden`; pagamento não é registrado | `PRINT de teste do trabalho/CT09 - Cliente processar pagamento - erro 403.png` |
| CT10 | Impedir reprocessamento de pedido pago | Pedido com status `PAGO` | `POST /pedidos/{pedidoId}/pagamentos` | `formaPagamento: "PIX"`, `forcarResultado: "APROVADO"` | `409 Conflict`; novo pagamento não é registrado | `PRINT de teste do trabalho/CT10 - Reprocessar pedido pago 409.png` |

## Situação de execução

Todos os casos devem ser registrados como aprovados somente após a captura da requisição, da resposta completa em JSON e do código HTTP correspondente.

## Observações

- Os números dos pedidos usados em CT04, CT05 e CT10 devem ser substituídos pelo `id` real disponível no banco.
- Os testes positivos e negativos devem ser executados na mesma coleção exportada do Postman.

## Arquivos associados

- Evidências e capturas: `docs/PRINT de teste do trabalho/`
- Coleção Postman: `docs/PRINT de teste do trabalho/Raizes do Nordeste - Plano de Testes.postman_collection.json`
