package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class Graf {

    // wezel grafu
    private class Wezel {
        boolean uzywany; // czy wchodza lub wychodza z niego jakies krawedzie
        List<Integer> krawedzie = new ArrayList<>();
    }
    // --- skladowe klasy Graf
    private static int SIZE = 1000;
    private final Wezel wezly[];

    // --- konstruktor
    private Graf() {
        this.wezly = new Wezel[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            this.wezly[i] = new Wezel();
        }
    }

    // --- funkcje prywatne
    private void dodajKrawedz(int a, int b) {
        wezly[a].krawedzie.add(b);
        wezly[a].uzywany = true;
        wezly[b].uzywany = true;
    }

    private void porzadkujKrawedzie() {
        for (int i = 0; i < SIZE; i++) {
            TreeSet<Integer> bezDuplikatow = new TreeSet<>(wezly[i].krawedzie);
            wezly[i].krawedzie = new ArrayList<>(bezDuplikatow);
        }
    }

    // ------------- Wypisywanie
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            if (wezly[i].krawedzie.isEmpty()) {
                continue;
            }
            sb.append(String.format("%3d", i));
            for (int to : wezly[i].krawedzie) {
                sb.append(String.format(" %3d", to));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getOsiagalnoscCNF(int x, int y) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < SIZE; i++) {
            if (wezly[i].krawedzie.isEmpty()) {
                continue;
            }
            sb.append(String.format("%3d", -i));
            for (int to : wezly[i].krawedzie) {
                sb.append(String.format(" %3d", to));
            }
            sb.append("  0\n");
        }
        sb.append(String.format("%3d  0\n", x));
        sb.append(String.format("%3d  0\n", -y));
        return sb.toString();
    }

    public String getCyklCNF(int x, int y) {
        List<Integer> bezNastepnika = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            if (!wezly[i].uzywany) // jesli nie uzywany, przegladaj dalej
            {
                continue;
            }
            if (wezly[i].krawedzie.isEmpty()) { // nie ma krawedzi wychodzacych
                bezNastepnika.add(i); // zapamietaj
            } else { // jesli ma krawedzie - wypisz formule
                sb.append(String.format("%3d", -i));
                for (int to : wezly[i].krawedzie) {
                    sb.append(String.format(" %3d", to));
                }
                sb.append("  0\n");
            }
        }
        for (int i : bezNastepnika) {
            sb.append(String.format("%3d  0\n", -i)); // dodaj negacje
        }
        sb.append(String.format("%3d  0\n", x));
        sb.append(String.format("%3d  0\n", -y));
        return sb.toString();
    }

    // ------------- Konstrukcja
    public static Graf konstruujGraf(String tekst) {
        Graf g = new Graf();
        Scanner s = new Scanner(tekst);
        while (s.hasNextLine()) {
            String line = s.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            Scanner lineScanner = new Scanner(line);
            int x = lineScanner.nextInt();
            while (lineScanner.hasNext()) {
                int y = lineScanner.nextInt();
                g.dodajKrawedz(x, y);
            }
        }
        g.porzadkujKrawedzie();
        return g;
    }

    public static Graf losujGraf(int n, int m) {
        Graf g = new Graf();
        Random rand = new Random();
        while (--m >= 0) {
            int x = rand.nextInt(n) + 1;
            int y = rand.nextInt(n) + 1;
            g.dodajKrawedz(x, y);
        }
        g.porzadkujKrawedzie();
        return g;
    }
}
