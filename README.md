# Introdução
Esse serviço de pagamento foi desenvolvido com o intuito de entender melhor o funcionamento de métodos de pagamento e
utilização de microserviços, uma vez que esse projeto será utilizado por [outro projeto](https://github.com/luqmts/Store).

O gateway de pagamentos do Stripe foi utilizado no desenvolvimento do projeto.

Por enquanto é suportado:
- Três requisições, um POST e dois GETs que estão no arquivo [PaymentController](/src/main/java/com/luq/payment/controllers/PaymentController.java).
- Criação de sessões de checkout no Stripe por um tópico no Kafka.

---

# Introduction
This payment service was developed for purpose on better understanding how payment methods work and microservices usage, 
as long this project will be used by [another project](https://github.com/luqmts/Store).

Stripe's payment gateway was used on project's development.

For now, the following is supported:
- Tree requests, one POST and two GETs that are on [PaymentController](/src/main/java/com/luq/payment/controllers/PaymentController.java) file.
- Checkout sessions creation on Stripe over a topic on Kafka