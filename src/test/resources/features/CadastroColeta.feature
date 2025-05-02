# language: pt

Funcionalidade: Cadastro de novo coleta
  Como usuário da API
  Quero cadastrar um novo coleta
  Para que o registro seja salvo corretamente no sistema

  Cenário: Cadastro bem-sucedido de coleta
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | PLASTICO     |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    E que eu recupere o ID de recipiente criado no contexto
    E que eu tenha os seguintes dados de coleta:
      | campo         | valor        |
      | recipienteId  | usar_contexto|
      | data          | 2025-04-30   |
    Quando eu enviar a requisição para o endpoint "/coleta" de cadastro de coleta
    Então o status code da resposta de coleta deve ser 201

  Cenário: Cadastro de coleta com data inválida
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
      | data          | 2025-04-31    |
    Quando eu enviar a requisição para o endpoint "/coleta" de cadastro de coleta
    Então o status code da resposta de coleta deve ser 500
    E o corpo de resposta de erro da api de coleta deve retornar a mensagem "Error processing request"