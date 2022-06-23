package pacman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class EredmenyLista {
    //private static final String FILENEV = "eredmenylista.txt";
    private static final int MERET = 5;
    private SortedSet<Eredmeny> lista = new TreeSet<>();

    public EredmenyLista() {
        try {
            fileBeolvas("eredmenylista.txt");
        } catch (IOException ex) {
            // ures listaval indulunk
        }
    }
    
    public void hozzaad(Eredmeny uj) throws IOException {
        lista.add(uj);
        if (lista.size() > MERET) {
            Eredmeny utolso = lista.last();
            lista.remove(utolso);
        }
        fileKiir();
    }
    public boolean ellenoriz(int pont) { 
        if (!lista.isEmpty()){
            return lista.last().getPontszam() < pont;
        }else{
            return true;
        }
    }
    public SortedSet<Eredmeny> osszes() {  
        return Collections.unmodifiableSortedSet(lista);
    }
    
    public void fileBeolvas(String file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String kovetkezoSor;
            while ((kovetkezoSor = br.readLine()) != null) {
                
                String[] adatokTomb = kovetkezoSor.split(";");
                Eredmeny e = new Eredmeny(adatokTomb[0],Integer.parseInt(adatokTomb[1]));
                hozzaad(e);
                
            }

        } catch (IOException ex) {
            throw ex;
        }
    }
    
    private void fileKiir() throws IOException {
        try (FileWriter fw = new FileWriter("eredmenylista.txt");
                PrintWriter pw = new PrintWriter(fw)) {
            for (Eredmeny e : lista) {
                pw.println(e.getNev() + ";" + e.getPontszam());
            }
        }
    }
}
