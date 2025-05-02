# language: pt

@contexto_recipiente
Funcionalidade: Cadastro de novo deposito
  Como usuário da API
  Quero cadastrar um novo deposito
  Para que o registro seja salvo corretamente no sistema

  Cenário: Cadastro bem-sucedido de deposito
    Dado que eu tenha os seguintes dados de deposito:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | peso          | 50            |
      | data          | 2025-04-30    |
    Quando eu enviar a requisição para o endpoint "/deposito" de cadastro de deposito
    Então o status code da resposta de deposito deve ser 201

  Cenário: Cadastro de deposito com data inválida
    Dado que eu tenha os seguintes dados de deposito:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | peso          | 50            |
      | data          | 2025-04-31    |
    Quando eu enviar a requisição para o endpoint "/deposito" de cadastro de deposito
    Então o status code da resposta de deposito deve ser 500
    E o corpo de resposta de erro da api de deposito deve retornar a mensagem "Error processing request"

  @contexto_deposito
  Cenário: Exclusão bem-sucedida de deposito
    Quando eu enviar a requisição com o ID para o endpoint "/deposito" de deleção de deposito
    Então o status code da resposta de deposito deve ser 204