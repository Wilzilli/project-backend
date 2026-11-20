# SISTEMA RAÍZES DO NORDESTE

**Wilson Zilli Junior**

**RU: 4425298**

**Curso: Análise e Desenvolvimento de Sistemas**

**Centro Universitário Internacional UNINTER**

Pinhais

2026

<div style="page-break-after: always;"></div>

# Sumário

1. Apresentação
2. Objetivos
3. Tecnologias utilizadas
4. Arquitetura da solução
5. Análise e requisitos
6. Modelo de dados
7. Implementação dos fluxos críticos
8. API e endpoints
9. Segurança, perfis e LGPD
10. Auditoria no banco de dados
11. Documentação Swagger/OpenAPI
12. Plano e evidências dos testes
13. Considerações finais
14. Referências
15. Anexos

<div style="page-break-after: always;"></div>

# 1. Apresentação

Este trabalho apresenta o desenvolvimento de uma API REST para gerenciamento de pedidos de uma rede fictícia denominada Raízes do Nordeste. O sistema contempla criação e consulta de pedidos em diferentes canais, processamento simulado de pagamentos e registro de ações críticas em banco de dados.

A aplicação utiliza Java 17 e Spring Boot, com PostgreSQL para persistência, Flyway para controle de migrações, Spring Data JPA para acesso aos dados, Spring Security para autenticação e autorização e Springdoc para documentação OpenAPI.

# 2. Objetivos

## 2.1 Objetivo geral

Desenvolver uma API REST funcional, segura e documentada para apoiar o fluxo de pedidos e pagamentos multicanal da rede Raízes do Nordeste.

## 2.2 Objetivos específicos

- Persistir pedidos e pagamentos em PostgreSQL.
- Disponibilizar operações REST para criação e consulta de pedidos.
- Implementar filtro de pedidos por canal de venda.
- Simular cenários de pagamento aprovado e recusado.
- Validar entradas e padronizar respostas de erro.
- Diferenciar usuários por perfis de acesso.
- Registrar ações críticas em tabela de auditoria.
- Documentar a API por meio de Swagger/OpenAPI.
- Evidenciar o funcionamento por testes no Postman.

# 3. Tecnologias utilizadas

- Java 17.
- Spring Boot 4.1.2.
- Spring Data JPA e Hibernate.
- PostgreSQL 15.
- Flyway.
- Spring Security com Basic Auth.
- Bean Validation.
- Springdoc OpenAPI e Swagger UI.
- Maven e Maven Wrapper.
- Docker Compose.
- Postman.
- Git e GitHub.

# 4. Arquitetura da solução

A aplicação segue uma organização em camadas:

- **API:** controllers responsáveis pelas requisições HTTP.
- **Application:** serviços e objetos de entrada das operações.
- **Domain:** entidades persistidas e regras de domínio.
- **Infrastructure:** repositórios JPA e integração com PostgreSQL.
- **Config:** configuração de segurança e OpenAPI.

O PostgreSQL é executado em container por Docker Compose, enquanto a API Spring Boot inicia pela porta 8080.

# 5. Análise e requisitos

Os requisitos funcionais e não funcionais completos estão registrados em `docs/requisitos.md`.

## 5.1 Principais requisitos funcionais

- Criar pedidos com cliente, unidade, canal, status e valor total.
- Consultar todos os pedidos ou filtrar por canal.
- Criar pedidos com status inicial `AGUARDANDO_PAGAMENTO`.
- Processar pagamentos simulados.
- Alterar pedidos aprovados para `PAGO`.
- Impedir reprocessamento de pedidos já finalizados.
- Consultar pagamentos associados a um pedido.
- Validar canal, identificadores e valor total.
- Diferenciar permissões entre cliente, atendente, gerente e administrador.
- Registrar operações críticas em auditoria.

## 5.2 Principais requisitos não funcionais

- Persistência em PostgreSQL.
- Uso de migrações Flyway.
- API REST baseada em JSON.
- Respostas HTTP consistentes.
- Validação antes da persistência.
- Proteção de credenciais reais em ambiente externo.
- Documentação por Swagger/OpenAPI.
- Testes positivos e negativos no Postman.

