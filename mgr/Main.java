package mgr;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private int n, t;
    private int[][] tab;
    private int[] wybrane;
    private final boolean[] powt = new boolean[2000];
    private final HashSet<HashSet<Integer>> zbiorWybranych = new HashSet<>();

    //inicjalizuj - dane potrzebne do obliczen
    private void inicjalizujPozostaleDane() {
        t = 0;
        wybrane = new int[n];
        for (int i = 0; i < 2000; i++) {
            powt[i] = false;
        }
    }

    //wczytaj implikacje
    public void wczytajDane() {
        //liczba linijek
        Scanner in = new Scanner(System.in);
        System.out.println("Podaj liczbę implikacji");
        n = in.nextInt();

        //tablica
        tab = new int[n][2];

        //wczytywanie implikacji - petla, tyle obrotow ile linijek
        for (int i = 0; i < n; i++) {
            System.out.println("Podaj liczby a i b");
            int a = in.nextInt();
            int b = in.nextInt();
            tab[i][0] = a;
            tab[i][1] = -b;
        }
        //pozostale dane
        inicjalizujPozostaleDane();
    }

    //generuj implikacje
    public void generujDane(String[] args) {
        int s;
        if (args.length == 3) {
            // uzyj liczb - argumentow programu
            s = Integer.parseInt(args[1]);
            n = Integer.parseInt(args[2]);
        } else {
            // wczytaj z wejsca
            Scanner in = new Scanner(System.in);
            System.out.println("Podaj liczbę stanów");
            s = in.nextInt();
            System.out.println("Podaj liczbę tranzycji:");
            n = in.nextInt();
        }

        //tablica
        tab = new int[n][2];

        //petla z generowaniem tranzycji (implikacji)
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int a = r.nextInt(s) + 1;
            int b = r.nextInt(s) + 1;
            tab[i][0] = a;
            tab[i][1] = -b;
        }
        //pozostale dane
        inicjalizujPozostaleDane();
    }

    //wypisz implikacje
    public void wypiszDane() {
        System.out.println("Implikacje:");
        System.out.println(n);
        //wypisywanie tablicy na ekran
        for (int i = 0; i < n; i++) { // przejdz po wierszach
            System.out.println(tab[i][0] + " " + -tab[i][1]);
        }
    }

    //wybierz
    public void wybierzLiczby(int i) { // wybierz liczbe z linijki i
        if (i < n) { // jestesmy na poziomie [0...n)
            boolean recPowt = false;
            // petla - wybierz pierwsza a potem druga liczbe z i-tej linijki
            for (int COL = 0; COL < 2; ++COL) {
                // wybierz liczbe tab[i][COL]
                int a = tab[i][COL];

                if (powt[a + 1000]) { // czy ta liczba byla juz wybrana - tak, idziemy do i+1, ale nie dodajemy jej
                    if (!recPowt) { // czy wywolywalismy sie juz bez dodawania liczby - tak, to nie wywolujemy sie
                        wybierzLiczby(i + 1);
                        recPowt = true; // oznacz zeby drugi raz w takim przypadku nie wywolywac
                    }
                } else if (!powt[-a + 1000]) { // czy -a jest wybrane? tak - nie wywolujemy sie
                    wybrane[t++] = a;       // dodaj a do wybranych i zwieksz ich liczbe
                    powt[a + 1000] = true;  // oznacz, ze a jest wybrane
                    wybierzLiczby(i + 1);   // przejdz do poziomu i+1
                    powt[a + 1000] = false; // odznacz wybranie a
                    t--;                    // usun a z wybranych
                }
            }
        } else { // i >= n, przeszlismy wszystkie linijki - mamy kompletny wybor
            // zapisz zbior wybranych elementow
            HashSet<Integer> set = new HashSet<>(); // utworz nowy zbior (HashSet)
            for (int j = 0; j < t; j++) {
                set.add(wybrane[j]); // dodaj wybrane elementy
            }
            zbiorWybranych.add(set); // dodaj do wybranych zbiorow (jesli byl juz taki zbior - nie zostanie dodany)
        }
    }

    //wypisz
    public void wypiszWybrane() {
        System.out.println("Wybrane liczby:");
        for (HashSet<Integer> hs : zbiorWybranych) {
            for (int j : hs) {
                System.out.print(String.format("%3d", j) + " ");
            }
            System.out.println("  0"); // na koniec kazdej linijki dodajemy 0
        }
    }

    //main - START programu
    public static void main(String[] args) {
        Main program = new Main();

        // obsluga argumentow
        if (args.length == 0) { // brak - wczytaj dane
            program.wczytajDane();
        } else if (args[0].equals("-rand")) { // opcja rand - generuj dane
            program.generujDane(args);
        } else { // zly argument - wypisz sposob uzycia programu
            System.out.println("wybrano zla opcje!");
            System.out.println("uzycie programu:");
            System.out.println("\tjava -jar mgr.jar");
            System.out.println("\tjava -jar mgr.jar -rand");
            return;
        }
        // sprawdzamy jakie dane zostaly wczytane/wygenerowane
        program.wypiszDane();

        // wybierz liczby i wypisz mozliwe wybory
        program.wybierzLiczby(0);
        program.wypiszWybrane();
    }
}
