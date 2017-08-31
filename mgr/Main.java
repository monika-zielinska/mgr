package mgr;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

	private int n;
	private int[][] tab;
	private int[] wybrane;
	
	public void loadData() {
		//licba linijek
		Scanner in = new Scanner(System.in);
		System.out.println("Podaj liczbę linijek");
		n = in.nextInt();
		System.out.println("Liczba linijek: " + n);
		
		//tablica
		tab = new int[n][2];
		wybrane = new int[n];
		
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
	}//public void load
	
	public void printData() {
		//wypisywanie tablicy na ekran
		for(int i=0; i< n; i++){ // przejdz po wierszach
			System.out.println("Linijka " + i + ": " + tab[i][0] + " " + tab[i][1]);
		}//for
	}//public void print
	
	public void wybierzLiczby(int i){ // wybierz liczbe z linijki i
		if(i<n){
			// wybierz pierwsza z i-tej linijki (tab[i][0])
			wybrane[i] = tab[i][0];
			wybierzLiczby(i+1);
			// wybierz druga z i-tej linijki (tab[i][1])
			// ...
		} else{
			System.out.println("Wybrane liczby:... ");
			// trzeba wypisac wybrane
		}
	}
	
	public static void main(String[] args) {
		Main program = new Main();
		program.loadData();
		program.printData();
		program.wybierzLiczby(0);
	}
}//public class Main	