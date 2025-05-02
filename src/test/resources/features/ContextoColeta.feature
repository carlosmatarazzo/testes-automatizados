# language: pt

Funcionalidade: Deletar um coleta
Como usuário da API
Quero conseguir deletar um coleta
Para que o registro seja apagado corretamente no sistema

Cenário: Deve ser possível deletar um coleta
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
  E que eu recupere o ID de coleta criado no contexto
  Quando eu enviar a requisição com o ID para o endpoint "/coleta" de deleção de coleta
  Então o status code da resposta de coleta deve ser 204
