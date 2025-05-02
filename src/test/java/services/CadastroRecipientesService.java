
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
import model.RecipienteModel;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CadastroRecipientesService {

    final RecipienteModel recipienteModel = new RecipienteModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080/api";
    String recipienteId;
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setFieldsRecipiente(String field, String value) {
        switch (field) {
            //case "" -> recipienteModel.set(Integer.parseInt(value));
            case "tipo" -> recipienteModel.setTipo(value);
            case "capacidadeTotal" -> recipienteModel.setCapacidadeTotal(Integer.parseInt(value));
            case "localizacao" -> recipienteModel.setLocalizacao(value);
            default -> throw new IllegalStateException("Unexpected feld" + field);
        }
    }

    public void createRecipiente(String endPoint) {
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(recipienteModel);

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

    public void retrieveRecipienteId() {
        recipienteId = String.valueOf(gson.fromJson(response.jsonPath().prettify(), RecipienteModel.class).getRecipienteId());
        System.out.println("Recipiente ID capturado: " + recipienteId);
    }

    public String getRecipienteId() {
        return this.recipienteId;
    }

    public void deleteRecipiente(String endPoint) {
        String url = String.format("%s%s/%s", baseUrl, endPoint, recipienteId);

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
            case "Cadastro bem-sucedido de recipiente" -> jsonSchema = loadJsonFromFile(schemasPath + "cadastro-bem-sucedido-de-recipiente.json");
            default -> throw new IllegalStateException("Unexpected contract" + contract);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException {

        // Obter o corpo da resposta como String e converter para JSONObject
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        // Configurar o JsonSchemaFactory e criar o JsonSchema
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());

        // Converter o JSON de resposta para JsonNode
        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());

        // Validar o JSON de resposta contra o esquema
        Set<ValidationMessage> schemaValidationErrors = schema.validate(jsonResponseNode);

        return schemaValidationErrors;

    }
}