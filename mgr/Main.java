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

    //init
    public void initData() {
        t = 0;
        wybrane = new int[n];
        for (int i = 0; i < 2000; i++) {
            powt[i] = false;
        }//for
    }//public void initData

    public void loadData() {
        //liczba linijek
        Scanner in = new Scanner(System.in);
        System.out.println("Podaj liczbę implikacji");
        n = in.nextInt();

        //tablica
        tab = new int[n][2];

        //pętla
        for (int i = 0; i < n; i++) {
            System.out.println("Podaj liczby a i b");
            int a = in.nextInt();
            int b = in.nextInt();

            tab[i][0] = a;
            tab[i][1] = -b;
        } //for
    }//public void load

    public void randomData(String[] args) {
        int s;
        if (args.length == 3) {
            // uzyj liczb - argumentow programu
            s = Integer.parseInt(args[1]);
            n = Integer.parseInt(args[2]);
        } else {
            Scanner in = new Scanner(System.in);
            System.out.println("Podaj liczbę stanów");
            s = in.nextInt();
            System.out.println("Podaj liczbę tranzycji:");
            n = in.nextInt();
        }

        //tablica
        tab = new int[n][2];

        //pętla
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int a = r.nextInt(s) + 1;
            int b = r.nextInt(s) + 1;

            tab[i][0] = a;
            tab[i][1] = -b;
        } //for
    }//public void random

    public void printData() {
        System.out.println("Implikacje:");
        System.out.println(n);
        //wypisywanie tablicy na ekran
        for (int i = 0; i < n; i++) { // przejdz po wierszach
            System.out.println(tab[i][0] + " " + -tab[i][1]);
        }//for
    }//public void print

    public void wybierzLiczby(int i) { // wybierz liczbe z linijki i
        if (i < n) {
            boolean recPowt = false;
            
            // wybierz pierwsza z i-tej linijki (tab[i][0])
            int a = tab[i][0];
            // sprawdzamy czy a bylo juz wybrane
            if (powt[a + 1000]) {
                wybierzLiczby(i + 1);
                recPowt = true;
            } else if (!powt[-a + 1000]) {
                wybrane[t] = a;
                t++;
                powt[a + 1000] = true;
                wybierzLiczby(i + 1);
                powt[a + 1000] = false;
                t--;
            }//else if

            // wybierz druga z i-tej linijki (tab[i][1])
            int b = tab[i][1];

            if (powt[b + 1000]) {
                if(!recPowt) wybierzLiczby(i + 1);
            } else if (!powt[-b + 1000]) {
                wybrane[t] = b;
                t++;
                powt[b + 1000] = true;
                wybierzLiczby(i + 1);
                powt[b + 1000] = false;
                t--;
            }//else if

        } else {
            // zapisz zbior wybranych elementow
            HashSet<Integer> set = new HashSet<>(); // utworz nowy zbior (HashSet)
            for (int j = 0; j < t; j++) set.add(wybrane[j]); // dodaj wybrane elementy
            zbiorWybranych.add(set); // dodaj do wybranych zbiorow (jesli byl juz taki zbior - nie zostanie dodany)
        }//else
    }//public void wybierzLiczby

    private void wypiszWybrane(){
        System.out.println("Wybrane liczby:");
        for(HashSet<Integer> hs : zbiorWybranych){
            for (int j : hs) {
                System.out.print(String.format("%3d", j) + " ");
            }//for
            System.out.println("  0");
        }
    }
    
    // funkcja main - START programu
    public static void main(String[] args) {
        Main program = new Main();
        
        if (args.length == 0) {
            program.loadData();
            //obsluga argumentow programu
        } else if (args[0].equals("-rand")) {
            program.randomData(args);
        } else { // zly argument
            System.out.println("wybrano zla opcje!");
            System.out.println("uzycie programu:");
            System.out.println("\tjava -jar mgr.jar");
            System.out.println("\tjava -jar mgr.jar -rand");
            return;
        }

        program.initData();
        program.printData();
        program.wybierzLiczby(0);
        program.wypiszWybrane();
    }//public static void main
}//public class Main    

