
package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.DepositoModel;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CadastroDepositosService {

    final DepositoModel depositoModel = new DepositoModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080/api";
    String depositoId;
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setFieldsDeposito(String field, String value) {
        switch (field) {
            case "recipienteId" -> depositoModel.setRecipienteId(value);
            case "peso" -> depositoModel.setPeso(Integer.parseInt(value));
            case "data" -> depositoModel.setData(value);
            default -> throw new IllegalStateException("Unexpected feld" + field);
        }
    }

    public void createDeposito(String endPoint) {
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(depositoModel);

        System.out.println("Enviando para: " + url);
        System.out.println("Payload: " + bodyToSend);

        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public void retrieveDepositoId() {
        depositoId = String.valueOf(
                gson.fromJson(response.jsonPath().prettify(), model.DepositoModel.class).getDepositoId()
        );
        System.out.println("Depósito ID capturado: " + depositoId);
    }

    public String getDepositoId() {
        return this.depositoId;
    }
    public void deleteDeposito(String endPoint) {
        String url = String.format("%s%s/%s", baseUrl, endPoint, depositoId);

        System.out.println("Enviando para: " + url);

        response = given()
                .accept(ContentType.JSON)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
        }
    }

    public void setContract(String contract) throws IOException {
        switch (contract) {
            case "Cadastro bem-sucedido de deposito" ->
                    jsonSchema = loadJsonFromFile(schemasPath + "cadastro-bem-sucedido-de-deposito.json");
            default ->
                    throw new IllegalStateException("Contrato não esperado: " + contract);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException {
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());

        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());

        return schema.validate(jsonResponseNode);
    }
}