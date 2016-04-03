package datastructures;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Random;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/01.
 */
class FileDataGenerator {

    private FileDataGenerator() {

    }

    public static RandomAccessFile generateRandomAccessFileWithData(String fileName, int amountOfData) throws IOException {
        Files.deleteIfExists(FileSystems.getDefault().getPath("./", fileName));

        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
        Random randomNumbers = new Random();

        for (int i = 0; i < amountOfData; i++) {
            randomAccessFile.writeInt(randomNumbers.nextInt(100));
        }

        return randomAccessFile;
    }
}
