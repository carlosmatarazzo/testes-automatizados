
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
import model.ColetaModel;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CadastroColetasService {

    final ColetaModel coletaModel = new ColetaModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080/api";
    String coletaId;
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setFieldsColeta(String field, String value) {
        switch (field) {
            case "recipienteId" -> coletaModel.setRecipienteId(value);
            case "data" -> coletaModel.setData(value);
            default -> throw new IllegalStateException("Unexpected feld" + field);
        }
    }

    public void createColeta(String endPoint) {
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(coletaModel);

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

    public void retrieveColetaId() {
        coletaId = String.valueOf(
                gson.fromJson(response.jsonPath().prettify(), ColetaModel.class).getColetaId()
        );
        System.out.println("Depósito ID capturado: " + coletaId);
    }

    public String getColetaId() {
        return this.coletaId;
    }
    public void deleteColeta(String endPoint) {
        String url = String.format("%s%s/%s", baseUrl, endPoint, coletaId);

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
            case "Cadastro bem-sucedido de coleta" ->
                    jsonSchema = loadJsonFromFile(schemasPath + "cadastro-bem-sucedido-de-coleta.json");
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