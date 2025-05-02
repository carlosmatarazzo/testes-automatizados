package steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import context.WorldContext;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class FluxoCompletoSteps {

    @E("que eu consulte os dados do recipiente {string} usando o ID do contexto")
    public void consultarRecipientePorId(String endpointComPath) {
        String endpoint = endpointComPath.replace("{id}", WorldContext.recipienteId);
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("http://localhost:8080/api" + endpoint)
                .then()
                .extract()
                .response();

        WorldContext.recipientResponseJson = response.asString();
    }

    @Então("a capacidade atual do recipiente deve ser igual ou maior que a capacidade total")
    public void validarCapacidadeMaiorOuIgual() {
        JsonObject json = JsonParser.parseString(WorldContext.recipientResponseJson).getAsJsonObject();
        int atual = json.get("capacidadeAtual").getAsInt();
        int total = json.get("capacidadeTotal").getAsInt();
        Assert.assertTrue("Capacidade atual não atingiu o total", atual >= total);
    }

    @Então("a capacidade atual do recipiente deve ser {int}")
    public void validarCapacidadeZerada(int valorEsperado) {
        JsonObject json = JsonParser.parseString(WorldContext.recipientResponseJson).getAsJsonObject();
        int atual = json.get("capacidadeAtual").getAsInt();
        Assert.assertEquals("Capacidade atual não está conforme o esperado", valorEsperado, atual);
    }
}