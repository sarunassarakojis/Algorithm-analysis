package datastructures;

import table.HashType;
import table.MyMap;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/04.
 */
public class TableTypeFileDataManipulator implements FileDataManipulator {

    private RandomAccessFile randomAccessFileWithData;

    public TableTypeFileDataManipulator() {

    }

    @Override
    public RandomAccessFile generateRandomAccessFileWithData(String fileName, int amountOfData) throws IOException {
        this.randomAccessFileWithData = FileDataGenerator.generateRandomAccessFileWithStringData(fileName, amountOfData);

        return randomAccessFileWithData;
    }

    public void searchForSameNames() throws IOException {
        MyMap<String, String> names = new MyMap<>(10000, HashType.JCF8);
        String line;

        randomAccessFileWithData.seek(0);
        while ((line = randomAccessFileWithData.readLine()) != null) {
            names.put(line, line);
        }

        names.printConflictingNames();
    }

    @Override
    public RandomAccessFile createNewRandomAccessFile(String fileName) throws IOException {
        return null;
    }

    @Override
    public int getIntFromRandomAccessFile(int position) throws IOException {
        return 0;
    }

    @Override
    public void addValueToDataFile(int newValue) throws IOException {

    }

    @Override
    public void addValueToDataFile(int position, int newValue) throws IOException {

    }

    @Override
    public void printRandomAccessFileContents() throws IOException {
        String line;

        randomAccessFileWithData.seek(0);
        while ((line = randomAccessFileWithData.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Override
    public int getIndexInDataFileFromValue(int value) throws IOException {
        return 0;
    }

    @Override
    public void swapElements(int firstValueIndex, int secondValueIndex) throws IOException {

    }

    @Override
    public int getSize() throws IOException {
        return 0;
    }
}
