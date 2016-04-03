package algorithms;

import datastructures.FileDataAsListManipulator;
import datastructures.FileDataManipulator;

import java.io.IOException;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/02.
 */
public class SelectionSort implements FileDataSorter {

    private SelectionSort() {

    }

    private static class SelectionSortInstanceHolder {
        private static final SelectionSort INSERTION_SORT = new SelectionSort();
    }

    public static SelectionSort getInstance() {
        return SelectionSortInstanceHolder.INSERTION_SORT;
    }

    @Override
    public void sortList(FileDataAsListManipulator listManipulator) throws IOException {
        int size = listManipulator.getSize();

        for (int i = 0; i < size - 1; i++) {
            int currentIndex = listManipulator.convertNodeIndexToFileIndex(i);
            int minIndex = currentIndex;

            for (int j = i + 1; j < size; j++) {
                int searchIndex = listManipulator.convertNodeIndexToFileIndex(j);

                if (listManipulator.getIntFromRandomAccessFile(searchIndex) <
                        listManipulator.getIntFromRandomAccessFile(minIndex)) {
                    minIndex = searchIndex;
                }
            }

            if (currentIndex != minIndex) {
                listManipulator.swapElements(currentIndex, minIndex);
            }
        }
    }

    @Override
    public void sortArray(FileDataManipulator dataManipulator) throws IOException {
        int size = dataManipulator.getSize();

        for (int currentIndex = 0; currentIndex < size; currentIndex++) {
            int minIndex = currentIndex;

            for (int j = currentIndex + 1; j < size; j++) {

                if (dataManipulator.getIntFromRandomAccessFile(j) <
                        dataManipulator.getIntFromRandomAccessFile(minIndex)) {
                    minIndex = j;
                }
            }

            if (currentIndex != minIndex) {
                dataManipulator.swapElements(currentIndex, minIndex);
            }
        }
    }
}
