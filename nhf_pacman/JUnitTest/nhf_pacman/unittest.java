package nhf_pacman;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Assert;
import java.io.FileNotFoundException;
import java.io.IOException;
import pacman.Eredmeny;
import pacman.EredmenyLista;
import pacman.Tabla;



public class unittest {


	@Test
	public void nevlekerdez() {
       Eredmeny e = new Eredmeny("Jatekos", 100);       
       assertEquals("Jatekos",e.getNev());
    }
	
	
	@Test
	public void pontlekerdez() {
       Eredmeny e = new Eredmeny("Jatekos", 100);
        assertEquals(100,e.getPontszam());
    }
	
	
	@Test
	public void pontbeallit() {
        Eredmeny e = new Eredmeny("Jatekos", 100);      
        e.setPontszam(50);
        assertEquals(50,e.getPontszam());
    }
	
	
	@Test
	public void osszehasonlit() {
        Eredmeny e = new Eredmeny("Jatekos", 100); 
        Eredmeny e2 = new Eredmeny("Jatekos2", 100);
        e.setPontszam(50);
        int x = e.compareTo(e2);
        assertEquals(1,x);
    }
	
	
	@Test(expected = FileNotFoundException.class)
	public void fileBeolvas() throws IOException {
		EredmenyLista el = new EredmenyLista();
		el.fileBeolvas("vmi.txt");
	}
	
	
	@Test
	public void listaell() throws IOException {
		boolean x;
		EredmenyLista el = new EredmenyLista();
		Eredmeny e = new Eredmeny("Jatekos", 100);
		el.hozzaad(e);
		x = el.ellenoriz(100);
		assertEquals(false,x);		
	}
	
	
	@Test
	public void nehezseg() {
	    Tabla t = new Tabla();
	    t.konnyu();	    
		assertEquals(3,t.pacmanspeed);
		assertEquals(1,t.ghostspeed);
		assertEquals(3,t.nrofghosts);
	}
	
	
	@Test
	public void labTeszt() {
		Tabla t = new Tabla();
		t.initGame();
		for (int i = 0; i < t.nrofblocks * t.nrofblocks; i++){
			Assert.assertEquals(t.screendata[i], t.leveldata[i]);
		}
	}
	
	@Test
	public void jatekvege() {
		Tabla t = new Tabla();
		t.pacsleft = 1;
		t.death();
		Assert.assertEquals(false,t.ingame);
		Assert.assertEquals(0,t.score);   //0 score mert nem csináltunk semmit
	}
	
	
	@Test
	public void eletvesztes() {
		java.awt.Graphics2D g2d = null;
		Tabla t = new Tabla();
		t.dying = true;
		t.playGame(g2d);
		Assert.assertEquals(2,t.pacsleft);
	}
	
	
	@Test
	public void kovSzint() {
		Tabla t = new Tabla();
		t.initLevel();
		t.screendata[17] = 3;
		t.nextLevel();	
		Assert.assertEquals(50,t.score);
		for (int i = 0; i < t.nrofblocks * t.nrofblocks; i++)
			Assert.assertEquals(t.screendata[i], t.leveldata[i]);
	}
		
}
