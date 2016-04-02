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

    public static BucketSort getInstance() {
        return BucketSortInstanceHolder.BUCKET_SORTER;
    }

    @Override
    public void sortList(FileDataAsListManipulator listManipulator) throws IOException {
        int firstInt = listManipulator.getIntFromRandomAccessFile(0);
        int minValue = firstInt;
        int maxValue = firstInt;
        int bucketCount;

        for (int i1 = 0, size1 = listManipulator.getSize(); i1 < size1; i1++) {
            int current = listManipulator.getIntFromRandomAccessFile(i1);

            if (current < minValue) {
                minValue = current;
            } else {
                if (current > maxValue) {
                    maxValue = current;
                }
            }
        }

        bucketCount = (maxValue - minValue) / DEFAULT_BUCKET_SIZE + 1;
        List<FileDataAsListManipulator> buckets = getListOfInitializedBuckets(bucketCount);

        distributeValuesToBuckets(listManipulator, buckets, minValue);
        for (int i = 0; i < bucketCount; i++) {
            FileDataAsListManipulator bucket = buckets.get(i);

            bucket.generatePointersFile("pointers" + i + ".txt");
            InsertionSort.getInstance().sortList(bucket);
        }
    }

    private void distributeValuesToBuckets(FileDataAsListManipulator listManipulator,
                                           List<FileDataAsListManipulator> buckets, int minValue) throws IOException {
        for (int i = 0, size = listManipulator.getSize(); i < size; i++) {
            int value = listManipulator.getIntFromRandomAccessFile(i);

            buckets.get((value - minValue) / DEFAULT_BUCKET_SIZE).addValueToDataFile(value);
        }
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


    @Override
    public void sortArray(FileDataManipulator arrayManipulator) throws IOException {

    }
}
