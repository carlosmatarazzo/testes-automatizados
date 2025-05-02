# language: pt

Funcionalidade: Deletar um recipiente
  Como usuário da API
  Quero conseguir deletar um recipiente
  Para que o registro seja apagado corretamente no sistema

  Contexto: Cadastro bem-sucedido de recipiente
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | PLASTICO     |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    Então o status code da resposta de recipiente deve ser 201

  Cenário: Deve ser possível deletar um recipiente
    Dado que eu recupere o ID de recipiente criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/recipiente" de deleção de recipiente
    Então o status code da resposta de recipiente deve ser 204