package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Project: L2
 * <p>
 * Created by Šaras on 16/04/30.
 */
public class BitonicPathCalculator {

    private static final int UNDEFINED = -1;

    public static List<Point> getListOfSortedPoints(Point... points) {
        List<Point> pointList = new ArrayList<>(points.length);

        Collections.addAll(pointList, points);
        Collections.sort(pointList);

        return pointList;
    }

    /**
     * @param points list whose points are ordered by <em>x coordinate</em>,
     *               more formally: <code>p1.x <= p2.x <= p3.x <= ..... pn.x</code>
     */
    public static double calculateBitonicPath(List<Point> points) {
        double[][] bitonicLengths = new double[points.size()][points.size()];
        bitonicLengths[0][0] = 0;
        bitonicLengths[0][1] = points.get(0).getDistanceToPoint(points.get(1));

        for (int j = 2, size = points.size(); j < size; j++) {
            bitonicLengths[0][j] = bitonicLengths[0][j - 1] + points.get(j - 1).getDistanceToPoint(points.get(j));
        }

        for (int row = 1, size = points.size(); row < size; row++) {
            for (int column = row; column < size; column++) {
                bitonicLengths[row][column] = UNDEFINED;

                if (row == column || row == column - 1) {
                    double temp;
                    double min = Double.MAX_VALUE;

                    for (int counter = 0; counter < row; counter++) {
                        temp = bitonicLengths[counter][row] + points.get(counter).getDistanceToPoint(points.get(column));
                        min = temp < min ? temp : min;
                    }

                    bitonicLengths[row][column] = min;
                } else {
                    bitonicLengths[row][column] = bitonicLengths[row][column - 1] +
                            points.get(column - 1).getDistanceToPoint(points.get(column));
                }
            }
        }

        return bitonicLengths[points.size() - 1][points.size() - 1];
    }
}
