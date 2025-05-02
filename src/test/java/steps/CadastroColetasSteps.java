package steps;

import com.networknt.schema.ValidationMessage;
import context.WorldContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageModel;
import org.junit.Assert;
import services.CadastroColetasService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroColetasSteps {

    CadastroColetasService service = new CadastroColetasService();

    @Dado("que eu tenha os seguintes dados de coleta:")
    public void queEuTenhaOsSeguintesDadosDeColeta(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            String campo = columns.get("campo");
            String valor = columns.get("valor");
            if ("recipienteId".equalsIgnoreCase(campo) && "usar_contexto".equalsIgnoreCase(valor)) {
                valor = WorldContext.recipienteId;
            }
            service.setFieldsColeta(campo, valor);
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de coleta")
    public void enviarRequisicaoCadastro(String endPoint) {
        service.createColeta(endPoint);
    }

    @Então("o status code da resposta de coleta deve ser {int}")
    public void statusCodeDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, service.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de coleta deve retornar a mensagem {string}")
    public void corpoErroMensagem(String mensagem) {
        ErrorMessageModel error = service.gson.fromJson(service.response.jsonPath().prettify(), ErrorMessageModel.class);
        Assert.assertEquals(mensagem, error.getMessage());
    }

    @Dado("que eu recupere o ID de coleta criado no contexto")
    public void recuperarIdColetaContexto() {
        service.retrieveColetaId();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de coleta")
    public void enviarRequisicaoDelete(String endPoint) {
        service.deleteColeta(endPoint);
    }

    @E("que o arquivo de contrato de coleta esperado é o {string}")
    public void carregarContrato(String contrato) throws IOException {
        service.setContract(contrato);
    }

    @Então("a resposta da requisição de coleta deve estar em conformidade com o contrato selecionado")
    public void validarContrato() throws IOException {
        Set<ValidationMessage> erros = service.validateResponseAgainstSchema();
        Assert.assertTrue("Contrato inválido: " + erros, erros.isEmpty());
    }
}