package modelTests;

import com.septanome.exception.BadLinkException;
import com.septanome.exception.EmptyListException;
import com.septanome.model.Chemin;
import com.septanome.model.Troncon;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class CheminTest {
    @Test public void testCheminConstructor (){
        Troncon t1 = new Troncon(2,0.5,"Rue Alsace",1);
        Troncon t2 = new Troncon(3,0.3,"Rue Lorraine",2);
        List<Troncon> troncons = Arrays.asList(t1,t2);
        try {
            Chemin cheminUnderTest = new Chemin(t2.getDestinationID(), t1.getOrigineID(), troncons);
            assertTrue("Troncon list should match", cheminUnderTest.getTroncons().equals(troncons));
            assertTrue("Total length should match", cheminUnderTest.getLongeur() == (t1.getLongeur() + t2.getLongeur()));
            assertTrue("OriginID should match first Troncon origin ID", cheminUnderTest.getOriginePointID() == t1.getOrigineID());
            assertTrue("DestinationID should match last Troncon destination ID", cheminUnderTest.getDestinationPointID() == t2.getDestinationID());
        }catch (BadLinkException ble){
            fail("BadLink fail not expected");
        }catch (EmptyListException ele){
            fail("Empty list exception not expected");
        }
    }

    /**
     * The creation of a Chemin that detination and destination don't match the troncon list
     * destination and origin should fail.
     */
    @Test public void testCheminIntegrity(){
        Troncon t1 = new Troncon(2,1.4,"Allée L'OM",1);
        List<Troncon> troncons = Arrays.asList(t1);
        try {
            Chemin cheminUnderTest = new Chemin(5, 4, troncons);
        }catch(Exception e){
            return;
        }
        assertTrue("The creation of a Chemin that detination and origin don't match the troncon list" +
                "destination and origin should fail.",false);
    }
    /**
     * The troncons should be connected
     */
    @Test public void testCheminContinuity(){
        Troncon t1 = new Troncon(2,0.5,"Rue de la liberte",1);
        Troncon t2 = new Troncon(3,0.3,"Rue Pierre Baratin",2);
        Troncon t3 = new Troncon(5,0.3,"Rue Pierre Baratin",4);
        List<Troncon> troncons = Arrays.asList(t1,t2,t3);
        try {
            Chemin cheminUnderTest = new Chemin(t2.getDestinationID(), t1.getOrigineID(), troncons);
        }catch(Exception e){
            return;
        }
        assertTrue("The troncon list should contain connected troncons",false);

    }

}
