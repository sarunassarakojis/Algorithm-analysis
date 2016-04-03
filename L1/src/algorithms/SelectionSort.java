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
    public void sortArray(FileDataManipulator arrayManipulator) throws IOException {

    }
}
