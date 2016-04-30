import datastructure.BitonicPathCalculator;
import datastructure.Point;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Project: L2
 * <p>
 * Created by Šaras on 16/04/30.
 */
public class BitonicPathCalculatorTest {

    @Test
    public void testBitonicTour() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(7, 4);
        Point p3 = new Point(9, 2);
        Point p4 = new Point(4, 2);
        List<Point> pointList = BitonicPathCalculator.getListOfSortedPoints(p1, p2, p3, p4);

        assertEquals("Shortest path should be 14", 14, BitonicPathCalculator.calculateBitonicPath(pointList), 0.1d);
    }
}
