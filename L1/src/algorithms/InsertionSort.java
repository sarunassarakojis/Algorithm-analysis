package algorithms;

import datastructures.FileDataAsListManipulator;
import datastructures.FileDataManipulator;

import java.io.IOException;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/02.
 */
public class InsertionSort implements FileDataSorter {

    private InsertionSort() {

    }

    private static class InsertionSortInstanceHolder {
        private static final InsertionSort INSERTION_SORT = new InsertionSort();
    }

    public static InsertionSort getInstance() {
        return InsertionSortInstanceHolder.INSERTION_SORT;
    }

    @Override
    public void sortList(FileDataAsListManipulator listManipulator) throws IOException {
        for (int i = 1, size = listManipulator.getSize(); i < size; i++) {
            int value1 = listManipulator.getIntFromRandomAccessFile(i);
            int j = i;
            int value2;

            while (j > 0 && (value2 = listManipulator.getIntFromRandomAccessFile(j - 1)) < value1) {
                listManipulator.swapElements(value1, value2);
                j--;
            }
        }
    }

    @Override
    public void sortArray(FileDataManipulator arrayManipulator) throws IOException {

    }
}
