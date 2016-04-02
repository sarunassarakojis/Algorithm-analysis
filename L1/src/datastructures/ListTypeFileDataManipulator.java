package datastructures;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/01.
 */
public class ListTypeFileDataManipulator implements FileDataAsListManipulator {

    private RandomAccessFile randomAccessFileWithData;
    private int headNodeIndex;
    private RandomAccessFile pointersFile;

    public ListTypeFileDataManipulator() {
        headNodeIndex = 0;
    }

    public class NodePointerData {

        private int previousElementIndex;
        private int nextElementIndex;
        private int nodeIndex;

        public NodePointerData(int nodeIndex) throws IOException {
            pointersFile.seek(nodeIndex * 8);

            this.nodeIndex = nodeIndex;
            this.previousElementIndex = pointersFile.readInt();
            this.nextElementIndex = pointersFile.readInt();
        }

        public int getNextElementIndex() {
            return this.nextElementIndex;
        }

        public int getPreviousElementIndex() {
            return this.previousElementIndex;
        }

        public int getNodeIndex() {
            return nodeIndex;
        }

        public void setNextElementIndex(int newNextElementIndex) throws IOException {
            this.nextElementIndex = newNextElementIndex;

            pointersFile.seek(nodeIndex * 8 + 4);
            pointersFile.writeInt(newNextElementIndex);
        }

        public void setPreviousElementIndex(int newPreviousElementIndex) throws IOException {
            this.previousElementIndex = newPreviousElementIndex;

            pointersFile.seek(nodeIndex * 8);
            pointersFile.writeInt(newPreviousElementIndex);
        }

        public void setNodeIndexes(int newNextElementIndex, int newPreviousElementIndex) throws IOException {
            setPreviousElementIndex(newPreviousElementIndex);
            setNextElementIndex(newNextElementIndex);
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();

            if (previousElementIndex == NULL_POINTER) {
                output.append("null");
            } else {
                output.append(previousElementIndex);
            }
            output.append(" <-- ").append(nodeIndex).append(" --> ");
            if (nextElementIndex == NULL_POINTER) {
                output.append("null");
            } else {
                output.append(nextElementIndex);
            }

            return output.toString();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof NodePointerData && this.nodeIndex == ((NodePointerData) obj).nodeIndex;
        }
    }

    @Override
    public RandomAccessFile generatePointersFile(String fileName) throws IOException {
        RandomAccessFile pointersFile = new RandomAccessFile(fileName, "rw");
        long amountOfData = (randomAccessFileWithData.length() / 2) - 1;
        int nextElementIndex;
        int previousElementIndex = 0;
        this.pointersFile = pointersFile;

        pointersFile.writeInt(NULL_POINTER);

        for (int i = 1; i < amountOfData; i++) {

            if (i % 2 == 0) {
                pointersFile.writeInt(previousElementIndex++);
            } else {
                nextElementIndex = previousElementIndex + 1;

                pointersFile.writeInt(nextElementIndex);
            }
        }

        pointersFile.writeInt(NULL_POINTER);

        return pointersFile;
    }

    @Override
    public RandomAccessFile getPointersFile() {
        return this.pointersFile;
    }

    @Override
    public RandomAccessFile generateRandomAccessFileWithData(String fileName, int amountOfData) throws IOException {
        this.randomAccessFileWithData = FileDataGenerator.generateRandomAccessFileWithData(fileName, amountOfData);

        return this.randomAccessFileWithData;
    }

    @Override
    public RandomAccessFile getRandomAccessFile() throws IOException {
        return this.randomAccessFileWithData;
    }

    @Override
    public int readIntFromRandomAccessFile(int position) throws IOException {
        randomAccessFileWithData.seek(position * 4);

        return randomAccessFileWithData.readInt();
    }

    @Override
    public void setIntInRandomAccessFile(int position, int newValue) throws IOException {
        randomAccessFileWithData.seek(position * 4);
        randomAccessFileWithData.writeInt(newValue);
    }

    @Override
    public void printOutRandomAccessFileContents() throws IOException {
        List<NodePointerData> listOfNodePointerData = getListOfNodePointerData();
        int nextNode = listOfNodePointerData.get(headNodeIndex).getNextElementIndex();

        System.out.println("List file contents:");
        System.out.println(readIntFromRandomAccessFile(headNodeIndex));
        while (nextNode != NULL_POINTER) {
            System.out.println(readIntFromRandomAccessFile(nextNode));

            nextNode = listOfNodePointerData.get(nextNode).getNextElementIndex();
        }
    }

