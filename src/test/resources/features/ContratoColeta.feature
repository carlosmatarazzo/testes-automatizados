# language: pt

Funcionalidade: Validar o contrato ao realizar um cadastro bem-sucedido de coleta
  Como usuário da API
  Quero cadastrar um novo coleta bem-sucedido
  Para que eu consiga validar se o contrato está conforme o esperado

  Cenario: Validar contrato do cadastro bem-sucedido de coleta
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | PLASTICO     |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    E que eu recupere o ID de recipiente criado no contexto
    E que eu tenha os seguintes dados de coleta:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | data          | 2025-04-30    |
    Quando eu enviar a requisição para o endpoint "/coleta" de cadastro de coleta
    Então o status code da resposta de coleta deve ser 201
    E que o arquivo de contrato de coleta esperado é o "Cadastro bem-sucedido de coleta"
    Então a resposta da requisição de coleta deve estar em conformidade com o contrato selecionado
