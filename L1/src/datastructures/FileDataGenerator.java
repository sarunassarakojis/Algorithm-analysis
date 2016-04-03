package datastructures;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/01.
 */
class FileDataGenerator {

    private static final int LOWER_RANGE = 0;
    private static final int UPPER_RANGE = 1000;

    private FileDataGenerator() {

    }

    public static RandomAccessFile generateRandomAccessFileWithData(String fileName, int amountOfData) throws IOException {
        Files.deleteIfExists(FileSystems.getDefault().getPath("./", fileName));

        List<Integer> values = generateValuesArray(amountOfData);
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");

        for (Integer integer : values) {
            randomAccessFile.writeInt(integer);
        }

        return randomAccessFile;
    }

    private static List<Integer> generateValuesArray(int amountOfData) {
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
