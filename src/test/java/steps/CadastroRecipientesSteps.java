package steps;

import com.networknt.schema.ValidationMessage;
import context.WorldContext;
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

public class CadastroRecipientesSteps {

    CadastroRecipientesService service = new CadastroRecipientesService();
    //private final CadastroRecipientesService cadastroRecipientesService = new CadastroRecipientesService();

    @Dado("que eu tenha os seguintes dados de recipiente:")
    public void queEuTenhaOsSeguintesDadosDeRecipiente(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            service.setFieldsRecipiente(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de recipiente")
    public void enviarRequisicaoCadastro(String endPoint) {
        service.createRecipiente(endPoint);
    }

    @Então("o status code da resposta de recipiente deve ser {int}")
    public void statusCodeDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, service.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de recipiente deve retornar a mensagem {string}")
    public void corpoErroMensagem(String mensagem) {
        ErrorMessageModel error = service.gson.fromJson(service.response.jsonPath().prettify(), ErrorMessageModel.class);
        Assert.assertEquals(mensagem, error.getMessage());
    }

    @Dado("que eu recupere o ID de recipiente criado no contexto")
    public void recuperarIdRecipienteContexto() {
        Assert.assertNotNull("Resposta da criação do recipiente está nula!", service.response);
        service.retrieveRecipienteId();
        String id = service.getRecipienteId();
        Assert.assertNotNull("ID do recipiente não está disponível no contexto!", id);
        WorldContext.recipienteId = id;
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de recipiente")
    public void enviarRequisicaoDelete(String endPoint) {
        service.deleteRecipiente(endPoint);
    }

    @E("que o arquivo de contrato de recipiente esperado é o {string}")
    public void carregarContrato(String contrato) throws IOException {
        service.setContract(contrato);
    }

    @Então("a resposta da requisição de recipiente deve estar em conformidade com o contrato selecionado")
    public void validarContrato() throws IOException {
        Set<ValidationMessage> erros = service.validateResponseAgainstSchema();
        Assert.assertTrue("Contrato inválido: " + erros, erros.isEmpty());
    }

}