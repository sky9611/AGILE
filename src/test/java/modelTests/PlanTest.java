package modelTests;

import com.septanome.model.Plan;
import com.septanome.model.Point;
import com.septanome.model.Troncon;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

public class PlanTest {
    @Test public void testConstructor(){
        Point a = new Point(1,1,1);
        Point b = new Point(2,2,1);
        Troncon troncon = new Troncon(1,1,"Rue de Marseille",2);

        HashMap<Long, Point> points = new HashMap<>();
        points.put((long)1,a);
        points.put((long)2,b);

        HashMap<Long, HashMap<Long, Troncon>> troncons = new HashMap<Long, HashMap<Long, Troncon>>();
        HashMap<Long, Troncon> h = new HashMap<Long, Troncon>();
        h.put(troncon.getDestinationID(),troncon);
        troncons.put(troncon.getOrigineID(),h);
        Plan planUnderTest = new Plan(points,troncons);
        assertTrue("Points hashmap should be the same",planUnderTest.getPointsMap().equals(points));
        assertEquals(planUnderTest.getTronconsMap(),troncons);
    }
}
