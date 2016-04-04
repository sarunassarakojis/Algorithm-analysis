package core;

import algorithms.BucketSort;
import algorithms.FileDataSorter;
import datastructures.ArrayTypeFileDataManipulator;
import datastructures.ListTypeFileDataManipulator;
import datastructures.TableTypeFileDataManipulator;
import table.Timekeeper;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        int[] amounts = {100, 200, 300, 400, 500, 600, 700};

        /*ListTypeFileDataManipulator listManipulator = new ListTypeFileDataManipulator();
        FileDataSorter sorter = BucketSort.getInstance();

        listManipulator.generateRandomAccessFileWithData("text.txt", amounts[6]);
        listManipulator.generatePointersFile("pointers.txt");

        long t1 = System.currentTimeMillis();
        sorter.sortList(listManipulator);
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);*/


        /*ArrayTypeFileDataManipulator manipulator = new ArrayTypeFileDataManipulator();
        FileDataSorter sorter = BucketSort.getInstance();

        manipulator.generateRandomAccessFileWithData("array.txt", amounts[0]);
        long t1 = System.currentTimeMillis();
        sorter.sortArray(manipulator);
        long t2 = System.currentTimeMillis();

        System.out.println(t2 - t1);*/

        TableTypeFileDataManipulator table = new TableTypeFileDataManipulator();

        table.generateRandomAccessFileWithData("table.txt", amounts[6]);
        long t1 = System.currentTimeMillis();
        table.searchForSameNames();
        long t2 = System.currentTimeMillis();

        System.out.println(t2 - t1);
    }
}
