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
    public void testFourPointsBitonicTour() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(7, 4);
        Point p3 = new Point(9, 2);
        Point p4 = new Point(4, 2);
        List<Point> pointList = BitonicPathCalculator.getListOfSortedPoints(p1, p2, p3, p4);

        assertEquals(14, BitonicPathCalculator.calculateBitonicPath(pointList), 0.1d);
    }

    @Test
    public void testEightPointBitonicTour() {
        Point p1 = new Point(4, 6);
        Point p2 = new Point(6, 6);
        Point p3 = new Point(8, 5);
        Point p4 = new Point(13, 4);
        Point p5 = new Point(14, 3);
        Point p6 = new Point(12, 1);
        Point p7 = new Point(7, 1);
        Point p8 = new Point(5, 1);
        List<Point> pointList = BitonicPathCalculator.getListOfSortedPoints(p1, p2, p3, p4, p5, p6, p7, p8);

        assertEquals(25.7d, BitonicPathCalculator.calculateBitonicPath(pointList), 0.1d);
    }
}
