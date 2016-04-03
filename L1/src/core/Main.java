package core;

import algorithms.BucketSort;
import algorithms.FileDataSorter;
import datastructures.ListTypeFileDataManipulator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ListTypeFileDataManipulator listManipulator = new ListTypeFileDataManipulator();
        FileDataSorter sorter = BucketSort.getInstance();

        listManipulator.generateRandomAccessFileWithData("text.txt", 10);
        listManipulator.generatePointersFile("pointers.txt");
        listManipulator.printRandomAccessFileContents();
        sorter.sortList(listManipulator);
        listManipulator.printRandomAccessFileContents();
        listManipulator.generatePointersFile("pointersX.txt");
        listManipulator.printRandomAccessFileContents();
    }
}
