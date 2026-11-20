# Requisitos do Sistema

## Requisitos funcionais

| ID | Requisito | Prioridade |
|---|---|---|
| RF01 | O sistema deve permitir a criação de pedidos. | Alta |
| RF02 | O pedido deve conter cliente, unidade, canal, status e valor total. | Alta |
| RF03 | Os canais permitidos devem ser APP, TOTEM, BALCAO, PICKUP e WEB. | Alta |
| RF04 | O sistema deve consultar todos os pedidos cadastrados. | Alta |
| RF05 | O sistema deve filtrar pedidos pelo canal informado. | Alta |
| RF06 | O sistema deve atribuir inicialmente o status AGUARDANDO_PAGAMENTO ao pedido criado. | Alta |
| RF07 | O sistema deve permitir simular pagamentos aprovados ou recusados. | Alta |
| RF08 | Um pagamento aprovado deve alterar o status do pedido para PAGO. | Alta |
| RF09 | O sistema deve impedir novo pagamento em pedido já finalizado. | Alta |
| RF10 | O sistema deve consultar o histórico de pagamentos de um pedido. | Média |
| RF11 | O sistema deve validar campos obrigatórios e valores inválidos antes da persistência. | Alta |
| RF12 | O sistema deve retornar mensagens de erro em formato JSON padronizado. | Alta |
| RF13 | O sistema deve autenticar usuários por meio de perfil e senha. | Alta |
| RF14 | CLIENTE deve poder criar e consultar pedidos. | Alta |
| RF15 | ATENDENTE e GERENTE devem poder processar pagamentos. | Alta |
| RF16 | A documentação dos endpoints deve ser disponibilizada pelo Swagger/OpenAPI. | Média |
| RF17 | O sistema deve registrar em banco ações críticas de criação de pedido e processamento de pagamento. | Alta |
| RF18 | Somente usuários com perfil ADMIN devem consultar os registros de auditoria. | Alta |

## Requisitos não funcionais

| ID | Requisito | Prioridade |
|---|---|---|
| RNF01 | A aplicação deve ser desenvolvida em Java 17 com Spring Boot. | Alta |
| RNF02 | Os dados devem ser persistidos em PostgreSQL. | Alta |
| RNF03 | Alterações no banco devem utilizar migrações Flyway. | Alta |
| RNF04 | A API deve seguir o padrão REST e utilizar JSON. | Alta |
| RNF05 | Erros comuns devem utilizar códigos HTTP adequados: 400, 401, 403, 404 e 409. | Alta |
| RNF06 | O ambiente local deve ser executável por Docker Compose e Maven. | Alta |
| RNF07 | Senhas de usuários não devem ser armazenadas em texto puro. | Alta |
| RNF08 | Dados sensíveis não devem aparecer em logs desnecessários ou respostas públicas. | Alta |
| RNF09 | A API deve ser documentada por Swagger/OpenAPI. | Média |
| RNF10 | O projeto deve possuir testes automatizados básicos e testes manuais documentados no Postman. | Alta |
| RNF11 | Credenciais reais não devem ser versionadas no repositório público. | Alta |
| RNF12 | A aplicação deve validar entradas antes de acessar regras de persistência. | Alta |
| RNF13 | Logs de auditoria devem registrar usuário, ação, entidade, identificador e data/hora. | Alta |
