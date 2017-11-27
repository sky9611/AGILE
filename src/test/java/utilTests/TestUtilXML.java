package utilTests;

import com.septanome.model.Point;
import com.septanome.util.UtilXML;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestUtilXML {
    private final String EXTRA_SMALL_XML ="./src/test/resources/fichiersXML/planLyonTresPetit.xml";
    
     //This test will not verify the content of the loaded points!
     
    @Test public void testCanLoadPoint(){
        UtilXML u = new UtilXML();
        assertNotNull("We must be able to load points",
                u.loadPoint(EXTRA_SMALL_XML));
    }

    
     //Tests whether the loaded values match the values in the XML file
    
    @Test public void testPointLoaded(){
        UtilXML u = new UtilXML();
        HashMap<Long, Point> points = u.loadPoint(EXTRA_SMALL_XML);
        Point pointUnderTest = points.get((long)1029591870);
        assertTrue("IDs between key and object must match",
                pointUnderTest.getId() ==(long) 1029591870);
        assertTrue(pointUnderTest.getCoordX()== 15427);
        assertTrue(pointUnderTest.getCoordY() == 27866);
    }
}
