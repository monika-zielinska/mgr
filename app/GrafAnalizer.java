package app;

public class GrafAnalizer {

    private Graf graf;

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

    public String wyswietlGraf() {
        return graf.toString();
    }

    public String wyswietlOsiagalnoscCNF(int odStanu, int doStanu) {
        return graf.getOsiagalnoscCNF(odStanu, doStanu);
    }

    public String wyswietlCyklCNF(int odStanu, int doStanu) {
        return graf.getCyklCNF(odStanu, doStanu);
    }

}
