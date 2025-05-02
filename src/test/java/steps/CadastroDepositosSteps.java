package steps;

import com.networknt.schema.ValidationMessage;
import context.WorldContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageModel;
import org.junit.Assert;
import services.CadastroDepositosService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroDepositosSteps {

    CadastroDepositosService service = new CadastroDepositosService();

    @Dado("que eu tenha os seguintes dados de deposito:")
    public void queEuTenhaOsSeguintesDadosDeDeposito(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            String campo = columns.get("campo");
            String valor = columns.get("valor");
            if ("recipienteId".equalsIgnoreCase(campo) && "usar_contexto".equalsIgnoreCase(valor)) {
                valor = WorldContext.recipienteId;
            }
            service.setFieldsDeposito(campo, valor);
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de deposito")
    public void enviarRequisicaoCadastro(String endPoint) {
        service.createDeposito(endPoint);
    }

    @Então("o status code da resposta de deposito deve ser {int}")
    public void statusCodeDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, service.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de deposito deve retornar a mensagem {string}")
    public void corpoErroMensagem(String mensagem) {
        ErrorMessageModel error = service.gson.fromJson(service.response.jsonPath().prettify(), ErrorMessageModel.class);
        Assert.assertEquals(mensagem, error.getMessage());
    }

    @Dado("que eu recupere o ID de deposito criado no contexto")
    public void recuperarIdDepositoContexto() {
        service.retrieveDepositoId();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de deposito")
    public void enviarRequisicaoDelete(String endPoint) {
        service.deleteDeposito(endPoint);
    }

    @E("que o arquivo de contrato de deposito esperado é o {string}")
    public void carregarContrato(String contrato) throws IOException {
        service.setContract(contrato);
    }

    @Então("a resposta da requisição de deposito deve estar em conformidade com o contrato selecionado")
    public void validarContrato() throws IOException {
        Set<ValidationMessage> erros = service.validateResponseAgainstSchema();
        Assert.assertTrue("Contrato inválido: " + erros, erros.isEmpty());
    }
}