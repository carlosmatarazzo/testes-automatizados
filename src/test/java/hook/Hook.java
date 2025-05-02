package hook;

import context.WorldContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hook {

    @Before
    public void setUp() {
        System.out.println(">> Iniciando novo cenário de teste...");
        WorldContext.reset();
    }

    @After
    public void tearDown() {
        System.out.println("<< Finalizando cenário de teste...");
    }
}