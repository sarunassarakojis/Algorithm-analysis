package table;

import javax.swing.JTextArea;

/**
 *
 */
public class Timekeeper {

    int[] tyrimoImtis;
    private long startTime, finishTime;
    private final long startTimeTot;
    private long finishTimeTot;
    private double sumTime;
    private int tyrimoInd;
    private int kiekioInd;
    private int tyrimųN;
    private final int tyrimųNmax = 30;
    private final int kiekioN;
    double[][] laikai;
    private String tyrimųEilutė;
    private final String duomFormatas = "%9.4f ";
    private final String normFormatas = "%9.2f ";
    private final String vardoFormatas = "%9s ";
    private final String kiekioFormatas = "%8d(%2d) ";
    private String antraštė = "  kiekis(*k) ";

    public Timekeeper(int[] kiekiai) {
        this.tyrimoImtis = kiekiai;
        kiekioN = tyrimoImtis.length;
        laikai = new double[kiekioN][tyrimųNmax];
        startTimeTot = System.nanoTime();
    }

    public void start() {
        tyrimoInd = 0;

        tyrimųEilutė = String.format(kiekioFormatas, tyrimoImtis[kiekioInd],
                tyrimoImtis[kiekioInd] / tyrimoImtis[0]);
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        startTime = System.nanoTime();
    }

    public void startAfterPause() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        startTime = System.nanoTime();
    }

    public void finish(String vardas) {
        finishTime = System.nanoTime();
        double t1 = (finishTime - startTime) / 1e9;
        sumTime += t1;

        if (kiekioInd == 0) {
            antraštė += String.format(vardoFormatas, vardas);
        }

        laikai[kiekioInd][tyrimoInd++] = t1;
        tyrimųEilutė += String.format(duomFormatas, t1);
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        startTime = System.nanoTime();
    }

    public void seriesFinish() {
        kiekioInd++;
        tyrimųN = tyrimoInd;

        if (kiekioInd == (kiekioN)) {
            summary();
        }
    }

    private void summary() {
        finishTimeTot = System.nanoTime();
        double totTime = (finishTimeTot - startTimeTot) / 1e9;
        double d1 = laikai[0][0];
        for (int i = 0; i < kiekioN; i++) {
            tyrimųEilutė = String.format(kiekioFormatas, tyrimoImtis[i],
                    tyrimoImtis[i] / tyrimoImtis[0]);
            for (int j = 0; j < tyrimųN; j++) {
                tyrimųEilutė += String.format(normFormatas, laikai[i][j] / d1);
            }
        }
    }
}
