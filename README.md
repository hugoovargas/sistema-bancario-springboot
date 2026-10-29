# Bank System

Sistema bancário backend desenvolvido em Java 21 e Spring Boot, com foco em
Clean Architecture, Domain-Driven Design (DDD), modelagem rica de domínio,
testes automatizados e boas práticas de engenharia de software.

O projeto simula operações bancárias como gerenciamento de clientes, contas correntes e poupanças, depósitos, saques, transferências e consulta de transações. Também inclui relatórios financeiros desenvolvidos com consultas SQL nativas no PostgreSQL, permitindo analisar movimentações por conta e classificar contas por volume movimentado.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Maven](https://img.shields.io/badge/Maven-build-red)

![Tests](https://img.shields.io/github/actions/workflow/status/hugoovargas/sistema-bancario-springboot/ci.yml?branch=main&label=tests)
![Coverage](https://raw.githubusercontent.com/hugoovargas/sistema-bancario-springboot/main/badges/jacoco.svg)
![Branches](https://raw.githubusercontent.com/hugoovargas/sistema-bancario-springboot/main/badges/branches.svg)

## Sobre o projeto

O Bank System foi desenvolvido como um projeto de estudo para aplicar, na
prática, conceitos de desenvolvimento backend e arquitetura de software.

A aplicação foi construída buscando manter as regras de negócio isoladas
do framework e da infraestrutura, utilizando uma separação clara entre
domínio, aplicação, apresentação e persistência.

Entre os principais conceitos praticados estão:

- Clean Architecture
- Domain-Driven Design (DDD)
- Ports & Adapters
- Rich Domain Model
- SOLID
- Value Objects
- Testes unitários e de integração
- APIs REST
- Persistência relacional

---

## Funcionalidades

### Clientes

- Cadastro de clientes com CPF, nome e e-mail.
- Consulta dos dados de um cliente.
- Alteração de nome e e-mail.
- Remoção de clientes respeitando as regras de negócio.
- Validação de CPF e e-mail únicos.

### Contas

- Criação de contas correntes e contas poupança.
- Geração de agência e número de conta.
- Consulta de uma conta específica.
- Consulta das contas pertencentes a um cliente.
- Consulta de saldo.
- Remoção de contas somente quando permitido pelas regras de negócio.

### Operações bancárias

- Depósitos.
- Saques.
- Transferências entre contas.
- Consulta de transações de uma conta.
- Consulta paginada das transações.
- Registro das operações no histórico da conta.

### Conta corrente

- Suporte a saldo negativo dentro do limite definido para a conta.
- Validação do limite disponível durante operações de saque e transferência.

### Conta poupança

- Aplicação automática dos rendimentos durante as operações.
- Cálculo retroativo dos rendimentos após períodos de inatividade.
- Capitalização dos rendimentos.
- Registro dos rendimentos como transações no histórico da conta.

### Relatórios e consultas SQL

- Relatório de movimentações por conta, com quantidade e valor total de depósitos, saques, transferências enviadas e transferências recebidas.
- Ranking de contas por volume movimentado, ordenado do maior para o menor volume.
- Consultas SQL nativas no PostgreSQL utilizando `JOIN`, `GROUP BY`, `COUNT`, `SUM` e `COALESCE` para relacionar dados, agrupar transações e calcular indicadores.

### Regras de negócio

O domínio possui validações próprias para impedir operações inválidas,
como valores monetários inválidos, contas inexistentes, saldo insuficiente,
duplicidade de CPF ou e-mail e remoção de contas que ainda possuem saldo.

---

## Arquitetura

O projeto utiliza uma combinação de **Clean Architecture**, **Domain-Driven
Design (DDD)** e **Ports & Adapters (Arquitetura Hexagonal)**, buscando manter
as regras de negócio independentes de frameworks, banco de dados e detalhes
de infraestrutura.

A aplicação é organizada nas seguintes camadas:

```text
src/main/java/com/banco/bank_system
│
├── domain
│   ├── entity
│   ├── valueobject
│   ├── exception
│   └── ...
│
├── application
│   ├── account
│   ├── client
│   ├── transaction
│   └── ...
│
├── infrastructure
│   ├── persistence
│   └── ...
│
└── presentation
    ├── controller
    ├── dto
    └── ...
```

### Domínio

Contém o núcleo do sistema e concentra as regras de negócio.

É composto por entidades, objetos de valor e exceções de domínio. Essa
camada não depende de Spring, JPA ou PostgreSQL.

Entre os principais elementos estão:

- `Client`
- `Account`
- `CheckingAccount`
- `SavingsAccount`
- `Transaction`
- `Money`
- `CPF`
- `Email`
- `AccountIdentity`
- `AccountId`
- `ClientId`
- `TransactionId`

### Application

Contém os casos de uso da aplicação e coordena a execução das operações
de negócio.

Os casos de uso trabalham através de interfaces (ports), evitando depender
diretamente de implementações de infraestrutura.

Exemplos:

- Criação e gerenciamento de clientes
- Criação e gerenciamento de contas
- Depósitos
- Saques
- Transferências
- Consulta de saldo
- Consulta de contas
- Consulta de transações

Os casos de uso também disponibilizam relatórios de movimentações por conta e ranking de contas por volume movimentado. Para isso, utilizam DTOs e interfaces de persistência (*ports*), mantendo a camada de aplicação desacoplada das consultas SQL e dos detalhes de infraestrutura.

### Fluxo da aplicação

Uma requisição percorre, de forma simplificada, o seguinte fluxo:

```text
HTTP Request
     │
     ▼
Controller
     │
     ▼
Use Case
     │
     ▼
Domain
     │
     ▼
Repository Port
     │
     ▼
Repository Adapter
     │
     ▼
Spring Data JPA
     │
     ▼
PostgreSQL
```

Essa separação mantém as regras de negócio independentes dos detalhes de
infraestrutura e facilita a substituição de componentes externos.

### Infrastructure

Contém os detalhes relacionados à infraestrutura da aplicação.

Essa camada implementa os ports definidos pela aplicação e é responsável
pela persistência utilizando:

Spring Data JPA;
Hibernate;
PostgreSQL;
H2.

As entidades de persistência são separadas das entidades de domínio, com
mapeamentos realizados através de Mappers.

Para os relatórios, a infraestrutura utiliza consultas SQL nativas no PostgreSQL, com operações de junção (`JOIN`), agrupamento (`GROUP BY`) e agregação (`COUNT` e `SUM`). Os resultados são mapeados por projeções e convertidos em DTOs da aplicação por meio dos adaptadores de persistência.

### Presentation

Responsável pela exposição da API REST.

Contém:

* Controllers;
* Request DTOs;
* Response DTOs;
* tratamento das respostas HTTP;
* documentação da API.

A camada de apresentação não contém regras de negócio, delegando a execução
das operações aos casos de uso.

---

## Testes e Qualidade

O projeto possui testes automatizados em diferentes níveis, buscando validar
tanto as regras de negócio do domínio quanto a integração entre as camadas
da aplicação.

### Testes de domínio

Validam diretamente as regras de negócio das entidades e Value Objects,
sem dependência do Spring ou da infraestrutura.

São testados comportamentos como:

- operações de `Money`;
- criação e restauração de entidades;
- depósitos e saques;
- limites de conta corrente;
- regras da conta poupança;
- aplicação de rendimentos;
- validações e exceções de domínio.

### Testes unitários

Os casos de uso são testados isoladamente utilizando **JUnit 5** e **Mockito**,
com dependências externas substituídas por mocks.

Isso permite verificar o comportamento da camada de aplicação sem depender
do banco de dados ou de implementações concretas de infraestrutura.

### Testes de Controller

Os controllers possuem testes utilizando `MockMvc`, verificando:

- status HTTP;
- estrutura das respostas;
- dados retornados;
- validação das requisições;
- tratamento de erros;
- códigos de erro da API.

### Testes de integração

Os fluxos principais da aplicação são testados através de
`@SpringBootTest`, utilizando o contexto real do Spring e banco de dados
para validar a integração entre:

```text
Controller
    ↓
Use Case
    ↓
Repository Adapter
    ↓
Spring Data JPA
    ↓
Database
```

Os testes de integração verificam operações como criação de clientes,
criação de contas, depósitos, saques, transferências e consultas de
transações.

---

## Cobertura

A cobertura dos testes é monitorada utilizando JaCoCo.

O projeto também possui GitHub Actions, executando automaticamente os
testes e gerando os relatórios de cobertura a cada alteração enviada ao
repositório.

Os badges de cobertura e de branches são atualizados automaticamente pelo
CI.

---

## CI

O projeto utiliza **GitHub Actions** para executar automaticamente o build,
os testes e a geração dos relatórios de cobertura.

O pipeline executa:

```text
Checkout
   ↓
Java 21
   ↓
Maven
   ↓
Testes
   ↓
JaCoCo
   ↓
Relatório de cobertura
   ↓
Badges de cobertura
```

---

## Tecnologias e Ferramentas

### Backend

- Java 21
- Spring Boot 3
- Spring Web
- Spring Validation
- Spring Data JPA
- Hibernate

### Banco de dados

- PostgreSQL
- H2 Database
- SQL

O PostgreSQL é utilizado como banco de dados principal da aplicação,
enquanto o H2 é utilizado nos testes.

### Arquitetura e desenvolvimento

- Clean Architecture
- Domain-Driven Design (DDD)
- Ports & Adapters
- SOLID
- Programação Orientada a Objetos
- Value Objects
- Rich Domain Model
- Maven

### Testes e qualidade

- JUnit 5
- Mockito
- Spring Boot Test
- MockMvc
- JaCoCo

### DevOps e ferramentas

- Git
- GitHub
- GitHub Actions
- Docker
- Docker Compose

### Documentação

- OpenAPI
- Swagger UI

---

## API

A aplicação disponibiliza uma API REST para gerenciamento dos recursos
bancários.

Principais recursos:

| Recurso | Operações |
|---|---|
| Clientes | Cadastro, consulta, alteração e remoção |
| Contas | Criação, consulta, saldo e remoção |
| Depósitos | Realização de depósitos |
| Saques | Realização de saques |
| Transferências | Transferências entre contas |
| Transações | Consulta do histórico de transações |
| Contas do cliente | Consulta das contas associadas a um cliente |
| Relatórios de movimentações | Consulta de quantidades e totais por tipo de operação e conta |
| Ranking de contas | Classificação de contas por volume movimentado |

A documentação completa dos endpoints está disponível através do Swagger UI.

[Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

## Como executar o projeto

### Pré-requisitos

Antes de executar o projeto, certifique-se de possuir:

- Java 21
- Maven
- Docker
- Docker Compose
- Git

### 1. Clone o repositório

```bash
git clone https://github.com/hugoovargas/sistema-bancario-springboot.git
cd sistema-bancario-springboot
```

### 2. Suba o banco de dados

O projeto utiliza PostgreSQL executado através do Docker Compose.

```text
docker compose up -d
```

Para verificar se o container está em execução:

```text
docker compose ps
```

### 3. Execute a aplicação

Utilizando Maven:

```text
./mvnw spring-boot:run
```

No Windows:

```text
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:
```text
http://localhost:8080
```

### 4. Executar os testes

Para executar todos os testes:

```text
./mvnw test
```

Para executar o build completo com os testes e geração do relatório JaCoCo:

```text
./mvnw clean verify
```
### 5. Acessar a documentação da API

A API possui documentação através do OpenAPI e Swagger UI.

Após iniciar a aplicação, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

A documentação permite visualizar e testar os endpoints disponíveis
diretamente pelo navegador.

### 6. Parar o banco de dados

Para parar os containers:

```text
docker compose down
```

## Destaques técnicos

- Modelagem de domínio utilizando Entities e Value Objects.
- Regras de negócio encapsuladas no domínio.
- Separação entre entidades de domínio e entidades de persistência.
- Casos de uso desacoplados da infraestrutura através de Ports & Adapters.
- Consultas SQL nativas no PostgreSQL para relatórios de movimentações por conta e ranking por volume movimentado, utilizando JOINs, agrupamentos e funções de agregação.
- Geração de identificadores específicos para cada contexto do domínio.
- Paginação de consultas utilizando Spring Data.
- Tratamento centralizado de exceções e códigos de erro da API.
- Persistência utilizando PostgreSQL e Spring Data JPA.
- Testes unitários, de domínio, controllers e integração.
- Cobertura de código monitorada através do JaCoCo.
- Pipeline de CI utilizando GitHub Actions.
- Documentação da API utilizando OpenAPI e Swagger.
- Ambiente de desenvolvimento utilizando Docker Compose.
