package steps;

import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageModel;
import org.junit.Assert;
import services.CadastroColetasService;
import services.CadastroRecipientesService;
import context.WorldContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroColetasSteps {

    CadastroColetasService cadastroColetasService = new CadastroColetasService();
    CadastroRecipientesService cadastroRecipientesService = new CadastroRecipientesService(); // 👈 Instância aqui

    @Dado("que eu tenha os seguintes dados de coleta:")
    public void queEuTenhaOsSeguintesDadosDeColeta(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            String campo = columns.get("campo");
            String valor = columns.get("valor");

            if (campo.equalsIgnoreCase("recipienteId") && valor.equalsIgnoreCase("usar_contexto")) {
                valor = WorldContext.recipienteId; // ✅ usa o ID compartilhado
            }

            cadastroColetasService.setFieldsColeta(campo, valor);
        }
    }

    public void queEuTenhaOsSeguintesDadosDaColeta() {
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de coleta")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeColetas(String endPoint) {
        cadastroColetasService.createColeta(endPoint);
    }

    @Então("o status code da resposta de coleta deve ser {int}")
    public void oStatusDaRespostaDeDepositoDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroColetasService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de coleta deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeDepositoDeveRetornarAMensagem(String message) {
        ErrorMessageModel errorMessageModel = cadastroColetasService.gson.fromJson(
                cadastroColetasService.response.jsonPath().prettify(), ErrorMessageModel.class);
        Assert.assertEquals(message, errorMessageModel.getMessage());
    }

    @Dado("que eu recupere o ID de coleta criado no contexto")
    public void queEuRecupereOIDDaColetaCriadaNoContexto() {
        cadastroColetasService.retrieveColetaId();
        WorldContext.coletaId = cadastroColetasService.getColetaId();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de coleta")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeColeta(String endPoint) {
        cadastroColetasService.deleteColeta(endPoint);
    }

    @E("que o arquivo de contrato de coleta esperado é o {string}")
    public void queOArquivoDeContratoDeColetaEsperadoÉO(String contract) throws IOException {
        cadastroColetasService.setContract(contract);
    }

    @Então("a resposta da requisição de coleta deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeColetaDeveEstarEmConformidadeComOContratoSelecionado() throws IOException {
        Set<ValidationMessage> validateResponse = cadastroColetasService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }
}