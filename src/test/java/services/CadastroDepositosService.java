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
import context.WorldContext;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CadastroDepositosService {

    private final DepositoModel depositoModel = new DepositoModel();
    public final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public Response response;
    private final String baseUrl = "http://localhost:8080/api";
    private final String schemasPath = "src/test/resources/schemas/";
    private JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setFieldsDeposito(String field, String value) {
        switch (field) {
            case "recipienteId" -> depositoModel.setRecipienteId(value);
            case "peso" -> depositoModel.setPeso(Integer.parseInt(value));
            case "data" -> depositoModel.setData(value);
            default -> throw new IllegalStateException("Campo inesperado: " + field);
        }
    }

    public void createDeposito(String endPoint) {
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(depositoModel);

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
        int id = gson.fromJson(response.jsonPath().prettify(), DepositoModel.class).getDepositoId();
        WorldContext.depositoId = String.valueOf(id);
    }

    public String getDepositoId() {
        return WorldContext.depositoId;
    }

    public void deleteDeposito(String endPoint) {
        String id = WorldContext.depositoId;
        String url = String.format("%s%s/%s", baseUrl, endPoint, id);

        System.out.println(">>> Enviando DELETE para: " + url);

        response = given()
                .accept(ContentType.JSON)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    public void setContract(String contract) throws IOException {
        switch (contract) {
            case "Cadastro bem-sucedido de deposito" ->
                    jsonSchema = loadJsonFromFile(schemasPath + "cadastro-bem-sucedido-de-deposito.json");
            default -> throw new IllegalStateException("Contrato inesperado: " + contract);
        }
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
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