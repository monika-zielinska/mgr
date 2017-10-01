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
    private static final int SIZE = 100000;
    private final Wezel wezly[];
    private int maksymalnaZmienna;

    // --- konstruktor
    private Graf() {
        this.wezly = new Wezel[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            this.wezly[i] = new Wezel();
        }
        maksymalnaZmienna = 0;
    }

    // --- funkcje prywatne
    private void dodajKrawedz(int a, int b) {
        wezly[a].krawedzie.add(b);
        wezly[a].uzywany = true;
        wezly[b].uzywany = true;
        maksymalnaZmienna = Math.max(maksymalnaZmienna, Math.max(a, b));
    }

    private void porzadkujKrawedzie() {
        for (int i = 1; i <= maksymalnaZmienna; i++) {
            TreeSet<Integer> bezDuplikatow = new TreeSet<>(wezly[i].krawedzie);
            wezly[i].krawedzie = new ArrayList<>(bezDuplikatow);
        }
    }

    // ------------- Wypisywanie
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= maksymalnaZmienna; i++) {
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
        int liczbaKlauzul = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= maksymalnaZmienna; i++) {
            if (wezly[i].krawedzie.isEmpty()) continue;
            for (int to : wezly[i].krawedzie) {
                sb.append(String.format("%3d %3d %3d\n",-i, to, 0));
                ++liczbaKlauzul;
            }
        }
        sb.append(String.format("%3d %3d\n", x,0));
        ++liczbaKlauzul;
        sb.append(String.format("%3d %3d\n",-y,0));
        ++liczbaKlauzul;

        String naglowek = String.format("p cnf %d %d\n", maksymalnaZmienna, liczbaKlauzul);
        return naglowek + sb.toString();
    }

    public String getCyklCNF(int start) {
        int liczbaKlauzul = 0;
        List<Integer> bezNastepnika = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= maksymalnaZmienna; i++) {
            if (!wezly[i].uzywany) continue; // jesli nie uzywany, przegladaj dalej
            if (wezly[i].krawedzie.isEmpty()) { // nie ma krawedzi wychodzacych
                bezNastepnika.add(i); // zapamietaj
            } else { // jesli ma krawedzie - wypisz formule
                sb.append(String.format("%3d", -i));
                for (int to : wezly[i].krawedzie) {
                    sb.append(String.format(" %3d", to));
                }
                sb.append(String.format(" %3d\n", 0));
                ++liczbaKlauzul;
            }
        }
        for (int i : bezNastepnika) {
            sb.append(String.format("%3d %3d\n", -i, 0)); // dodaj negacje
            ++liczbaKlauzul;
        }
        sb.append(String.format("%3d %3d\n", start, 0));
        ++liczbaKlauzul;

        String naglowek = String.format("p cnf %d %d\n", maksymalnaZmienna, liczbaKlauzul);
        return naglowek + sb.toString();
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
