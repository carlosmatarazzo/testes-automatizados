# language: pt

Funcionalidade: Validar o contrato ao realizar um cadastro bem-sucedido de deposito
  Como usuário da API
  Quero cadastrar um novo deposito bem-sucedido
  Para que eu consiga validar se o contrato está conforme o esperado

  Cenario: Validar contrato do cadastro bem-sucedido de deposito
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | PLASTICO     |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    E que eu recupere o ID de recipiente criado no contexto
    E que eu tenha os seguintes dados de deposito:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | peso          | 50            |
      | data          | 2025-04-30    |
    Quando eu enviar a requisição para o endpoint "/deposito" de cadastro de deposito
    Então o status code da resposta de deposito deve ser 201
    E que o arquivo de contrato de deposito esperado é o "Cadastro bem-sucedido de deposito"
    Então a resposta da requisição de deposito deve estar em conformidade com o contrato selecionado
