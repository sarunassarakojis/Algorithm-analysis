package table;

import java.util.Arrays;
import java.util.Objects;

/**
 * Porų ("maping'ų") raktas-reikšmė objektų kolekcijos - atvaizdžio realizacija
 * maišos lentele, kolizijas sprendžiant atskirų grandinėlių (angl. separate
 * chaining) metodu. Neužmirškite, jei poros raktas - nuosavos klasės objektas,
 * pvz. klasės Automobilis objektas, klasėje būtina perdengti metodus
 * equals(Object o) ir hashCode().
 *
 * @param <K> atvaizdžio raktas
 * @param <V> atvaizdžio reikšmė
 *
 * @Užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 *
 * @author darius.matulis@ktu.lt
 */
public class MapKTU<K, V> implements MapADTp<K, V> {

    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;

    // Maišos lentelė
    protected Node<K, V>[] table;
    // Lentelėje esančių raktas-reikšmė porų kiekis
    protected int size = 0;
    // Apkrovimo faktorius
    protected float loadFactor;
    // Maišos metodas
    protected HashType ht;
    //--------------------------------------------------------------------------
    //  Maišos lentelės įvertinimo parametrai
    //--------------------------------------------------------------------------
    // Maksimalus suformuotos maišos lentelės grandinėlės ilgis
    protected int maxChainSize = 0;
    // Permaišymų kiekis
    protected int rehashesCounter = 0;
    // Paskutinės patalpintos poros grandinėlės indeksas maišos lentelėje
    protected int lastUpdatedChain = 0;
    // Lentelės grandinėlių skaičius     
    protected int chainsCounter = 0;
    // Einamas poros indeksas maišos lentelėje
    protected int index = 0;

    /* Klasėje sukurti 4 perkloti konstruktoriai, nustatantys atskirus maišos 
     * lentelės parametrus. Jei kuris nors parametras nėra nustatomas - 
     * priskiriama standartinė reikšmė.
     */
    public MapKTU() {
        this(DEFAULT_HASH_TYPE);
    }

