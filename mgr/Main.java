package mgr;

import java.util.Scanner;

public class Main {

    private int n, t;
    private int[][] tab;
    private int[] wybrane;
    private boolean[] powt = new boolean[2000];

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
        System.out.println("Podaj liczbę linijek");
        n = in.nextInt();
        System.out.println("Liczba linijek: " + n);

        //tablica
        tab = new int[n][2];

        //pętla
        for (int i = 0; i < n; i++) {
            System.out.println("Podaj liczbę a i b");
            int a = in.nextInt();
            int b = in.nextInt();

            tab[i][0] = a;
            tab[i][1] = -b;
        } //for
    }//public void load

    public void printData() {
        //wypisywanie tablicy na ekran
        for (int i = 0; i < n; i++) { // przejdz po wierszach
            System.out.println("Linijka " + i + ": " + tab[i][0] + " " + tab[i][1]);
        }//for
    }//public void print

    public void wybierzLiczby(int i) { // wybierz liczbe z linijki i
        if (i < n) {
            // wybierz pierwsza z i-tej linijki (tab[i][0])
            int a = tab[i][0];
            // sprawdzamy czy a bylo juz wybrane
            if (powt[a + 1000]) {
                wybierzLiczby(i + 1);
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
                wybierzLiczby(i + 1);
            } else if (!powt[-b + 1000]) {
                wybrane[t] = b; 
                t++;
                powt[b + 1000] = true;    
                wybierzLiczby(i + 1);
                powt[b + 1000] = false;
                t--;
            }//else if

        } else {
            for (int j = 0; j < t; j++) {
                System.out.print(String.format("%3d", wybrane[j]) + " ");
            }//for
            System.out.println("  0");
        }//else
    }//public void wybierzLiczby

    // funkcja main - START programu
    public static void main(String[] args) {
        Main program = new Main();
        program.loadData();
        program.initData();
        program.printData();
        System.out.println("Wybrane liczby:");
        program.wybierzLiczby(0);
    }//public static void main
}//public class Main    

