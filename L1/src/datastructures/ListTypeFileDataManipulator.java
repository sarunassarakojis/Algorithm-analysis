package datastructures;

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
        Files.deleteIfExists(FileSystems.getDefault().getPath("./", fileName));

        RandomAccessFile pointersFile = new RandomAccessFile(fileName, "rw");
        long amountOfData = (randomAccessFileWithData.length() / 2) - 1;
        int nextElementIndex;
        int previousElementIndex = 0;
        this.pointersFile = pointersFile;
        this.headNodeIndex = 0;

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
    public int convertNodeIndexToFileIndex(int nodeIndex) throws IOException {
        if (nodeIndex == 0) {
            return headNodeIndex;
        } else {
            int nodesTraversed = 1;
            NodePointerData data = new NodePointerData(new NodePointerData(headNodeIndex).getNextElementIndex());

            while (nodesTraversed <= nodeIndex) {
                if (nodesTraversed++ == nodeIndex) {
                    return data.getNodeIndex();
                }

                data = new NodePointerData(data.getNextElementIndex());
            }
        }

        return NULL_POINTER;
    }

    @Override
    public int getIntFromRandomAccessFile(int position) throws IOException {
        randomAccessFileWithData.seek(position * 4);

        return randomAccessFileWithData.readInt();
    }

    @Override
    public void addValueToDataFile(int position, int newValue) throws IOException {
        randomAccessFileWithData.seek(position * 4);
        randomAccessFileWithData.writeInt(newValue);
    }

    @Override
    public void addValueToDataFile(int newValue) throws IOException {
        randomAccessFileWithData.seek(randomAccessFileWithData.length());
        randomAccessFileWithData.writeInt(newValue);
    }

    @Override
    public void printRandomAccessFileContents() throws IOException {
        List<NodePointerData> listOfNodePointerData = getListOfNodePointerData();
        int nextNode = listOfNodePointerData.get(headNodeIndex).getNextElementIndex();

        System.out.println("List file contents:");
        System.out.println(getIntFromRandomAccessFile(headNodeIndex));
        while (nextNode != NULL_POINTER) {
            System.out.println(getIntFromRandomAccessFile(nextNode));

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
    public String toString() {
        try {
            return "Head node index --> " + headNodeIndex + ", size: " + getSize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.toString();
    }

    @Override
    public void swapElements(int firstValueIndex, int secondValueIndex) throws IOException {
        NodePointerData firstValueData = new NodePointerData(firstValueIndex);
        NodePointerData secondValueData = new NodePointerData(secondValueIndex);

        if (!areNodesAdjacent(firstValueData, secondValueData)) {
            readjustPreviousNodesIndexes(firstValueData, secondValueData);
            readjustPreviousNodesIndexes(secondValueData, firstValueData);
            readjustNextNodesIndexes(firstValueData, secondValueData);
            readjustNextNodesIndexes(secondValueData, firstValueData);
        } else {
//              if I knew which index is the minimum and which is the maximum there wil be
//              no need for these checks
//            TODO do this instead :
//            int temp1 = firstValueIndex;
//            int temp2 = secondValueIndex;
//            firstValueIndex = Math.min(temp1, temp2);
//            secondValueIndex = Math.max(temp1, temp2);

            if (firstValueIndex < secondValueIndex) {
                if (firstValueData.getPreviousElementIndex() == secondValueData.getNodeIndex()) {
                    readjustPreviousNodesIndexes(secondValueData, firstValueData);
                } else {
                    readjustPreviousNodesIndexes(firstValueData, secondValueData);
                }

                if (secondValueData.getNextElementIndex() == firstValueData.getNodeIndex()) {
                    readjustNextNodesIndexes(firstValueData, secondValueData);
                } else {
                    readjustNextNodesIndexes(secondValueData, firstValueData);
                }

                if (firstValueData.getPreviousElementIndex() == secondValueData.getNodeIndex()) {
                    swapNeighborNodesIndexes(secondValueData, firstValueData);
                } else {
                    swapNeighborNodesIndexes(firstValueData, secondValueData);
                }
                return;
            } else {
                if (secondValueData.getPreviousElementIndex() == firstValueData.getNodeIndex()) {
                    readjustPreviousNodesIndexes(firstValueData, secondValueData);
                } else {
                    readjustPreviousNodesIndexes(secondValueData, firstValueData);
                }

                if (firstValueData.getNextElementIndex() == secondValueData.getNodeIndex()) {
                    readjustNextNodesIndexes(secondValueData, firstValueData);
                } else {
                    readjustNextNodesIndexes(firstValueData, secondValueData);
                }

                if (secondValueData.getPreviousElementIndex() == firstValueData.getNodeIndex()) {
                    swapNeighborNodesIndexes(firstValueData, secondValueData);
                } else {
                    swapNeighborNodesIndexes(secondValueData, firstValueData);
                }
                return;
            }
        }

        swapNodesIndexes(firstValueData, secondValueData);
    }

    private boolean areNodesAdjacent(NodePointerData data1, NodePointerData data2) {
        return data1.getPreviousElementIndex() == data2.getNodeIndex()
                || data1.getNextElementIndex() == data2.getNodeIndex();

    }

    @Override
    public int getIndexInDataFileFromValue(int value) throws IOException {

        randomAccessFileWithData.seek(0);
        for (long position = 0, length = getSize(); position < length; position++) {

            if (getIntFromRandomAccessFile((int) position) == value) {
                return (int) position;
            }
        }

        return NULL_POINTER;
    }

    private void readjustPreviousNodesIndexes(NodePointerData firstData, NodePointerData anotherData) throws IOException {
        if (firstData.getPreviousElementIndex() != NULL_POINTER) {
            NodePointerData previousNodeData = new NodePointerData(firstData.getPreviousElementIndex());

            previousNodeData.setNextElementIndex(anotherData.getNodeIndex());
        } else {
            int newHeadNode = anotherData.getNodeIndex();
            headNodeIndex = newHeadNode;

            new NodePointerData(firstData.getNodeIndex()).setNextElementIndex(newHeadNode);
        }
    }

    private void readjustNextNodesIndexes(NodePointerData firstData, NodePointerData anotherData) throws IOException {
        if (firstData.getNextElementIndex() != NULL_POINTER) {
            NodePointerData nextNodeData = new NodePointerData(firstData.getNextElementIndex());

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

}
