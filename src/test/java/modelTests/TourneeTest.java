package modelTests;

import com.septanome.model.Chemin;
import com.septanome.model.Tournee;
import com.septanome.model.Troncon;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TourneeTest {
    @Test
    public void testTourneeConstructor(){
        Troncon t1 = new Troncon(1,0.5,"Avenue des Arts",5);
        Troncon t2 = new Troncon(3,0.3,"Rue de la Physique",1);
        List<Troncon> troncons = Arrays.asList(t1,t2);
        Chemin c1 = new Chemin(t2.getDestinationID(),t1.getOrigineID(),troncons);
        List<Chemin> chemins = Arrays.asList(c1);
        Tournee tourneeUnderTest = new Tournee(chemins);
        assertTrue("Chemin list should match",tourneeUnderTest.getChemins().equals(chemins));
    }
}
