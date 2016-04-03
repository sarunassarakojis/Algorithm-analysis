package algorithms;

import datastructures.FileDataAsListManipulator;
import datastructures.FileDataManipulator;
import datastructures.ListTypeFileDataManipulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/02.
 */
public class BucketSort implements FileDataSorter {

    public static final int DEFAULT_BUCKET_SIZE = 5;

    private BucketSort() {

    }

    private static class BucketSortInstanceHolder {
        private static final BucketSort BUCKET_SORTER = new BucketSort();
    }

    private class Pair {

        private final int firstNumber;
        private final int secondNumber;

        public Pair(int firstNumber, int secondNuber) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNuber;
        }

        public int getFirstNumber() {
            return this.firstNumber;
        }

        public int getSecondNumber() {
            return this.secondNumber;
        }
    }

    public static BucketSort getInstance() {
        return BucketSortInstanceHolder.BUCKET_SORTER;
    }

    @Override
    public void sortList(FileDataAsListManipulator listManipulator) throws IOException {
        Pair minAndMax = getMinAndMaxValues(listManipulator);
        int minValue = minAndMax.getFirstNumber();
        int maxValue = minAndMax.getSecondNumber();
        int bucketCount = (maxValue - minValue) / DEFAULT_BUCKET_SIZE + 1;
        List<FileDataAsListManipulator> buckets = getListOfInitializedBuckets(bucketCount);

        distributeValuesToBuckets(listManipulator, buckets, minValue);
        for (int bucketIndex = 0; bucketIndex < bucketCount; bucketIndex++) {
            FileDataAsListManipulator bucket = buckets.get(bucketIndex);

            bucket.generatePointersFile("pointers" + bucketIndex + ".txt");
            SelectionSort.getInstance().sortList(bucket);
        }
    }

    private Pair getMinAndMaxValues(FileDataAsListManipulator listManipulator) throws IOException {
        int firstInt = listManipulator.getIntFromRandomAccessFile(0);
        int minValue = firstInt;
        int maxValue = firstInt;

        for (int i = 0, size = listManipulator.getSize(); i < size; i++) {
            int current = listManipulator.getIntFromRandomAccessFile(i);

            if (current < minValue) {
                minValue = current;
            } else {
                if (current > maxValue) {
                    maxValue = current;
                }
            }
        }

        return new Pair(minValue, maxValue);
    }

    private List<FileDataAsListManipulator> getListOfInitializedBuckets(int bucketCount) throws IOException {
        List<FileDataAsListManipulator> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            ListTypeFileDataManipulator manipulator = new ListTypeFileDataManipulator();

            manipulator.createNewRandomAccessFile("file" + i + ".txt");
            buckets.add(manipulator);
        }

        return buckets;
    }

    private void distributeValuesToBuckets(FileDataAsListManipulator listManipulator,
                                           List<FileDataAsListManipulator> buckets, int minValue) throws IOException {
        for (int i = 0, size = listManipulator.getSize(); i < size; i++) {
            int value = listManipulator.getIntFromRandomAccessFile(i);

            buckets.get((value - minValue) / DEFAULT_BUCKET_SIZE).addValueToDataFile(value);
        }
    }

    private void mergeBucketOntoMainList(FileDataAsListManipulator listManipulator, FileDataAsListManipulator bucket)
            throws IOException {
        int bucketSize = bucket.getSize();

        if (bucketSize != 0) {
            for (int i = 0; i < bucketSize; i++) {

            }
        }
    }

    @Override
    public void sortArray(FileDataManipulator arrayManipulator) throws IOException {

    }
}
