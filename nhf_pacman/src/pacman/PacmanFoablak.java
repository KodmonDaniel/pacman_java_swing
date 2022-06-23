package pacman;

import java.io.IOException;
import java.util.SortedSet;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PacmanFoablak extends JFrame {
	private static final long serialVersionUID = 1L;
	private Tabla t;
    private EredmenyLista eredmenyLista = new EredmenyLista();
    public PacmanFoablak() {
        t = new Tabla();
        t.setGameListener(new GameListener() {
            @Override
            public void death() {  
            }

            @Override
            public void lastDeath(int score) {
                eredmenyMentese(score);
                eredmenyMegjelenitese();
                System.exit(0);
            }

            @Override
            public void won(int score) {
                int valasz = JOptionPane.showConfirmDialog(PacmanFoablak.this, "Szeretned folytatni a jatekot?", "Nyertel!", JOptionPane.YES_NO_OPTION);
                if (valasz == JOptionPane.YES_OPTION) {
                    t.nextLevel();
                } else {
                    eredmenyMentese(score);
                    eredmenyMegjelenitese();
                    System.exit(0);
                }
            }
            
            private void eredmenyMentese(int score) {
                if (eredmenyLista.ellenoriz(score)) {
                    String nev = JOptionPane.showInputDialog(PacmanFoablak.this, "Add meg a nevedet!");
                    if (nev == null) {
                        nev = "";
                    }
                    try {
                        eredmenyLista.hozzaad(new Eredmeny(nev, score));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(PacmanFoablak.this, "Nem sikerult az eredmenylista mentese.", "HIBA", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            
            private void eredmenyMegjelenitese() {
                SortedSet<Eredmeny> eredmenyek = eredmenyLista.osszes();
               String eredmenyString = 
                        eredmenyek.stream()
                                .map(e -> e.getNev() + ": " + e.getPontszam())
                                .collect(Collectors.joining("\n"));             
                JOptionPane.showMessageDialog(PacmanFoablak.this, eredmenyString, "Eredmenyek", JOptionPane.PLAIN_MESSAGE);
            }

            
        });
        add(t);
        setTitle("Pac Man");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    } 
    public static void main(String[] args) {
    	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {                
                new PacmanFoablak().setVisible(true);                
            }
        });
    }
    
}
