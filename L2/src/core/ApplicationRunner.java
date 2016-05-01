package core;

import utility.BitonicPathCalculator;
import utility.Point;
import utility.PointGenerator;

import java.util.Collections;
import java.util.List;

/**
 * Project: L2
 * <p>
 * Created by Šaras on 16/04/30.
 */
public class ApplicationRunner {

    public static void main(String[] args) {
        int[] amounts = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000};
        long[][] results = new long[amounts.length][];

        for (int i = 0; i < amounts.length; i++) {
            List<Point> points = PointGenerator.getListOfRandomGeneratedPoints(amounts[i]);
            long t1 = System.currentTimeMillis();

            Collections.sort(points);
            double bitonicPath = BitonicPathCalculator.calculateBitonicPath(points);

            long t2 = System.currentTimeMillis();
            results[i] = new long[]{amounts[i], t2 - t1};

            System.out.println("Finished calculations for amount of: " + amounts[i]);
        }

        printResults(results);
    }

    private static void printResults(long[][] results) {
        System.out.println("--------------------------------------------------");

        for (long[] result : results) {
            System.out.println("| Amount of data: " + result[0] + "\t Execution time: " + result[1] + "\t |");
        }

        System.out.println("--------------------------------------------------");
    }
}
