package serviceTests;

import com.septanome.exception.BadLinkException;
import com.septanome.exception.ConstructorException;
import com.septanome.model.Chemin;
import com.septanome.model.Tournee;
import org.junit.Test;
import com.septanome.service.ServiceMetier;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ServiceMetierTest {
    @Test public void testGetTournee() throws ConstructorException {
        ServiceMetier sm = new ServiceMetier();
        sm.initPlan("./src/test/resources/fichiersXML/planLyonGrand.xml");
        sm.initCommande("./src/test/resources/fichiersXML/DLGrand10.xml");
        sm.initPlanLivraison();
        try {
            sm.calculerTournee(false);
        }catch (IOException ioe){
            fail("IOException");
        }catch(ClassNotFoundException cne){
            fail("ClassNotFoundException");
        }
        sm.getTournee();
    }
}
