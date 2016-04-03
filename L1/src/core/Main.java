package core;

import algorithms.BucketSort;
import algorithms.FileDataSorter;
import datastructures.ArrayTypeFileDataManipulator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        /*ListTypeFileDataManipulator listManipulator = new ListTypeFileDataManipulator();
        FileDataSorter sorter = BucketSort.getInstance();

        listManipulator.generateRandomAccessFileWithData("text.txt", 10);
        listManipulator.generatePointersFile("pointers.txt");
        listManipulator.printRandomAccessFileContents();
        sorter.sortList(listManipulator);
        listManipulator.printRandomAccessFileContents();
        listManipulator.generatePointersFile("pointersX.txt");
        listManipulator.printRandomAccessFileContents();*/

        ArrayTypeFileDataManipulator manipulator = new ArrayTypeFileDataManipulator();
        FileDataSorter sorter = BucketSort.getInstance();

        manipulator.generateRandomAccessFileWithData("array.txt", 5);
        manipulator.printRandomAccessFileContents();
        sorter.sortArray(manipulator);
        manipulator.printRandomAccessFileContents();
        manipulator.undo();
        manipulator.printRandomAccessFileContents();
    }
}
