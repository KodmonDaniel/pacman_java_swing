package pacman;


public class Eredmeny implements Comparable<Eredmeny>{
    private String nev;
    private int pontszam;

    public Eredmeny(String nev, int pontszam) {
        this.nev = nev;
        this.pontszam = pontszam;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getPontszam() {
        return pontszam;
    }

    public void setPontszam(int pontszam) {
        this.pontszam = pontszam;
    }

    @Override
    public int compareTo(Eredmeny other) {
        if (this.pontszam < other.pontszam) {
            return +1;
        } else if (this.pontszam == other.pontszam) {
            return 0;
        } else {
            return -1;
        }
    }
    
}
