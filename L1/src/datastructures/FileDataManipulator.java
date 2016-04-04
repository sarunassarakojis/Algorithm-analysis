package datastructures;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/01.
 */
public interface FileDataManipulator {

    int NULL_POINTER = Integer.MAX_VALUE;

    RandomAccessFile generateRandomAccessFileWithData(String fileName, int amountOfData) throws IOException;

    RandomAccessFile createNewRandomAccessFile(String fileName) throws IOException;

    int getIntFromRandomAccessFile(int position) throws IOException;

    void addValueToDataFile(int newValue) throws IOException;

    void addValueToDataFile(int position, int newValue) throws IOException;

    void printRandomAccessFileContents() throws IOException;

    int getIndexInDataFileFromValue(int value) throws IOException;

    void swapElements(int firstValueIndex, int secondValueIndex) throws IOException;

    int getSize() throws IOException;
}
