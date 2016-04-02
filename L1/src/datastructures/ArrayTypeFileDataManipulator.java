package datastructures;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/01.
 */
public class ArrayTypeFileDataManipulator implements FileDataManipulator {

    private RandomAccessFile randomAccessFileWithData;

    public ArrayTypeFileDataManipulator() {

    }

    @Override
    public RandomAccessFile generateRandomAccessFileWithData(String fileName, int amountOfData) throws IOException {
        this.randomAccessFileWithData = FileDataGenerator.generateRandomAccessFileWithData(fileName, amountOfData);

        return this.randomAccessFileWithData;
    }

    @Override
    public RandomAccessFile createNewRandomAccessFile(String fileName) throws IOException {
        Files.deleteIfExists(FileSystems.getDefault().getPath("./", fileName));

        this.randomAccessFileWithData = new RandomAccessFile(fileName, "rw");

        return this.randomAccessFileWithData;
    }

    @Override
    public RandomAccessFile getRandomAccessFile() throws IOException {
        return this.randomAccessFileWithData;
    }

    @Override
    public int getIntFromRandomAccessFile(int position) throws IOException {
        randomAccessFileWithData.seek(position * 4);

        return randomAccessFileWithData.readInt();
    }

    @Override
    public void addValueToDataFile(int newValue) throws IOException {

    }

    @Override
    public void addValueToDataFile(int position, int newValue) throws IOException {
        randomAccessFileWithData.seek(position * 4);
        randomAccessFileWithData.writeInt(newValue);
    }

    @Override
    public void printRandomAccessFileContents() throws IOException {
        randomAccessFileWithData.seek(0);

        System.out.println("Array type data manipulator file contents:");
        for (long i = 0, amountOfInts = randomAccessFileWithData.length() / 4; i < amountOfInts; i++) {
            System.out.println(randomAccessFileWithData.readInt());
        }
    }

    @Override
    public void swapElements(int firstValueIndex, int secondValueIndex) throws IOException {

    }

    @Override
    public int getSize() throws IOException {
        return (int) (randomAccessFileWithData.length() / 4);
    }
}
