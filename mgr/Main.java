package mgr;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		//licba linijek
		Scanner in = new Scanner(System.in);
		System.out.println("Podaj liczbę linijek");
		int n;
		n = in.nextInt();
		System.out.println("Liczba linijek: " + n);
		
		//tablica
		
		int[][] tab;
		tab = new int[10][2];
		
		//pętla
		for(int i=0; i< n; i++){
			
			System.out.println("Podaj liczbę a");
			int a;
			a = in.nextInt();
			
			System.out.println("Podaj liczbę b");
			int b;
			b = in.nextInt();
			
			tab[i][0]=a;
			tab[i][1]=b;	
			
		} //for
		
		//wypisywanie tablicy na ekran
		for(int i=0; i< n; i++){ // przejdz po wierszach
			System.out.println("Linijka " + i + ": " + tab[i][0] + " " + tab[i][1]);
		}//for
		
	}//public static void main
}//public class Main	