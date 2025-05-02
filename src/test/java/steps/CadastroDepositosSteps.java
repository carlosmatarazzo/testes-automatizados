package steps;

import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageModel;
import org.junit.Assert;
import services.CadastroDepositosService;
import services.CadastroRecipientesService;
import context.WorldContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroDepositosSteps {

    CadastroDepositosService cadastroDepositosService = new CadastroDepositosService();
    CadastroRecipientesService cadastroRecipientesService = new CadastroRecipientesService(); // 👈 Instância aqui

    @Dado("que eu tenha os seguintes dados de deposito:")
    public void queEuTenhaOsSeguintesDadosDeDeposito(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            String campo = columns.get("campo");
            String valor = columns.get("valor");

            if (campo.equalsIgnoreCase("recipienteId") && valor.equalsIgnoreCase("usar_contexto")) {
                valor = WorldContext.recipienteId; // ✅ usa o ID compartilhado
            }

            cadastroDepositosService.setFieldsDeposito(campo, valor);
        }
    }

    public void queEuTenhaOsSeguintesDadosDaDeposito() {
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de deposito")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeDepositos(String endPoint) {
        cadastroDepositosService.createDeposito(endPoint);
    }

    @Então("o status code da resposta de deposito deve ser {int}")
    public void oStatusDaRespostaDeDepositoDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroDepositosService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de deposito deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeDepositoDeveRetornarAMensagem(String message) {
        ErrorMessageModel errorMessageModel = cadastroDepositosService.gson.fromJson(
                cadastroDepositosService.response.jsonPath().prettify(), ErrorMessageModel.class);
        Assert.assertEquals(message, errorMessageModel.getMessage());
    }

    @Dado("que eu recupere o ID de deposito criado no contexto")
    public void queEuRecupereOIDDaDepositoCriadaNoContexto() {
        cadastroDepositosService.retrieveDepositoId();
        WorldContext.depositoId = cadastroDepositosService.getDepositoId();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de deposito")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeDeposito(String endPoint) {
        cadastroDepositosService.deleteDeposito(endPoint);
    }

    @E("que o arquivo de contrato de deposito esperado é o {string}")
    public void queOArquivoDeContratoDeDepositoEsperadoÉO(String contract) throws IOException {
        cadastroDepositosService.setContract(contract);
    }

    @Então("a resposta da requisição de deposito deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeDepositoDeveEstarEmConformidadeComOContratoSelecionado() throws IOException {
        Set<ValidationMessage> validateResponse = cadastroDepositosService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }
}