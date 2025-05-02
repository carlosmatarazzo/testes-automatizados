# language: pt

Funcionalidade: Cadastro de novo recipiente
  Como usuário da API
  Quero cadastrar um novo recipiente
  Para que o registro seja salvo corretamente no sistema

  Cenário: Cadastro bem-sucedido de recipiente
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | PLASTICO     |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    Então o status code da resposta de recipiente deve ser 201

  Cenário: Cadastro de recipiente sem sucesso ao passar o campo tipo invalido
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | DESCONHECIDO |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    Então o status code da resposta de recipiente deve ser 500
    E o corpo de resposta de erro da api de recipiente deve retornar a mensagem "Error processing request"