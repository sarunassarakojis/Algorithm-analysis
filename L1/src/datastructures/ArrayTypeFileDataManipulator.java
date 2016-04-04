package datastructures;

import datastructures.capabilities.Reversible;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/01.
 */
public class ArrayTypeFileDataManipulator implements FileDataManipulator, Reversible {

    private RandomAccessFile randomAccessFileWithData;
    private List<ElementChange> elementChanges;

    public ArrayTypeFileDataManipulator() {
        elementChanges = new ArrayList<>();
    }

    private class ElementChange {

        private final int previousValue;
        private final int previousValueIndex;

        private ElementChange(int previousValueIndex, int index) {
            this.previousValue = previousValueIndex;
            this.previousValueIndex = index;
        }

        public int getPreviousValue() {
            return previousValue;
        }

        public int getPreviousValueIndex() {
            return previousValueIndex;
        }
    }

    @Override
    public RandomAccessFile generateRandomAccessFileWithData(String fileName, int amountOfData) throws IOException {
        this.randomAccessFileWithData = FileDataGenerator.generateRandomAccessFileWithIntegerData(fileName, amountOfData);

        return this.randomAccessFileWithData;
    }

    @Override
    public RandomAccessFile createNewRandomAccessFile(String fileName) throws IOException {
        Files.deleteIfExists(FileSystems.getDefault().getPath("./", fileName));

        this.randomAccessFileWithData = new RandomAccessFile(fileName, "rw");

        return this.randomAccessFileWithData;
    }

    @Override
    public int getIntFromRandomAccessFile(int position) throws IOException {
        randomAccessFileWithData.seek(position * 4);

        return randomAccessFileWithData.readInt();
    }

    @Override
    public void addValueToDataFile(int newValue) throws IOException {
        randomAccessFileWithData.seek(randomAccessFileWithData.length());
        randomAccessFileWithData.writeInt(newValue);
    }

    @Override
    public void addValueToDataFile(int position, int newValue) throws IOException {
        randomAccessFileWithData.seek(position * 4);

        ElementChange elementChange = new ElementChange(randomAccessFileWithData.readInt(), position);

        randomAccessFileWithData.seek(position * 4);
        randomAccessFileWithData.writeInt(newValue);
        elementChanges.add(elementChange);
    }

    @Override
    public void printRandomAccessFileContents() throws IOException {
        randomAccessFileWithData.seek(0);

        System.out.println("Array type data manipulator file contents:");
        for (long i = 0, size = getSize(); i < size; i++) {
            System.out.println(randomAccessFileWithData.readInt());
        }
    }

    @Override
    public int getIndexInDataFileFromValue(int value) throws IOException {
        randomAccessFileWithData.seek(0);
        for (int position = 0, length = getSize(); position < length; position++) {

            if (getIntFromRandomAccessFile(position) == value) {
                return position;
            }
        }

        return NULL_POINTER;
    }

    @Override
    public void swapElements(int firstValueIndex, int secondValueIndex) throws IOException {
        int temp = getIntFromRandomAccessFile(firstValueIndex);

        addValueToDataFile(firstValueIndex, getIntFromRandomAccessFile(secondValueIndex));
        addValueToDataFile(secondValueIndex, temp);
    }

    @Override
    public int getSize() throws IOException {
        return (int) (randomAccessFileWithData.length() / 4);
    }

    @Override
    public void undo() throws IOException {
        for (ElementChange change : elementChanges) {
            addValueToDataFileNoRecording(change.getPreviousValueIndex(), change.getPreviousValue());
        }
    }

    public void addValueToDataFileNoRecording(int position, int newValue) throws IOException {
        randomAccessFileWithData.seek(position * 4);
        randomAccessFileWithData.writeInt(newValue);
    }


    @Override
    public String toString() {
        try {
            return "Amount of data: " + getSize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.toString();
    }
}
