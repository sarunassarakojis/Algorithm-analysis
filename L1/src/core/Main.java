package core;

import datastructures.ListTypeFileDataManipulator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ListTypeFileDataManipulator listManipulator = new ListTypeFileDataManipulator();

        listManipulator.generateRandomAccessFileWithData("sample.txt", 5);
        listManipulator.generatePointersFile("samplePointers.txt");
        listManipulator.swapElements(1, 3);
        listManipulator.printOutRandomAccessFileContents();

    }
}
