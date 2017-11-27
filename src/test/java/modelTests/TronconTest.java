package modelTests;

import com.septanome.model.Troncon;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TronconTest {
    @Test public void testTronconConstructor(){
        Troncon tronconUnderTest = new Troncon(1,0.5,"Rue de Marseille",2);
        assertEquals( tronconUnderTest.getDestinationID(),1 );
        assertEquals(tronconUnderTest.getOrigineID(),2);
        assertTrue("Troncon length should match",tronconUnderTest.getLongeur()==0.5);
        assertTrue("Troncon name should match",tronconUnderTest.getNom().equals("Rue de Marseille"));
    }
}
