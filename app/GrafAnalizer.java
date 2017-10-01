package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GrafAnalizer {

    private Graf graf;

    // ---
    
    public boolean nowyGrafzTekstu(String tekst) {
        Graf g = Graf.konstruujGraf(tekst);
        if (g == null) {
            return false;
        }
        graf = g;
        return true;
    }

    public boolean nowyGrafLosowy(int st, int tr) {
        Graf g = Graf.losujGraf(st, tr);
        if (g == null) {
            return false;
        }
        graf = g;
        return true;
    }
    
    public boolean nowyGrafzPliku(String sciezka) {
        try {
            String tekst = new String(Files.readAllBytes(Paths.get(sciezka)));
            return nowyGrafzTekstu(tekst);
        } catch (IOException ex) {
            return false;
        }
    }
    
    // ---
    
    public boolean zapiszTekstDoPliku(String tekst, String path) {
        try (PrintWriter zapis = new PrintWriter(new File(path))) {
            zapis.println(tekst);
        } catch (FileNotFoundException ex) {
            return false;
        }
        return true;
    }
    
    // ---

    public String wyswietlGraf() {
        return graf.toString();
    }

    public String wyswietlOsiagalnoscCNF(int odStanu, int doStanu) {
        return graf.getOsiagalnoscCNF(odStanu, doStanu);
    }

    public String wyswietlCyklCNF(int odStanu) {
        return graf.getCyklCNF(odStanu);
    }

}
