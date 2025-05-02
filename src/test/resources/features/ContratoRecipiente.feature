# language: pt

Funcionalidade: Validar o contrato ao realizar um cadastro bem-sucedido de recipiente
  Como usuário da API
  Quero cadastrar um novo recipiente bem-sucedido
  Para que eu consiga validar se o contrato esta conforme o esperado

  Cenario: Validar contrato do cadastro bem-sucedido de recipiente
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | PLASTICO     |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    Então o status code da resposta de recipiente deve ser 201
    E que o arquivo de contrato de recipiente esperado é o "Cadastro bem-sucedido de recipiente"
    Então a resposta da requisição de recipiente deve estar em conformidade com o contrato selecionado
