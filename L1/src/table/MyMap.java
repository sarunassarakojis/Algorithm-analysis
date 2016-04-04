package table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @param <K> type of the key
 * @param <V> type of the value mapped to a key
 *
 * @author Sarunas Sarakojis
 */
public class MyMap<K, V> implements MapADTp<K, V> {

    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;

    protected Node<K, V>[] table;
    private List<K> conflictingNames;
    protected int size = 0;
    protected float loadFactor;
    protected HashType hashType;
    protected int rehashesCounter = 0;
    protected int index = 0;

    public MyMap() {
        this(DEFAULT_HASH_TYPE);
    }

    public MyMap(HashType hashType) {
        this(DEFAULT_INITIAL_CAPACITY, hashType);
    }

    public MyMap(int initialCapacity, HashType hashType) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, hashType);
    }

    public MyMap(float loadFactor, HashType hashType) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, hashType);
    }

    public MyMap(int initialCapacity, float loadFactor, HashType hashType) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: "
                    + initialCapacity);
        }

        if ((loadFactor <= 0.0) || (loadFactor > 1.0)) {
            throw new IllegalArgumentException("Illegal load factor: "
                    + loadFactor);
        }

        this.table = new Node[initialCapacity];
        this.loadFactor = loadFactor;
        this.hashType = hashType;
        this.conflictingNames = new ArrayList<>();
    }

    public List<K> getConflictingNames() {
        return conflictingNames;
    }

    @Override
    public int getMaxChainSize() {
        return -1;
    }

    @Override
    public int getRehashesCounter() {
        return rehashesCounter;
    }

    @Override
    public int getTableCapacity() {
        return table.length;
    }

    @Override
    public int getLastUpdatedChain() {
        return -1;
    }

    @Override
    public int getChainsCounter() {
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        index = 0;
        rehashesCounter = 0;
        Arrays.fill(table, null);
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        Node<K, V> node = new Node<>(key, value);
        index = hash(key, hashType);

        if (table[index] == null) {
            table[index] = node;
        } else {
            conflictingNames.add(key);

            for (int i = 1; i < size; i++) {
                int newIndex = (index + i) % size;
                if (table[newIndex] == null) {
                    table[newIndex] = node;
                    break;
                }
            }
        }
        size++;

        if (size > table.length * loadFactor) {
            rehash();
        }

        return value;
    }

    private int hash(K key, HashType hashType) {
        int h = key.hashCode();
        switch (hashType) {
            case DIVISION:
                return Math.abs(h) % table.length;
            case MULTIPLICATION:
                double k = (Math.sqrt(5) - 1) / 2;
                return (int) (((k * Math.abs(h)) % 1) * table.length);
            case JCF7:
                h ^= (h >>> 20) ^ (h >>> 12);
                h = h ^ (h >>> 7) ^ (h >>> 4);
                return h & (table.length - 1);
            case JCF8:
                h = h ^ (h >>> 16);
                return h & (table.length - 1);
            default:
                return Math.abs(h) % table.length;
        }
    }

    private void rehash() {
        MyMap<K, V> newMap = new MyMap<>(table.length * 2, loadFactor, hashType);
        for (Node<K, V> aTable : table) {

            if (aTable != null) {
                newMap.put(aTable.key, aTable.value);
            }
        }

        newMap.rehashesCounter = this.rehashesCounter++;
        table = newMap.table;
    }

    @Override
    public V get(K key) {
        Objects.requireNonNull(key);

        index = hash(key, hashType);
        Node<K, V> node = table[index];

        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        Objects.requireNonNull(key);

        index = hash(key, hashType);
        if (table[index] != null) {
            V value = table[index].value;
            table[index] = null;
            size--;
            return value;
        } else {
            return null;
        }
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    /**
     * class <tt>Node</tt> represents a basic node
     * in the map with type arguments.
     */
    protected class Node<Key, Value> {

        protected Key key;
        protected Value value;

        protected Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        protected Node(Node<Key, Value> node) {
            this(node.key, node.value);
        }

        public Key getKey() {
            return this.key;
        }

        public Value getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return key + " --> " + value;
        }
    }
}
