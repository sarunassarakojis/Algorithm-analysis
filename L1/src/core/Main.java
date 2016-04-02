package core;

import algorithms.BucketSort;
import algorithms.FileDataSorter;
import datastructures.ListTypeFileDataManipulator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ListTypeFileDataManipulator listManipulator = new ListTypeFileDataManipulator();
        FileDataSorter sorter = BucketSort.getInstance();

        listManipulator.generateRandomAccessFileWithData("text.txt", 5);
        listManipulator.generatePointersFile("pointers.txt");
        int ind1 = listManipulator.convertNodeIndexToFileIndex(0);
        int ind2 = listManipulator.convertNodeIndexToFileIndex(1);

        listManipulator.swapElements(1, 0);
        listManipulator.printRandomAccessFileContents();

        ind1 = listManipulator.convertNodeIndexToFileIndex(0);
        ind2 = listManipulator.convertNodeIndexToFileIndex(1);

        listManipulator.swapElements(1, 0);
        listManipulator.printRandomAccessFileContents();
    }
}
