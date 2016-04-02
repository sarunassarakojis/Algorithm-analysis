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
        listManipulator.swapElements(1, 3);
        listManipulator.swapElements(1, 3);
        int a = listManipulator.convertNodeIndexToFileIndex(0);
        int b = listManipulator.convertNodeIndexToFileIndex(1);
        int c = listManipulator.convertNodeIndexToFileIndex(2);
        listManipulator.printRandomAccessFileContents();
        sorter.sortList(listManipulator);
    }
}
