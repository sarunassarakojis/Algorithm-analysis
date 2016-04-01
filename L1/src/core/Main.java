package core;

import datastructures.ListTypeFileDataManipulator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ListTypeFileDataManipulator listManipulator = new ListTypeFileDataManipulator();

        listManipulator.generateRandomAccessFileWithData("sample.txt", 10000);
        listManipulator.generatePointersFile("samplePointers.txt");
        listManipulator.printOutRandomAccessFileContents();
        listManipulator.swapElements(555, 0);
        listManipulator.printOutRandomAccessFileContents();

    }
}
