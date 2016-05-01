package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Project: L2
 * <p>
 * Created by Šaras on 16/05/01.
 */
public class PointGenerator {

    private static final int LOWER_RANGE = 0;
    private static final int UPPER_RANGE = 100000;

    private PointGenerator() {
        /*
         * This utility class cannot be initialised as
         * it consists of only (static) methods.
         */
    }

    public static List<Point> getListOfRandomGeneratedPoints(int amountOfData) {
        List<Integer> xCoordinates = getListOfIntegerData(amountOfData);
        List<Integer> yCoordinates = getListOfIntegerData(amountOfData);
        List<Point> points = new ArrayList<>(amountOfData);

        for (int i = 0; i < amountOfData; ++i) {
            points.add(new Point(xCoordinates.get(i), yCoordinates.get(i)));
        }

        return points;
    }

    private static List<Integer> getListOfIntegerData(int amountOfData) {
        if (amountOfData > UPPER_RANGE) {
            throw new IllegalArgumentException("Amount of data to generate should be below upper bound, that is: " +
                    UPPER_RANGE + ", passed in value: " + amountOfData);
        }

        List<Integer> values = IntStream.range(LOWER_RANGE, UPPER_RANGE)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.shuffle(values);

        return values.subList(0, amountOfData);
    }
}
