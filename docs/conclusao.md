# Conclusão

Este projeto apresentou o desenvolvimento de uma API REST para gerenciamento de pedidos em um contexto de restaurante com operação multicanal. A solução utilizou Java 17, Spring Boot, PostgreSQL, JPA, Flyway e Docker Compose, além de Spring Security, validações Bean Validation e documentação OpenAPI.

A implementação contemplou cadastro e consulta de pedidos, filtragem por canal, processamento simulados de pagamento e atualização do status do pedido. Foram definidos também perfis de acesso para cliente, atendente, gerente e administrador, com respostas padronizadas para erros comuns da API.

A auditoria persistida em PostgreSQL registrou ações críticas, como a criação de pedidos e o processamento de pagamentos. Essa funcionalidade permite identificar o usuário responsável, a entidade alterada e o momento da operação, contribuindo para rastreabilidade e controles de segurança.

Os testes realizados no Postman abrangiram fluxos positivos e negativos, incluindo criação de pedido, consulta, validação de canal e valor, autorização por perfil, pagamento aprovado e tentativa de reprocessamento. A documentação por Swagger, o plano de testes e as evidências capturadas facilitaram a compreensão e validação da API.

Como trabalhos futuros, podem ser implementados autenticação baseada em tokens, persistência dos usuários em banco de dados, hash seguro de senhas, auditoria completa de todas as operações críticas, implantação em nuvem e configuração de pipeline de integração e entrega contínua.
