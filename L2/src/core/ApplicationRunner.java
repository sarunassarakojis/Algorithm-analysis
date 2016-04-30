package core;

import datastructure.BitonicPathCalculator;
import datastructure.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Project: L2
 * <p>
 * Created by Šaras on 16/04/30.
 */
public class ApplicationRunner {

    public static void main(String[] args) {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(7, 4);
        Point p3 = new Point(9, 2);
        Point p4 = new Point(4, 2);
        List<Point> pointList = getListOfSortedPoints(p1, p2, p3, p4);

        BitonicPathCalculator.calculateBitonicPath(pointList);
    }

    private static List<Point> getListOfSortedPoints(Point... points) {
        List<Point> pointList = new ArrayList<>(points.length);

        Collections.addAll(pointList, points);
        Collections.sort(pointList);

        return pointList;
    }

}
