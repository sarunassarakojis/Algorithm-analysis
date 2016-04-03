package algorithms;

import datastructures.FileDataAsListManipulator;
import datastructures.FileDataManipulator;

import java.io.IOException;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/02.
 */
public interface FileDataSorter {

    void sortList(FileDataAsListManipulator listManipulator) throws IOException;

    void sortArray(FileDataManipulator dataManipulator) throws IOException;
}
