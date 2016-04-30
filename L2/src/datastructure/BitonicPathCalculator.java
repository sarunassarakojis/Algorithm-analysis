package datastructure;

import java.util.List;

/**
 * Project: L2
 * <p>
 * Created by Šaras on 16/04/30.
 */
public class BitonicPathCalculator {

    private static double[][] bitonicLengths;

    /**
     * @param points list whose points are ordered by <em>x coordinate</em>,
     *               more formally: <code>p1.x <= p2.x <= p3.x <= ..... pn.x</code>
     */
    public static void calculateBitonicPath(List<Point> points) {
        bitonicLengths = new double[points.size()][points.size()];
        bitonicLengths[0][0] = 0;
        bitonicLengths[0][1] = points.get(0).getDistanceToPoint(points.get(1));

        for (int j = 2, size = points.size(); j < size; j++) {
            bitonicLengths[0][j] = bitonicLengths[0][j - 1] + points.get(j - 1).getDistanceToPoint(points.get(j));
        }
    }
}