# 6. Modelo de dados

## 6.1 Entidades principais

O modelo inicial contém as entidades usuários, unidades, produtos, estoques, pedidos, itens de pedido e pagamentos.

Os pagamentos possuem relacionamento com pedidos. Os pedidos relacionam cliente e unidade. Produtos e unidades são associados por meio da tabela de estoque.

## 6.2 Auditoria

A tabela `logs_auditoria` registra:

- ação executada;
- entidade afetada;
- identificador da entidade;
- usuário responsável;
- detalhes da operação;
- data e hora do registro.

Os scripts Flyway estão disponíveis em `src/main/resources/db/migration/`.

# 7. Implementação dos fluxos críticos

## 7.1 Criação de pedido

A criação recebe os identificadores do cliente e da unidade, canal de venda e valor total. O status inicial é definido pela API como `AGUARDANDO_PAGAMENTO`. Após persistir o pedido, um registro é gravado na tabela de auditoria.

## 7.2 Pagamento aprovado

Para um pedido que ainda não foi finalizado, o usuário autorizado envia a forma de pagamento e o resultado mock. Quando o resultado é `APROVADO`, o pagamento é persistido e o status do pedido é atualizado para `PAGO`.

## 7.3 Pagamento recusado

Quando o resultado mock é `RECUSADO`, o pagamento é persistido, mas o pedido permanece em `AGUARDANDO_PAGAMENTO`.

## 7.4 Reprocessamento

Pedidos com status `PAGO` ou `CANCELADO` não devem receber novos pagamentos. Nesse caso, a API retorna conflito HTTP 409.

# 8. API e endpoints

## 8.1 Pedidos

### Criar pedido

```text
POST /pedidos
```

Exemplo de entrada:

```json
{
  "clienteId": 1,
  "unidadeId": 1,
  "canalPedido": "APP",
  "valorTotal": 89.90
}
```

### Consultar pedidos

```text
GET /pedidos
```

### Filtrar por canal

```text
GET /pedidos?canalPedido=APP
```

## 8.2 Pagamentos

### Processar pagamento

```text
POST /pedidos/{pedidoId}/pagamentos
```

```json
{
  "formaPagamento": "PIX",
  "forcarResultado": "APROVADO"
}
```

### Consultar pagamentos

```text
GET /pedidos/{pedidoId}/pagamentos
```

## 8.3 Auditoria

```text
GET /auditoria
```

Esse endpoint é restrito ao perfil administrador.

# 9. Segurança, perfis e LGPD

## 9.1 Segurança

A API utiliza HTTP Basic Auth para os testes iniciais. Os perfis definidos são:

- `CLIENTE`: cria e consulta pedidos.
- `ATENDENTE`: consulta pedidos e processa pagamentos.
- `GERENTE`: consulta pedidos e processa pagamentos.
- `ADMIN`: administra os recursos configurados e consulta auditoria.

## 9.2 LGPD

A implementação considera dados pessoais utilizados no cadastro de usuários e na associação de pedidos ao cliente. Os dados devem ter finalidade definida, acesso limitado e proteção técnica.

Senhas de desenvolvimento não devem ser reutilizadas em produção. A evolução recomendada envolve persistência segura de usuários, hash robusto, HTTPS, autenticação baseada em tokens e políticas de retenção e exclusão.

# 10. Auditoria no banco de dados

A migração `V6__create_logs_auditoria.sql` cria a tabela de auditoria. O serviço `AuditoriaService` registra ações críticas sem armazenar senhas ou dados completos de autenticação.

As ações implementadas incluem:

- `CRIAR_PEDIDO`, associada à entidade `PEDIDO`.
- `PROCESSAR_PAGAMENTO`, associada à entidade `PAGAMENTO`.

Cada registro contém o usuário autenticado obtido no contexto da requisição e o horário definido no momento da operação.

# 11. Documentação Swagger/OpenAPI

A documentação está disponível em:

```text
http://localhost:8080/swagger-ui/index.html
```

