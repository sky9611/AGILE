
package test.java.model;

import main.java.com.septanome.model.Point;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PointTest {
    @Test public void testPointConstructor(){
        Point pointUnderTest = new Point(1,1,1);
        assertEquals(pointUnderTest.getCoordX(),1);
        assertEquals(pointUnderTest.getCoordY(),1);
        assertEquals(pointUnderTest.getId(),1);
    }
}
