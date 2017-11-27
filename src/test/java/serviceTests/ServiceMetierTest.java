package serviceTests;

import org.junit.Test;
import com.septanome.service.ServiceMetier;

import java.io.IOException;

public class ServiceMetierTest {
    @Test public void test() throws IOException, ClassNotFoundException {
        ServiceMetier sm = new ServiceMetier();
        sm.initPlan("./src/test/resources/fichiersXML/testPlan.xml");
        sm.initCommande("./src/test/resources/fichiersXML/testLivraisons.xml");
        sm.initPlanLivraison();
        sm.calculerTournee(false);

        System.out.println(sm.getTournee());
    }
}
