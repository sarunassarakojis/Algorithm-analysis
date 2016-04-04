package datastructures;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Project: L1
 * <p>
 * Created by saras on 16/04/01.
 */
class FileDataGenerator {

    private static final int LOWER_RANGE = 0;
    private static final int UPPER_RANGE = 1000;

    private FileDataGenerator() {

    }

    public static RandomAccessFile generateRandomAccessFileWithIntegerData(String fileName, int amountOfData)
            throws IOException {
        deleteFileIfExists(fileName);

        List<Integer> values = generateValuesArray(amountOfData);
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");

        for (Integer integer : values) {
            randomAccessFile.writeInt(integer);
        }

        return randomAccessFile;
    }

    private static void deleteFileIfExists(String fileName) throws IOException {
        Files.deleteIfExists(FileSystems.getDefault().getPath("./", fileName));
    }

    private static List<Integer> generateValuesArray(int amountOfData) {
        if (amountOfData > UPPER_RANGE) {
            throw new IllegalArgumentException("Amount of data to generate should be below upper bound, that is: " +
                    UPPER_RANGE + ", passed in value: " + amountOfData);
        }

        List<Integer> values = IntStream.range(LOWER_RANGE, UPPER_RANGE)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.shuffle(values);
        return values.subList(0, amountOfData);
    }

    public static RandomAccessFile generateRandomAccessFileWithStringData(String fileName, int amountOfData)
            throws IOException {
        deleteFileIfExists(fileName);

        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
        List<String> names = getListOfNames(amountOfData);

        for (String name : names) {
            randomAccessFile.writeChars(name + "\n");
        }

        return randomAccessFile;
    }

    private static List<String> getListOfNames(int amountOfData) {
        Random random = new Random();
        List<String> names = new ArrayList<>(amountOfData);
        String[] possibleNames = {"Petraitis", "Jonaitis", "Kazlauskas", "Jankausas", "Petrauskas", "Stankevicius",
                "Vasiliauskas", "Zukauskas", "Butkus", "Paulauskas", "Urbonas", "Kavaliauskas", "Kazlauskiene",
                "Jankauskiene", "Petrauskiene", "Stankeviciene", "Vasiliauskiene", "Zukauskiene", "Butkiene",
                "Paulauskiene", "Urboniene", "Kavaliauskiene", "Navickiene", "Baranauskas", "Pocius", "Baranauskiene",
                "Pociene", "Sakalauskas", "Sakalauskiene", "Aakjaer", "Aalto", "Aames", "Aasen", "Aav", "Abaris",
                "Abarius", "Abartis", "Abaryte", "Abasidze", "Abbott", "Abdalchakas", "Abelkis", "Abisala", "Abraitis",
                "Abraityte", "Abramauskas", "Abramaviciene", "Abramavicius", "Abramikiene", "Abramoniene", "Abramovas",
                "Abramovicius", "Abromaitis", "Abromaityte", "Abromaviciene", "Abromavicius", "Abrutis", "Abrutyte",
                "Abukevicius", "Acukas", "Adam", "Adamaitis", "Adamkavicius", "Adamkeviciene", "Adamkevicius",
                "Adamoniene", "Adamonis", "Adams", "Adaskeviciene", "Addo", "Adleris", "Adomaitiene", "Adomaitis",
                "Adomaityte", "Adomauskas", "Adomavicius", "Adomaviciute", "Adomenas", "Adomoniene", "Adomonis",
                "Adomonyte", "Adomynas", "Agintas", "Agripa", "Ahmed", "Aidukas", "Aizinas", "Aizinbudas", "Aiziniene",
                "Ajauskas", "Akelaitiene", "Akelaitis", "Akelaityte", "Akmenskis", "Akramavicius", "Aksamitauskas",
                "Aksomaitis", "Akstinas", "Akstinaviciene", "Akstinavicius", "Akulavicius", "Alantas", "Albavicius",
                "Albertynas", "Albinas", "Albrechtas", "Albrechtiene", "Albridzio", "Albuziene", "Alechnaviciene",
                "Alechnavicius", "Alekna", "Aleknaite", "Aleknaviciene", "Aleknavicius", "Alekneviciene", "Alekniene",
                "Aleknonis", "Aleksa", "Aleksaite", "Aleksandravicius", "Aleksandraviciute", "Aleksandrovas",
                "Aleksandrovicius", "Aleksejunas", "Aleksiene", "Aleksis", "Aleksiunas", "Aleksiuniene", "Aleksynas",
                "Aleliunas", "Alesionka", "Aleskeviciene", "Aleskevicius", "Aleskeviciute", "Algirdaitis", "Alijevas",
                "Alijosius", "Alimas", "Alinskaite", "Alisanka", "Alisas", "Alisauskas", "Alisauskiene", "Aliukevicius",
                "Aliukonis", "Aliulis", "Alksninis", "Allen", "Almenas", "Alminas", "Almonaitis", "Almonaityte",
                "Alonso", "Alperavicius", "Alsauskas", "Alsauskiene", "Alseika", "Alseikiene", "Alsenas", "Alseniskis",
                "Alsys", "Altman", "Alton", "Altonenas", "Alunderis", "Alyta", "Ambrasaitis", "Ambrasas", "Ambrasiene",
                "Ambraska", "Ambrazaityte", "Ambrazas", "Ambrazevicius", "Ambraziejus", "Ambraziene", "Ambraziunas",
                "Ambrose", "Ambrozaitis"};

        for (int i = 0; i < amountOfData; i++) {
            names.add(possibleNames[random.nextInt(possibleNames.length - 1)]);
        }

        return names;
    }
}
