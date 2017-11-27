package modelTests;

import com.septanome.model.Chemin;
import com.septanome.model.Troncon;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class CheminTest {
    @Test public void testCheminConstructor(){
        Troncon t1 = new Troncon(2,0.5,"Rue Alsace",1);
        Troncon t2 = new Troncon(3,0.3,"Rue Lorraine",2);
        List<Troncon> troncons = Arrays.asList(t1,t2);
        Chemin cheminUnderTest = new Chemin(t2.getDestinationID(),t1.getOrigineID(),troncons);
        assertTrue("Troncon list should match",cheminUnderTest.getTroncons().equals(troncons));
        assertTrue("Total length should match",cheminUnderTest.getLongeur()==(t1.getLongeur()+t2.getLongeur()));
        assertTrue("OriginID should match first Troncon origin ID",cheminUnderTest.getOriginePointID()==t1.getOrigineID());
        assertTrue("DestinationID should match last Troncon destination ID",cheminUnderTest.getDestinationPointID()==t2.getDestinationID());
    }

    /**
     * The creation of a Chemin that detination and destination don't match the troncon list
     * destination and origin should fail.
     */
    @Test(expected = Exception.class) public void testCheminIntegrity(){
        Troncon t1 = new Troncon(2,1.4,"Allée L'OM",1);
        List<Troncon> troncons = Arrays.asList(t1);
        Chemin cheminUnderTest = new Chemin(5,4,troncons);
    }
}