O documento JSON da API está disponível em:

```text
http://localhost:8080/v3/api-docs
```

# 12. Plano e evidências dos testes

O plano completo está em `docs/plano-de-testes.md`, que relaciona 10 casos de teste às suas evidências.

## 12.1 Casos positivos

- CT01: criação de pedido válido.
- CT02: consulta de todos os pedidos.
- CT03: filtro por canal.
- CT04: pagamento aprovado.
- CT05: consulta de pagamentos.
- CT06: consulta realizada com perfil cliente.

## 12.2 Casos negativos

- CT07: canal inválido.
- CT08: valor inválido.
- CT09: cliente sem autorização para pagamento.
- CT10: reprocessamento de pedido pago.

A coleção Postman exportada e as capturas estão em `docs/PRINT de teste do trabalho/`.

# 13. Considerações finais

O projeto consolidou uma API REST funcional para pedidos e pagamentos, com persistência, validações, autenticação por perfis, documentação OpenAPI e auditoria em banco.

Os principais pontos entregues são coerentes entre modelagem, implementação e testes. Como propostas futuras, podem ser incluídos JWT ou OAuth2, base persistente de usuários, auditoria ampliada, testes automatizados adicionais, implantação em nuvem e pipeline de integração contínua.

# 14. Referências

BRASIL. **Lei nº 13.709, de 14 de agosto de 2018 – Lei Geral de Proteção de Dados Pessoais**. Diário Oficial da União, Brasília, DF. Disponível em: https://www.planalto.gov.br/ccivil_03/_ato2015-2018/2018/lei/l13709.htm. Acesso em: 20 nov. 2026.

DOCKER. **Docker Documentation**. Disponível em: https://docs.docker.com/. Acesso em: 20 nov. 2026.

FLYWAY. **Flyway Documentation**. Disponível em: https://documentation.red-gate.com/fd. Acesso em: 20 nov. 2026.

MARTIN, Robert C. **Clean architecture: a craftsman's guide to software structure and design**. Upper Saddle River: Prentice Hall, 2017.

MICROSOFT. **Git Documentation**. Disponível em: https://git-scm.com/doc. Acesso em: 20 nov. 2026.

POSTGRESQL. **PostgreSQL 15 Documentation**. Disponível em: https://www.postgresql.org/docs/15/. Acesso em: 20 nov. 2026.

POSTMAN. **Learning the Postman API platform**. Disponível em: https://learning.postman.com/docs/. Acesso em: 20 nov. 2026.

SPRING. **Spring Boot Reference Documentation**. Disponível em: https://docs.spring.io/spring-boot/reference/. Acesso em: 20 nov. 2026.

SPRING. **Spring Data JPA**. Disponível em: https://docs.spring.io/spring-data/jpa/reference/. Acesso em: 20 nov. 2026.

SPRING. **Spring Security Reference**. Disponível em: https://docs.spring.io/spring-security/reference/. Acesso em: 20 nov. 2026.

SPRINGDOC. **Springdoc OpenAPI**. Disponível em: https://springdoc.org/. Acesso em: 20 nov. 2026.

# 15. Anexos

## 15.1 Estrutura do repositório

O código-fonte, documentação, migrações, coleção Postman e evidências estão disponíveis em:

```text
https://github.com/Wilzilli/project-backend
```

## 15.2 Evidências Postman

- `CT01 - Criar pedido válido - status 200.png`
- `CT02 - Consultar todos os pedidos 200.png`
- `CT03 - Filtrar pedidos por canal 200.png`
- `CT04 - Pagamento aprovado 200.png`
- `CT05 - Consultar pagamentos do pedido 200.png`
- `CT06 - Cliente consultar pedidos 200.png`
- `CT07 - Canal inválido 400.png`
- `CT08 - Valor inválido 400.png`
- `CT09 - Cliente processar pagamento - erro 403.png`
- `CT10 - Reprocessar pedido pago 409.png`

## 15.3 Coleção exportada

`Raizes do Nordeste - Plano de Testes.postman_collection.json`
