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

    RandomAccessFile getRandomAccessFile() throws IOException;

    int readIntFromRandomAccessFile(int position) throws IOException;

    void setIntInRandomAccessFile(int position, int newValue) throws IOException;

    void printOutRandomAccessFileContents() throws IOException;

    void swapElements(int firstValue, int secondValue) throws IOException;

    int getSize() throws IOException;
}
