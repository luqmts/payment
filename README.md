[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[KAFKA_BADGE]: https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white
<h1 align="center" style="font-weight: bold;">Payment</h1>

![kafka][KAFKA_BADGE]
![java][JAVA_BADGE]
![spring][SPRING_BADGE]

<h1 id="description-pt-br">Glossário / Glossary</h1>

- [🇧🇷 Descrição](#description-pt-br)
- [Requisitos](#requisites-pt-br)
- [Instalação e utilização](#install-and-usage-pt-br)
- [Endpoints disponíveis](#available-endpoints-pt-br)
    - [Sessões](#available-endpoints-session-pt-br)
    - [Corpo das requisições e respostas dos endpoints](#request-response-body)
- [🇺🇸 Description](#description-en-us)
- [Requisites](#requisites-en-us)
- [Installation and usage](#install-and-usage-en-us)
- [Available Endpoints](#available-endpoints-en-us)
    - [Sessions](#available-endpoints-session-en-us)
    - [Request and Response Body endpoint](#request-response-body)

<h1 id="description-pt-br">🇧🇷 Descrição</h1>

Esse serviço de pagamento foi desenvolvido com o intuito de entender melhor o funcionamento de métodos de pagamento e
utilização de microserviços, uma vez que esse projeto é utilizado por [outro projeto](https://github.com/luqmts/sales-management).

Foi utilizado o gateway de pagamentos do [Stripe](https://stripe.com), a aplicação possui um endpoint para criação de 
Sessões de checkout, ao ser gerada, é esperado uma atualização do status por um webhook, caso não aconteça em 15 minutos, a sessão é dada como
expirada, caso o usuário efetue o pagamento a sessão é considerada como paga e o fluxo termina.

<h1 id="requisites-pt-br">Requisitos</h1>

- [Apache Kafka 4.0.0](https://kafka.apache.org/downloads)
- [Java 24](https://www.oracle.com/br/java/technologies/downloads/)

<h1 id="install-and-usage-pt-br">Instalação e utilização</h1>

1. Realize o clone do repositório na sua máquina com `git clone https://github.com/luqmts/payment`;
2. Verifique se as dependências no arquivo [pom](./pom.xml) foram adicionados corretamente no projeto, caso não,
   execute o comando `mvn clean install`;
3. Inicie o Kafka na porta 9092;
4. Caso o tópico _payment-status-topic_ não tenha sido criado, crie ele;
5. Execute o comando `mvn spring-boot:run` para iniciar a aplicação.

<h1 id="available-endpoints-pt-br">Endpoints disponíveis</h1>

Abaixo há todas as rotas de API disponíveis atualmente e uma descrição, ao clicar em alguma, é possível acessar o corpo da
resposta e caso exista, o corpo da requisição.

<h2 id="available-endpoints-session-pt-br">Sessões</h2>

| Rota                                                          | Descrição                                  | 
|---------------------------------------------------------------|--------------------------------------------|
| [POST /api/payments/create-checkout](#post-session)           | Cadastra uma nova sessão de Checkout       | 
| [GET /api/payments/get-last-sessions](#get-last-sessions)     | Obtém as 5 últimas sessões de Checkout     | 
| [GET /api/payments/get-session-info/{id}](#get-session-by-id) | Obtém uma sessão de Checkout através do id | 

--- 

<h1 id="description-en-us">🇺🇸 Description</h1>

This payment service was developed for better understanding how payment methods work and microservices usage,
as long this project will be used by [another project](https://github.com/luqmts/sales-management).

Was used [Stripe's payment gateway](https://stripe.com), application have an endpoint for creating checkout session, when is 
generated, is waited for a status update, in case of don't happen on 15 minutes, session is considered as expired, if User
finish payment, session is considered as paid and flow is finished.
 sessão é considerada como paga e o fluxo termina.

<h1 id="requisites-en-us">Requisites</h1>

- [Apache Kafka 4.0.0](https://kafka.apache.org/downloads)
- [Java 24](https://www.oracle.com/br/java/technologies/downloads/)

<h1 id="install-and-usage-en-us">Installation and usage</h1>

1. Clone repository on your desktop with `git clone https://github.com/luqmts/payment`;
2. Verify if project's dependencies on [pom](./pom.xml) file were correctly added, if not, run the command `mvn clean install`;
3. Start Kafka on port 9092;
4. If topic _payment-status-topic_ is not created, create it;
5. Run command `mvn spring-boot:run` to start application.

<h1 id="available-endpoints-en-us">Available Endpoints</h1>


Bellow there are every API route actually available and a description, clicking on it, you can access the response body and
if it needs, the request body.

<h2 id="available-endpoints-session-en-us">Sessions</h2>


| Route                                                         | Description                     | 
|---------------------------------------------------------------|---------------------------------|
| [POST /api/payments/create-checkout](#post-session)           | Register a new Checkout session | 
| [GET /api/payments/get-last-sessions](#get-last-sessions)     | Get 5 last Checkout sessions    | 
| [GET /api/payments/get-session-info/{id}](#get-session-by-id) | Get Checkout session by id      | 

--- 

<h1 id="request-response-body">Corpo das requisições e respostas dos endpoints / Request and Response Body endpoint</h1>

<h2 id="sessions-crud">CRUD de Sessões / Sessions CRUD</h3>

<h3 id="post-session">POST /api/payment/create-checkout</h3>

#### REQUEST

```json
{
  "orderId": 1,
  "unitPrice": 25,
  "quantity": 5,
  "productName": "Product sample"
}
```

#### RESPONSE

```json
{
  "id": "cs_test_a1mgVE9HdPFHk5hsOyiEjDyPglpwZVprFUyfCjdWZo6ZwJtYrOFcR2VTRk",
  "status": "open",
  "url": "sampleUrl"
}
```

<h3 id="get-last-sessions">GET /api/payment/get-last-sessions</h3>

#### RESPONSE

```json
[
  {
    "id": "cs_test_a1mgVE9HdPFHk5hsOyiEjDyPglpwZVprFUyfCjdWZo6ZwJtYrOFcR2VTRk",
    "status": "open",
    "url": "sampleUrl"
  },
  {
    "id": "cs_test_a1NJ75NOrC19KQAS6nrMdJTLHynmi4JyzkJ1cpfnxtpBPs02pY6gT8OhS6",
    "status": "expired",
    "url": "sampleUrl"
  },
  {
    "id": "cs_test_a1qQkX5xajQ6Oc0qPGwvAKZveAnAo54EPu23sUHKw4DVrkJ63pnRmTpYQe",
    "status": "complete",
    "url": "sampleUrl"
  },
  {
    "id": "cs_test_a1BM3XyH1ANtU5Mp4d71AxbaD1wsbGczmj7ah26fgVlvk30UGjHTJfMbmG",
    "status": "expired",
    "url": "sampleUrl"
  },
  {
    "id": "cs_test_a16MCnV6XLbxFa1hScL60pAI84T6BKfukG0a9hkCafSyt6OGSlZAygHZee",
    "status": "expired",
    "url": "sampleUrl"
  }
]
```


<h3 id="get-session-by-id">GET /api/payment/get-session-info/{session_id}</h3>

#### RESPONSE

```json
{
  "id": "cs_test_a1mgVE9HdPFHk5hsOyiEjDyPglpwZVprFUyfCjdWZo6ZwJtYrOFcR2VTRk",
  "status": "open",
  "url": "sampleUrl"
}
```