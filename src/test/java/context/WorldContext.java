package context;

public class WorldContext {
    public static String recipienteId = null;
    public static String depositoId = null;
    public static String coletaId = null;
    public static String recipientResponseJson = null;

    public static void reset() {
        recipienteId = null;
        depositoId = null;
        coletaId = null;
        recipientResponseJson = null;
    }
}