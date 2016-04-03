package algorithms;

import datastructures.ArrayTypeFileDataManipulator;
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
        int mainFileOffset = 0;
        List<FileDataAsListManipulator> buckets = getListOfInitializedBuckets(bucketCount);

        distributeValuesToBuckets(listManipulator, buckets, minValue);
        for (int bucketIndex = 0; bucketIndex < bucketCount; bucketIndex++) {
            FileDataAsListManipulator bucket = buckets.get(bucketIndex);

            bucket.generatePointersFile("pointers" + bucketIndex + ".txt");
            SelectionSort.getInstance().sortList(bucket);
            mainFileOffset = mergeBucketOntoMainList(listManipulator, bucket, mainFileOffset);
        }
    }

    private Pair getMinAndMaxValues(FileDataManipulator dataManipulator) throws IOException {
        int firstInt = dataManipulator.getIntFromRandomAccessFile(0);
        int minValue = firstInt;
        int maxValue = firstInt;

        for (int i = 0, size = dataManipulator.getSize(); i < size; i++) {
            int current = dataManipulator.getIntFromRandomAccessFile(i);

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

    private int mergeBucketOntoMainList(FileDataAsListManipulator mainList, FileDataAsListManipulator bucket,
                                        int mainFileOffset) throws IOException {
        int bucketSize = bucket.getSize();

        if (bucketSize != 0) {
            for (int i = 0; i < bucketSize; i++) {
                int bucketElement = bucket.getIntFromRandomAccessFile(bucket.convertNodeIndexToFileIndex(i));
                int positionInMainFile = mainList.convertNodeIndexToFileIndex(mainFileOffset);
                int mainFileElement = mainList.getIntFromRandomAccessFile(positionInMainFile);

                if (bucketElement != mainFileElement) {
                    int index = mainList.getIndexInDataFileFromValue(bucketElement);

                    mainList.swapElements(positionInMainFile, index);
                }
                mainFileOffset++;
            }
        }

        return mainFileOffset;
    }

    @Override
    public void sortArray(FileDataManipulator dataManipulator) throws IOException {
        Pair minAndMax = getMinAndMaxValues(dataManipulator);
        int minValue = minAndMax.getFirstNumber();
        int maxValue = minAndMax.getSecondNumber();
        int bucketCount = (maxValue - minValue) / DEFAULT_BUCKET_SIZE + 1;
        int mainFileOffset = 0;
        List<FileDataManipulator> buckets = getListOfInitializedArrayBuckets(bucketCount);

        distributeValuesToBuckets(dataManipulator, buckets, minValue);
        for (int bucketIndex = 0; bucketIndex < bucketCount; bucketIndex++) {
            FileDataManipulator bucket = buckets.get(bucketIndex);

            SelectionSort.getInstance().sortArray(bucket);
            mainFileOffset = mergeBucketOntoMainList(dataManipulator, bucket, mainFileOffset);
        }
    }

    private List<FileDataManipulator> getListOfInitializedArrayBuckets(int bucketCount) throws IOException {
        List<FileDataManipulator> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            FileDataManipulator bucket = new ArrayTypeFileDataManipulator();

            bucket.createNewRandomAccessFile("arr" + i + ".txt");
            buckets.add(bucket);
        }

        return buckets;
    }

    private void distributeValuesToBuckets(FileDataManipulator listManipulator,
                                           List<FileDataManipulator> buckets, int minValue) throws IOException {
        for (int i = 0, size = listManipulator.getSize(); i < size; i++) {
            int value = listManipulator.getIntFromRandomAccessFile(i);

            buckets.get((value - minValue) / DEFAULT_BUCKET_SIZE).addValueToDataFile(value);
        }
    }

    private int mergeBucketOntoMainList(FileDataManipulator mainList, FileDataManipulator bucket,
                                        int mainFileOffset) throws IOException {
        int bucketSize = bucket.getSize();

        if (bucketSize != 0) {
            for (int i = 0; i < bucketSize; i++) {
                int bucketElement = bucket.getIntFromRandomAccessFile(i);
                int mainFileElement = mainList.getIntFromRandomAccessFile(mainFileOffset);

                if (bucketElement != mainFileElement) {
                    mainList.addValueToDataFile(mainFileOffset, bucketElement);
                }
                mainFileOffset++;
            }
        }

        return mainFileOffset;
    }
}
