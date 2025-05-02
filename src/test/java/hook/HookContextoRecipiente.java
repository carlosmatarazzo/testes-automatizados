package hook;

import context.WorldContext;
import io.cucumber.java.Before;
import services.CadastroRecipientesService;

public class HookContextoRecipiente {

    // garantir que o recipiente está criado
    @Before("@contexto_recipiente")
    public void prepararRecipiente() {
        CadastroRecipientesService service = new CadastroRecipientesService();
        service.setFieldsRecipiente("tipo", "PLASTICO");
        service.setFieldsRecipiente("capacidadeTotal", "100");
        service.setFieldsRecipiente("localizacao", "Rua A, 1");
        service.createRecipiente("/recipiente");
        service.retrieveRecipienteId();
        WorldContext.recipienteId = service.getRecipienteId();
        System.out.println(">> Recipiente criado no contexto: " + WorldContext.recipienteId);
    }
}