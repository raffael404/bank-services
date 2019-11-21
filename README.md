[![Build Status](https://travis-ci.org/raffael404/banking-services.svg?branch=master)](https://travis-ci.org/raffael404/banking-services)

# Banking Services
API Restful genérica para serviços bancários.

### Detalhes da API RESTful
A API Restful para serviços bancários foi pensada como um sistema para integrar vários bancos e clientes em um mesmo local. Ela possui 2 tipos de usuários, um Pessoa Física (Cliente) e um Pessoa Jurídica (Banco), cada um podendo executar o serviços associados ao mesmo (o cliente, por exemplo pode abrir contas em qualquer banco cadastrado no sistema, enquanto o banco pode cadastrar agências associadas ao mesmo). Este projeto foi desenvolvido em Java 11 em conjunto com o framework Spring Boot e o Banco de Dados MySQL.

### Como executar a aplicação
Certifique-se de ter o Maven instalado e adicionado ao PATH de seu sistema operacional, assim como o Git e o MySQL.
```
git clone https://github.com/raffael404/banking-services.git
cd banking-services
mvn spring-boot:run
Acesse os endpoints através da url http://localhost:8080/banking
```
As requisições aceitas são todas do tipo POST e podem ser enviadas através de um cliente HTTP qualquer, como o Postman. O fluxo de autenticação segue o padrão OAuth2, com o modo de autorização por senha, ou seja, o usuário deve enviar uma requisição para localhost:8080/banking/oauth/token com o client id = cliente e o client secret = senha e os dados de acesso do seu usuário (grant_type, username e password). Os usuários devem ser previamente cadastrados como um "banco" ou "cliente" através dos endpoints correspondentes. Os demais endpoints só podem ser acessados se o token estiver presente no cabeçalho da requisição. O token de acesso expira após 1 hora.

### Utilizando o Swagger
A aplicação também pode ser testada diretamente pelo Swagger, através da URL http://localhost:8080/banking/swagger-ui.html. Contudo, para obter acesso aos endpoints protegidos é necessário incluir o token de acesso no cabeçalho da requisição através da opção "Authorize", no formato "Bearer token_de_acesso". O token, entretanto, precisa ser obtido através de uma aplicação externa. O comando abaixo mostra um exemplo de requisição com o cURL:
```
curl -X POST -u "cliente:senha" -d "grant_type=password&username=nome_de_usuario&password=senha_do_usuario" http://localhost:8080/banking/oauth/token
```

### Endpoints
```
/usuario/cadastrar/banco
/usuario/cadastrar/cliente
/banco/cadastrar/agencia
/banco/remover/agencia
/conta/abrir
/conta/fechar
/conta/depositar
/conta/sacar
/conta/transferir
/conta/extrato
```