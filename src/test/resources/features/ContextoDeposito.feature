# language: pt

Funcionalidade: Deletar um deposito
Como usuário da API
Quero conseguir deletar um deposito
Para que o registro seja apagado corretamente no sistema

Cenário: Deve ser possível deletar um deposito
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
  E que eu recupere o ID de deposito criado no contexto
  Quando eu enviar a requisição com o ID para o endpoint "/deposito" de deleção de deposito
  Então o status code da resposta de deposito deve ser 204
