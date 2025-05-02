# language: pt

Funcionalidade: Fluxo completo de controle de capacidade do recipiente
  Como usuário da API
  Quero acompanhar a evolução da capacidade de um recipiente
  Para garantir que notificações e coletas funcionem corretamente

  Cenário: Capacidade atinge limite, notifica, e coleta zera capacidade
    Dado que eu tenha os seguintes dados de recipiente:
      | campo           | valor        |
      | tipo            | PLASTICO     |
      | capacidadeTotal | 100          |
      | localizacao     | Rua A, 1     |
    Quando eu enviar a requisição para o endpoint "/recipiente" de cadastro de recipiente
    E que eu recupere o ID de recipiente criado no contexto

    Dado que eu tenha os seguintes dados de deposito:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | peso          | 100           |
      | data          | 2025-05-02    |
    Quando eu enviar a requisição para o endpoint "/deposito" de cadastro de deposito
    E que eu recupere o ID de deposito criado no contexto
    E que eu consulte os dados do recipiente "/recipiente/{id}" usando o ID do contexto
    Então a capacidade atual do recipiente deve ser igual ou maior que a capacidade total

    Dado que eu tenha os seguintes dados de coleta:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | data          | 2025-05-03    |
    Quando eu enviar a requisição para o endpoint "/coleta" de cadastro de coleta
    E que eu consulte os dados do recipiente "/recipiente/{id}" usando o ID do contexto
    Então a capacidade atual do recipiente deve ser 0