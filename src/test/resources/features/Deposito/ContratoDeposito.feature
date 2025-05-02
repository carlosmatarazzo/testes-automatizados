# language: pt

@contexto_recipiente
Funcionalidade: Validação de contrato de resposta de depósito
  Como consumidor da API
  Quero validar se a resposta está em conformidade com o contrato JSON
  Para garantir a integridade da integração

  Cenário: Resposta de depósito segue contrato
    Dado que eu tenha os seguintes dados de deposito:
      | campo         | valor         |
      | recipienteId  | usar_contexto |
      | peso          | 50            |
      | data          | 2025-04-30    |
    Quando eu enviar a requisição para o endpoint "/deposito" de cadastro de deposito
    E que o arquivo de contrato de deposito esperado é o "Cadastro bem-sucedido de deposito"
    Então a resposta da requisição de deposito deve estar em conformidade com o contrato selecionado

