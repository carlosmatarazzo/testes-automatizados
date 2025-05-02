# language: pt

@contexto_deposito
Funcionalidade: Validação de contrato da resposta de coleta
  Como consumidor da API
  Quero validar a resposta de coleta conforme o contrato JSON
  Para garantir consistência da integração

  Cenário: Resposta de coleta segue contrato
    Dado que eu tenha os seguintes dados de coleta:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | data          | 2025-05-02    |
    Quando eu enviar a requisição para o endpoint "/coleta" de cadastro de coleta
    E que o arquivo de contrato de coleta esperado é o "Cadastro bem-sucedido de coleta"
    Então a resposta da requisição de coleta deve estar em conformidade com o contrato selecionado