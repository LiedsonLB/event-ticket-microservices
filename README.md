# Desafio 03 - Gerenciamento de Eventos e Ingressos (Francisco Liédson)

![Arquitetura de Microsserviços](/snapshots/projectdiagram.png)

## Sobre o Projeto

Este projeto consiste em um sistema com arquitetura de microsserviços para gerenciamento de eventos e ingressos, como shows, teatros, concertos e exposições. O sistema é composto por dois microsserviços principais: **ms-event-manager** e **ms-ticket-manager**, que se comunicam via RabbitMQ e utilizam o MongoDB para persinstência dos dados. Além disso, o sistema consome a API ViaCEP para obter informações de endereço com base no CEP fornecido.

## Tecnologias Utilizadas

<div style="display: flex; flex-wrap: wrap; gap: 5px; justify-content: center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" alt="Java 17" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="Spring Boot 3.X" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/rabbitmq/rabbitmq-original.svg" alt="RabbitMQ" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-original.svg" alt="MongoDB" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/swagger/swagger-original.svg" alt="MongoDB" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/junit/junit-original.svg" alt="MongoDB" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/insomnia/insomnia-original.svg" alt="MongoDB" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" alt="Docker" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/githubactions/githubactions-original.svg" alt="Docker" height="30" width="40">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/amazonwebservices/amazonwebservices-plain-wordmark.svg" alt="AWS" height="30" width="40">
</div>

## Funcionalidades

1. **RabbitMQ**: É um broker de mensageria que permite a comunicação entre microsserviços de forma assíncrona, garantindo a escalabilidade e a confiabilidade da aplicação.
2. **MongoDB Atlas**: Banco de dados NoSQL em cloud que armazena os dados dos microsserviços, garantindo a persistência e a recuperação dos dados.
3. **ViaCEP API**: API que fornece informações de endereço com base no CEP fornecido, facilitando o preenchimento de formulários.
4. **GitHub Actions**: Ferramenta de CI/CD que automatiza o processo de build, testes e deploy da aplicação.
5. **Insomnia**: Ferramenta de testes de API que permite testar os endpoints dos microsserviços.
6. **Swagger**: Ferramenta de documentação de API que permite visualizar e testar os endpoints dos microsserviços.
7. **Docker Hub**: Repositório de imagens Docker que armazena as imagens dos microsserviços, facilitando a distribuição e execução da aplicação.
8. **AWS EC2**: Serviço de computação em nuvem que hospeda os microsserviços, garantindo a disponibilidade e a escalabilidade da aplicação.
9. **Docker**: Contêineriza toda a aplicação, permitindo que ela seja escalável, possa ser baixada e executada em qualquer dispositivo com acesso ao Docker, além de facilitar sua distribuição e execução.

## Microsserviços

### 1. ms-event-manager
Responsável pelo gerenciamento de eventos, incluindo criação, consulta, atualização e exclusão de eventos.

| **Método** | **Endpoint**            | **Descrição**                                   |
|------------|-------------------------|-------------------------------------------------|
| POST       | `/create-event`         | Cria um novo evento                             |
| GET        | `/get-event/{id}`       | Retorna detalhes de um evento pelo ID           |
| GET        | `/get-all-events`       | Lista todos os eventos                          |
| GET        | `/get-all-events/sorted`| Lista eventos em ordem alfabética               |
| PUT        | `/update-event/{id}`    | Atualiza um evento pelo ID                      |
| DELETE     | `/delete-event/{id}`    | Exclui um evento pelo ID (verifica ingressos)   |

---

### 2. ms-ticket-manager
Responsável pelo gerenciamento de ingressos, incluindo criação, consulta, atualização e cancelamento de ingressos.

| **Método** | **Endpoint**            | **Descrição**                                   |
|------------|-------------------------|-------------------------------------------------|
| POST       | `/create-ticket`        | Cria um novo ingresso após validar o evento     |
| GET        | `/get-ticket/{id}`      | Retorna detalhes de um ingresso pelo ID         |
| PUT        | `/update-ticket/{id}`   | Atualiza um ingresso pelo ID                    |
| DELETE     | `/cancel-ticket/{id}`   | Cancela um ingresso pelo ID (soft-delete)       |
| GET        | `/check-tickets-by-event/{eventId}` | Verifica ingressos vinculados a um evento |

---

## Configuração do Projeto

### Pré-requisitos

- Java 17
- MongoDB Atlas
- RabbitMQ Cloud
- Docker
- AWS EC2

## Deploy Automatizado com GitHub Actions na AWS EC2

O deploy da aplicação foi automatizado com GitHub Actions, que realiza o build e deploy da aplicação na AWS EC2. Para isso, foi criado um arquivo de configuração `.yml` na pasta `.github/workflows` que define o pipeline de CI/CD da aplicação. A ideia é que a cada push na branch `main`, o GitHub Actions realize o build da aplicação gere as imagens Docker, faça o push das imagens no Docker Hub e realize o deploy da aplicação na AWS EC2. futuramente, será implementado uma etapa de testes automatizados para garantir a qualidade do código antes do deploy.

![MongoDB imagem](/snapshots/github_deploy.png)

![MongoDB imagem](/snapshots/github_actions.png)

### Passos para Executar Localmente

####  Clone o repositório:
```bash
git clone https://github.com/LiedsonLB/PbDes03_FranciscoBarros.git
```

#### Baixando e executando os containers:
Na pasta raiz do repositório clonado, execute o seguinte script no terminal:
```bash
docker compose up --build
```

## Documentação de endpoints
Para acessar a documentação dos endpoint utilizados podemos acessar o swagger de cada microserviço, com o container rodando, execute:  
ms-event-manager:  
```bash
http://localhost:8080/swagger-ui/index.html
```
ms-ticket-manager:  
```bash
http://localhost:8081/swagger-ui/index.html
```

## Cobertura de Testes

Para gerar o relatório de cobertura de testes, execute:

```bash
mvn clean verify jacoco:report
```

O relatório de cobertura estará disponível em:
```
target/site/jacoco/index.html
```

## Estrutura do Banco de Dados
### Tabelas

- `tb_event` - armazena os eventos cadastrados no sistema em produção.
- `tb_ticket` - armazena os ingressos cadastrados no sistema em produção.
- `tb_event_dev` - armazena os eventos cadastrados no sistema em desenvolvimento.
- `tb_ticket_dev` - armazena os ingressos cadastrados no sistema em desenvolvimento.
  
## Imagens do Projeto

## Cobertura de Testes com Jacoco
ms-event-manager:
![Jacoco imagem](/snapshots/event_jacoco.png)
ms-ticket-manager:
![Jacoco imagem](/snapshots/ticket_jacoco.png)


### Swagger
![Swagger imagem](/snapshots/event_swagger.png)
![Swagger imagem](/snapshots/ticket_swagger.png)

### Mongo Atlas Databases
![MongoDB imagem](/snapshots/databases_atlas.png)

### RabbitMQ
![RabbitMQ imagem](/snapshots/rabbitmq.png)
![RabbitMQ imagem](/snapshots/mensages_payload.png)

### Containers Docker e Imagens Docker Hub
![Containers Docker imagem](/snapshots/dockerhub.png)