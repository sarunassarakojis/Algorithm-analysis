package datastructures;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/01.
 */
public interface FileDataAsListManipulator extends FileDataManipulator {

    int NULL_POINTER = Integer.MAX_VALUE;

    RandomAccessFile generatePointersFile(String fileName) throws IOException;

    RandomAccessFile getPointersFile();

    int convertNodeIndexToFileIndex(int nodeIndex) throws IOException;

    int getIndexInDataFileFromValue(int value) throws IOException;
}
