# LGPD, Segurança e Auditoria

## Dados pessoais tratados

O modelo inicial possui dados pessoais principalmente na tabela `usuarios`, incluindo nome, e-mail e perfil. A tabela de pedidos associa o cliente por meio de `cliente_id` e registra canal, status, valor e data de criação.

## Finalidades

Os dados são utilizados para:

- identificar o cliente vinculado ao pedido;
- registrar a origem multicanal da compra;
- processar o fluxo comercial e simular pagamentos;
- controlar status e histórico de pedidos;
- permitir autenticação e autorização de usuários internos.

## Base legal e consentimento

A implementação mantém o campo de consentimento LGPD na tabela de usuários. Em ambiente real, o cadastro deve registrar a finalidade, a base legal aplicável e a confirmação do consentimento quando necessário. O consentimento não deve ser considerado uma única base legal para todas as operações.

## Dados sensíveis

Senha ou hash de senha não devem ser retornados pelas APIs. Senhas de desenvolvimento e credenciais de banco não devem ser usadas em produção. O projeto utiliza variáveis de ambiente e deve evoluir para armazenamento seguro de credenciais.

## Controles técnicos implementados

- autenticação HTTP Basic;
- separação de usuários por perfis;
- autorização por perfil em pedidos e pagamentos;
- validação de dados com Bean Validation;
- respostas padronizadas de erro;
- migrações versionadas com Flyway;
- documentação pública limitada à interface técnica;
- senha simulada em ambiente local com perfil em memória.

## Controles recomendados para produção

- substituir autenticação básica por JWT ou OAuth2;
- armazenar usuários e roles em tabelas persistentes;
- usar hashes seguros com custo adequado;
- impedir acesso de usuários anônimos a dados pessoais;
- adicionar HTTPS;
- criar logs estruturados sem dados pessoais desnecessários;
- definir retenção, anonimização ou exclusão de dados;
- implementar auditoria de ações administrativas e pagamentos;
- aplicar backup, recuperação e controle de acesso ao banco;
- registrar consentimento e permitir revogação por canal adequado.

## Auditoria

A API deve registrar posteriormente o usuário responsável, data e horário das operações sensíveis. Dados mínimos recomendados incluem identificação do usuário, recurso acessado, ação realizada e resultado da operação, sem registrar senhas ou dados completos de autenticação.