    private List<NodePointerData> getListOfNodePointerData() throws IOException {
        List<NodePointerData> nodePointers = new ArrayList<>();

        pointersFile.seek(0);
        for (int i = 0, size = getSize(); i < size; i++) {
            nodePointers.add(new NodePointerData(i));
        }

        return nodePointers;
    }

    @Override
    public int getSize() throws IOException {
        return (int) randomAccessFileWithData.length() / 4;
    }

    @Override
    public void swapElements(int firstValue, int secondValue) throws IOException {
        int firstValueIndex = getIndexInDataFileFromValue(firstValue);
        int secondValueIndex = getIndexInDataFileFromValue(secondValue);
        NodePointerData firstValueData = new NodePointerData(firstValueIndex);
        NodePointerData secondValueData = new NodePointerData(secondValueIndex);

        if (Math.abs(firstValueIndex - secondValueIndex) > 1) {
            readjustPreviousNodesIndexes(firstValueIndex, secondValueData);
            readjustPreviousNodesIndexes(secondValueIndex, firstValueData);
            readjustNextNodesIndexes(firstValueIndex, secondValueData);
            readjustNextNodesIndexes(secondValueIndex, firstValueData);
        } else {
            if (firstValueIndex < secondValueIndex) {
                readjustPreviousNodesIndexes(firstValueIndex, secondValueData);
                readjustNextNodesIndexes(secondValueIndex, firstValueData);
                swapNeighborNodesIndexes(firstValueData, secondValueData);
                return;
            } else {
                readjustPreviousNodesIndexes(secondValueIndex, firstValueData);
                readjustNextNodesIndexes(firstValueIndex, secondValueData);
                swapNeighborNodesIndexes(secondValueData, firstValueData);
                return;
            }
        }

        swapNodesIndexes(firstValueData, secondValueData);
    }

    private int getIndexInDataFileFromValue(int value) throws IOException {

        randomAccessFileWithData.seek(0);
        for (long position = 0, length = randomAccessFileWithData.length() / 4; position < length; position++) {

            if (readIntFromRandomAccessFile((int) position) == value) {
                return (int) position;
            }
        }

        return NULL_POINTER;
    }

    private void readjustPreviousNodesIndexes(int nodeIndex, NodePointerData anotherData) throws IOException {
        if (nodeIndex - 1 >= 0) {
            NodePointerData previousNodeData = new NodePointerData(nodeIndex - 1);

            previousNodeData.setNextElementIndex(anotherData.getNodeIndex());
        } else {
            int newHeadNode = anotherData.getNodeIndex();
            headNodeIndex = newHeadNode;

            new NodePointerData(0).setNextElementIndex(newHeadNode);
        }
    }

    private void readjustNextNodesIndexes(int nodeIndex, NodePointerData anotherData) throws IOException {
        if (nodeIndex + 1 < getSize()) {
            NodePointerData nextNodeData = new NodePointerData(nodeIndex + 1);

            nextNodeData.setPreviousElementIndex(anotherData.getNodeIndex());
        }
    }

    private void swapNeighborNodesIndexes(NodePointerData data1, NodePointerData data2) throws IOException {
        int prevTemp = data1.getPreviousElementIndex();

        data1.setPreviousElementIndex(data1.getNextElementIndex());
        data1.setNextElementIndex(data2.getNextElementIndex());
        data2.setNextElementIndex(data2.getPreviousElementIndex());
        data2.setPreviousElementIndex(prevTemp);
    }

    private void swapNodesIndexes(NodePointerData data1, NodePointerData data2) throws IOException {
        int nextTemp = data1.getNextElementIndex();
        int prevTemp = data1.getPreviousElementIndex();
        data1.setNodeIndexes(data2.getNextElementIndex(), data2.getPreviousElementIndex());
        data2.setNodeIndexes(nextTemp, prevTemp);
    }

    /**
     * Method used mainly for debugging.
     *
     * @throws IOException
     */
    @Deprecated
    private void printOutPointersFile() throws IOException {
        pointersFile.seek(0);

        System.out.println("Pointers file contents:");
        for (long i = 0, length = pointersFile.length() / 4; i < length; i++) {
            System.out.println(pointersFile.readInt());
        }
    }

}