    public MapKTU(HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, ht);
    }

    public MapKTU(int initialCapacity, HashType ht) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    public MapKTU(float loadFactor, HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, ht);
    }

    public MapKTU(int initialCapacity, float loadFactor, HashType ht) {
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
        this.ht = ht;
    }

    /**
     * Patikrinama ar atvaizdis yra tuščias.
     *
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Grąžinamas atvaizdyje esančių porų kiekis.
     *
     * @return Grąžinamas atvaizdyje esančių porų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvalomas atvaizdis.
     */
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        index = 0;
        lastUpdatedChain = 0;
        maxChainSize = 0;
        rehashesCounter = 0;
        chainsCounter = 0;
    }

    /**
     * Patikrinama ar pora egzistuoja atvaizdyje.
     *
     * @param key raktas.
     * @return Patikrinama ar pora egzistuoja atvaizdyje.
     */
    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    public boolean containsValue(V value) {
        if (table == null)
            return false;
        for (int i = 0; i < size; i++) {
            Node<K, V> node = table[i];
            if (node != null && node.value == value)
                return true;
        }
        return false;
    }

    public double averageChainLength() {
        int chains = 0;
        int chainsN = 0;
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            if (node != null) {
                chainsN++;
                while (node != null) {
                    chains++;
                    node = node.next;
                }
            }
        }
        return (double) chains / (double) chainsN;
    }

    public int emptyCells() {
        final int[] n = {0};

        Arrays.stream(this.table).forEach(node -> {
            if (node == null) {
                n[0]++;
            }
        });

        return n[0];
    }

    /**
     * Atvaizdis papildomas nauja pora.
     *
     * @param key raktas,
     * @param value reikšmė.
     * @return Atvaizdis papildomas nauja pora.
     */
    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null in put(Key key, Value value)");
        }

        index = hash(key, ht);

        if (table[index] == null) {
            chainsCounter++;
        }

        Node<K, V> node = getInChain(key, table[index]);

        if (node == null) {
            table[index] = new Node<>(key, value, table[index]);
            size++;

            if (size > table.length * loadFactor) {
                rehash(table[index]);
            } else {
                lastUpdatedChain = index;
            }
        } else {
            node.value = value;
            lastUpdatedChain = index;
        }

        return value;
    }

    /**
     * Grąžinama atvaizdžio poros reikšmė.
     *
     * @return Grąžinama atvaizdžio poros reikšmė.
     *
     * @param key raktas.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in get(Key key)");
        }

        index = hash(key, ht);
        Node<K, V> node = getInChain(key, table[index]);
        return (node != null) ? node.value : null;
    }

    /**
     * Pora pašalinama iš atvaizdžio.
     *
     * @param key Pora pašalinama iš atvaizdžio.
     * @return key raktas.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in remove(Key key)");
        }

        index = hash(key, ht);
        Node<K, V> previous = null;
        for (Node<K, V> n = table[index]; n != null; n = n.next) {
            if ((n.key).equals(key)) {
                if (previous == null) {
                    table[index] = n.next;
                } else {
                    previous.next = n.next;
                }

                size--;

                if (table[index] == null) {
                    chainsCounter--;
                }

                return n.value;
            }
            previous = n;
        }
        return null;
    }

    /**
     * Permaišymas
     *
     * @param node
     */
    private void rehash(Node<K, V> node) {
        MapKTU mapKTU
                = new MapKTU(table.length * 2, loadFactor, ht);
        for (int i = 0; i < table.length; i++) {
            while (table[i] != null) {
                if (table[i].equals(node)) {
                    lastUpdatedChain = i;
                }

                mapKTU.put(table[i].key, table[i].value);
                table[i] = table[i].next;
            }
        }
        table = mapKTU.table;
        maxChainSize = mapKTU.maxChainSize;
        chainsCounter = mapKTU.chainsCounter;
        rehashesCounter++;
    }

    /**
     * Maišos funkcijos skaičiavimas: pagal rakto maišos kodą apskaičiuojamas
     * atvaizdžio poros indeksas maišos lentelės masyve
     *
     * @param key
     * @param hashType
     * @return
     */
    private int hash(K key, HashType hashType) {
        int h = key.hashCode();
        if (hashType == HashType.DIVISION) {
            return Math.abs(h) % table.length;
        } else if (hashType == HashType.MULTIPLICATION) {
            double k = (Math.sqrt(5) - 1) / 2;
            return (int) (((k * Math.abs(h)) % 1) * table.length);
        } else if (hashType == HashType.JCF7) {
            h ^= (h >>> 20) ^ (h >>> 12);
            h = h ^ (h >>> 7) ^ (h >>> 4);
            return h & (table.length - 1);
        } else if (hashType == HashType.JCF8) {
            h = h ^ (h >>> 16);
            return h & (table.length - 1);
        } else {
            return Math.abs(h) % table.length;
        }
    }

    /**
     * Method iterates from starting node which is given as this method
     * argument until it reaches the same node with same key.
     *
     * @param key the key to search
     * @param node the starting point
     * @return
     */
    private Node<K, V> getInChain(K key, Node<K, V> node) {
        Objects.requireNonNull(key);
        int chainSize = 0;

        for (Node<K, V> n = node; n != null; n = n.next) {
            chainSize++;

            if ((n.key).equals(key)) {
                return n;
            }
        }
        maxChainSize = Math.max(maxChainSize, chainSize + 1);
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Node<K, V> node : table) {
            if (node != null) {
                for (Node<K, V> n = node; n != null; n = n.next) {
                    result.append(n.toString()).append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    /**
     * Grąžina maksimalų grandinėlės ilgį.
     *
     * @return Maksimalus grandinėlės ilgis.
     */
    @Override
    public int getMaxChainSize() {
        return maxChainSize;
    }

    /**
     * Grąžina formuojant maišos lentelę įvykusių permaišymų kiekį.
     *
     * @return Permaišymų kiekis.
     */
    @Override
    public int getRehashesCounter() {
        return rehashesCounter;
    }

    /**
     * Grąžina maišos lentelės talpą.
     *
     * @return Maišos lentelės talpa.
     */
    @Override
    public int getTableCapacity() {
        return table.length;
    }

    /**
     * Grąžina paskutinės papildytos grandinėlės indeksą.
     *
     * @return Paskutinės papildytos grandinėlės indeksas.
     */
    @Override
    public int getLastUpdatedChain() {
        return lastUpdatedChain;
    }

    /**
     * Grąžina grandinėlių kiekį.
     *
     * @return Grandinėlių kiekis.
     */
    @Override
    public int getChainsCounter() {
        return chainsCounter;
    }

    protected class Node<Key, Value> {

        // Raktas        
        protected Key key;
        // Reikšmė
        protected Value value;
        // Rodyklė į sekantį grandinėlės mazgą
        protected Node<Key, Value> next;

        protected Node() {
        }

        protected Node(Key key, Value value, Node<Key, Value> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
