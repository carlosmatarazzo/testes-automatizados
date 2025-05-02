package steps;

import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageModel;
import org.junit.Assert;
import services.CadastroRecipientesService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import context.WorldContext;

public class CadastroRecipientesSteps {

    CadastroRecipientesService cadastroRecipientesService = new CadastroRecipientesService();

    @Dado("que eu tenha os seguintes dados de recipiente:")
    public void queEuTenhaOsSeguintesDadosDeRecipiente(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            cadastroRecipientesService.setFieldsRecipiente(columns.get("campo"), columns.get("valor"));
        }
    }

    public void queEuTenhaOsSeguintesDadosDaRecipiente() {
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de recipiente")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeRecipientes(String endPoint) {
        cadastroRecipientesService.createRecipiente(endPoint);
    }

    @Então("o status code da resposta de recipiente deve ser {int}")
    public void oStatusDaRespostaDeRecipienteDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroRecipientesService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de recipiente deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeRecipienteDeveRetornarAMensagem(String message) {
        ErrorMessageModel errorMessageModel = cadastroRecipientesService.gson.fromJson(
                cadastroRecipientesService.response.jsonPath().prettify(), ErrorMessageModel.class);
        Assert.assertEquals(message, errorMessageModel.getMessage());
    }

    @Dado("que eu recupere o ID de recipiente criado no contexto")
    public void queEuRecupereOIDDaRecipienteCriadaNoContexto() {
        cadastroRecipientesService.retrieveRecipienteId();
        WorldContext.recipienteId = cadastroRecipientesService.getRecipienteId();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de recipiente")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeRecipiente(String endPoint) {
        cadastroRecipientesService.deleteRecipiente(endPoint);
    }

    @E("que o arquivo de contrato de recipiente esperado é o {string}")
    public void queOArquivoDeContratoDeRecipienteEsperadoÉO(String contract) throws IOException {
        cadastroRecipientesService.setContract(contract);
    }

    @Então("a resposta da requisição de recipiente deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeRecipienteDeveEstarEmConformidadeComOContratoSelecionado() throws IOException {
        Set<ValidationMessage> validateResponse = cadastroRecipientesService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }
}