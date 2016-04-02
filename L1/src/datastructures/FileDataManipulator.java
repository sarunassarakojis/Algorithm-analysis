package datastructures;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/01.
 */
public interface FileDataManipulator {

    RandomAccessFile generateRandomAccessFileWithData(String fileName, int amountOfData) throws IOException;

    RandomAccessFile createNewRandomAccessFile(String fileName) throws IOException;

    RandomAccessFile getRandomAccessFile() throws IOException;

    int getIntFromRandomAccessFile(int position) throws IOException;

    void addValueToDataFile(int newValue) throws IOException;

    void addValueToDataFile(int position, int newValue) throws IOException;

    void printRandomAccessFileContents() throws IOException;

    void swapElements(int firstValue, int secondValue) throws IOException;

    int getSize() throws IOException;
}
