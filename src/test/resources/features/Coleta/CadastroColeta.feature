# language: pt

@contexto_deposito
Funcionalidade: Cadastro de nova coleta
  Como usuário da API
  Quero cadastrar uma nova coleta
  Para registrar a retirada de resíduos do recipiente

  Cenário: Cadastro bem-sucedido de coleta
    Dado que eu tenha os seguintes dados de coleta:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | data          | 2025-05-02    |
    Quando eu enviar a requisição para o endpoint "/coleta" de cadastro de coleta
    Então o status code da resposta de coleta deve ser 201

  Cenário: Cadastro de coleta com data inválida
    Dado que eu tenha os seguintes dados de coleta:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | data          | 2025-02-31    |
    Quando eu enviar a requisição para o endpoint "/coleta" de cadastro de coleta
    Então o status code da resposta de coleta deve ser 500
    E o corpo de resposta de erro da api de coleta deve retornar a mensagem "Error processing request"

  @contexto_coleta
  Cenário: Exclusão bem-sucedida de coleta com contexto completo
    Quando eu enviar a requisição com o ID para o endpoint "/coleta" de deleção de coleta
    Então o status code da resposta de coleta deve ser 204