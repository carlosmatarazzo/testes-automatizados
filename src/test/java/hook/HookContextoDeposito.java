package hook;

import context.WorldContext;
import services.CadastroDepositosService;
import services.CadastroRecipientesService;
import io.cucumber.java.Before;

public class HookContextoDeposito {

    @Before("@contexto_deposito")
    public void prepararDeposito() {

        // Etapa 1: garantir que o recipiente está criado
        CadastroRecipientesService recipienteService = new CadastroRecipientesService();
        recipienteService.setFieldsRecipiente("tipo", "PLASTICO");
        recipienteService.setFieldsRecipiente("capacidadeTotal", "100");
        recipienteService.setFieldsRecipiente("localizacao", "Rua A, 1");
        recipienteService.createRecipiente("/recipiente");
        recipienteService.retrieveRecipienteId();
        WorldContext.recipienteId = recipienteService.getRecipienteId();

        System.out.println(">>> Recipiente criado no contexto: " + WorldContext.recipienteId);

        // Etapa 2: criar depósito vinculado ao recipiente
        CadastroDepositosService depositoService = new CadastroDepositosService();
        depositoService.setFieldsDeposito("recipienteId", WorldContext.recipienteId);
        depositoService.setFieldsDeposito("peso", "50");
        depositoService.setFieldsDeposito("data", "2025-04-30");
        depositoService.createDeposito("/deposito");
        depositoService.retrieveDepositoId();

        System.out.println(">>> Deposito criado no contexto: " + WorldContext.depositoId);
    }
}
