package modelTests;

import com.septanome.model.Livraison;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LivraisonTest {
    @Test public void testLivraisonConstructor(){
        int duree =1;
        int coordX =15427;
        int coordY = 27866;
        long id = 1029591870;
        Livraison livraison = new Livraison(id,coordX,coordY,duree);
        assertEquals(livraison.getDuree(),duree);
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
        assertTrue("Livraison HeureDebut should be earlier than Heure Fin",(livraison.getHeureDeDebut()+livraison.getDuree())==livraison.getHeureDeFin());
    }

    @Test public void testTimeContinuity(){
        int duree =-1;
        int coordX =15427;
        int coordY = 27866;
        long id = 1029591870;
        int heureDeDebut = 2;
        int heureDeFin = 1;
        Livraison livraison = new Livraison(id,coordX,coordY,duree,heureDeDebut,heureDeFin);
        assertTrue("Livraison HeureDebut should be earlier than Heure Fin",livraison.getHeureDeDebut()<=livraison.getHeureDeFin());
    }

    @Test public void testCloneConstructor(){
        int duree =1;
        int coordX =15427;
        int coordY = 27866;
        long id = 1029591870;
        int heureDeDebut = 2;
        int heureDeFin = 3;
        Livraison livraison = new Livraison(id,coordX,coordY,duree,heureDeDebut,heureDeFin);
        Livraison cloneUnderTest = new Livraison(livraison);
        assertEquals(cloneUnderTest.getDuree(),duree);
        assertEquals(cloneUnderTest.getHeureDeDebut(),heureDeDebut);
        assertEquals(cloneUnderTest.getHeureDeFin(),heureDeFin);
    }
}
