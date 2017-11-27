package test.java.service;

import org.junit.Test;
import main.java.com.septanome.service.ServiceMetier;

public class ServiceMetierTest {
    @Test public void test(){
        ServiceMetier sm = new ServiceMetier();
        sm.initPlan("./src/test/resources/fichiersXML/testPlan.xml");
        sm.initCommande("./src/test/resources/fichiersXML/testLivraisons.xml");
        sm.initPlanLivraison();
        sm.calculerTournee(false);

        System.out.println(sm.getTournee());
    }
}
