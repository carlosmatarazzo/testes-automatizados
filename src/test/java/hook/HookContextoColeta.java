package hook;

import context.WorldContext;
import io.cucumber.java.Before;
import services.CadastroRecipientesService;
import services.CadastroDepositosService;
import services.CadastroColetasService;

public class HookContextoColeta {

    @Before("@contexto_coleta")
    public void prepararColeta() {
        // 1. Criar recipiente
        CadastroRecipientesService recipienteService = new CadastroRecipientesService();
        recipienteService.setFieldsRecipiente("tipo", "PLASTICO");
        recipienteService.setFieldsRecipiente("capacidadeTotal", "100");
        recipienteService.setFieldsRecipiente("localizacao", "Rua A, 1");
        recipienteService.createRecipiente("/recipiente");
        recipienteService.retrieveRecipienteId();
        WorldContext.recipienteId = recipienteService.getRecipienteId();
        System.out.println(">>> Recipiente criado: " + WorldContext.recipienteId);

        // 2. Criar depósito
        CadastroDepositosService depositoService = new CadastroDepositosService();
        depositoService.setFieldsDeposito("recipienteId", WorldContext.recipienteId);
        depositoService.setFieldsDeposito("peso", "50");
        depositoService.setFieldsDeposito("data", "2025-05-01");
        depositoService.createDeposito("/deposito");
        depositoService.retrieveDepositoId();
        WorldContext.depositoId = depositoService.getDepositoId();
        System.out.println(">>> Deposito criado: " + WorldContext.depositoId);

        // 3. Criar coleta
        CadastroColetasService coletaService = new CadastroColetasService();
        coletaService.setFieldsColeta("recipienteId", WorldContext.recipienteId);
        coletaService.setFieldsColeta("data", "2025-05-02");
        coletaService.createColeta("/coleta");
        coletaService.retrieveColetaId();
        System.out.println(">>> Coleta criada: " + WorldContext.coletaId);
    }
}
