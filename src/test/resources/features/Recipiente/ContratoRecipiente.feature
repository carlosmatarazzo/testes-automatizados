# language: pt

Funcionalidade: Contrato da resposta de cadastro de recipiente
  Como validador da API
  Quero garantir que a resposta esteja de acordo com o contrato JSON
  Para assegurar consistência entre front-end e back-end

  Cenário: Resposta de recipiente segue contrato
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | PLASTICO     |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    E que o arquivo de contrato de recipiente esperado é o "Cadastro bem-sucedido de recipiente"
    Então a resposta da requisição de recipiente deve estar em conformidade com o contrato selecionado