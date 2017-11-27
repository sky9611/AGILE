package modelTests;

import com.septanome.model.Livraison;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LivraisonTest {
    @Test public void testLivraisonConstructor(){
        int duree =1;
        int coordX =15427;
        int coordY = 27866;
        long id = 1029591870;
        Livraison livraison = new Livraison(id,coordX,coordY,duree);
        assertEquals(livraison.getDuree(),duree);
        assertEquals(livraison.getHeureDeDebut(),-1);
        assertEquals(livraison.getHeureDeFin(),-1);
    }
    @Test public void testLivraisonLongConstructor(){
        int duree =1;
        int coordX =15427;
        int coordY = 27866;
        long id = 1029591870;
        int heureDeDebut = 2;
        int heureDeFin = 3;
        Livraison livraison = new Livraison(id,coordX,coordY,duree,heureDeDebut,heureDeFin);
        assertEquals(livraison.getDuree(),duree);
        assertEquals(livraison.getHeureDeDebut(),heureDeDebut);
        assertEquals(livraison.getHeureDeFin(),heureDeFin);
    }

    /**
     * heureDeDebut + heureDeFin == duree ?
     */
    @Test public void testTimeIntegrity(){
        int duree =15;
        int coordX =15427;
        int coordY = 27866;
        long id = 1029591870;
        int heureDeDebut = 2;
        int heureDeFin = 3;
        Livraison livraison = new Livraison(id,coordX,coordY,duree,heureDeDebut,heureDeFin);
        //TODO error if succeeds
    }
}
