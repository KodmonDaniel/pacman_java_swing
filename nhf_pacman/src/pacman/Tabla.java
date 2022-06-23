package pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tabla extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
    private final Font ufont = new Font("OCR A Extended", Font.BOLD, 15);
    private final Font ufont2 = new Font("MS Gothic", Font.BOLD, 12);

    public boolean ingame = false;
    public boolean dying = false;

    public final int blocksize = 24;
    public final int nrofblocks = 15;
    private final int scrsize = nrofblocks * blocksize;

    public int pacmanspeed = 6;
    public int nrofghosts = 6;
    public int ghostspeed;
    
    public int pacsleft=3;
	public int score=0;
	private int kredit;
	
    private int[] dx, dy;
    private int[] ghostx, ghosty, ghostFolytx, ghostFolyty;
   
    private Image ghost, heart, up, down, left, right, fo, wall;

    private int pacmanx, pacmany, pacFolytX, pacFolytY;
    private int reqdx, reqdy, viewdx, viewdy;

    public short[] screendata;
	private Dimension d;
   
    private Timer timer;    
    private GameListener gameListener;
    
    public final short leveldata[] = {
    	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    	0,1,1,1,1,1,0,0,1,1,1,1,1,0,0,
    	0,1,0,0,1,1,0,0,1,1,1,1,1,0,0,
    	0,1,1,1,1,1,0,0,1,1,0,1,1,1,0,
    	0,1,1,1,1,1,0,0,1,1,0,1,1,1,0,
    	0,1,0,1,1,1,1,1,1,1,0,0,1,1,0,
    	0,0,0,1,1,1,1,1,1,1,0,1,1,1,0,
    	0,1,0,1,1,1,1,1,1,1,0,1,1,1,0,
    	0,1,1,1,0,0,0,1,1,1,0,1,1,1,0,
    	0,1,1,0,0,1,1,1,1,1,0,1,1,1,0,
    	0,1,1,1,0,1,1,1,1,1,1,1,1,1,0,
    	0,1,0,0,0,1,1,1,1,1,0,0,1,0,0,
    	0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,
    	0,1,1,1,1,1,1,0,0,1,1,1,1,1,0,
    	0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    	    };
    

    public Tabla() {
        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    public void setGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    private void initVariables() {

        screendata = new short[nrofblocks * nrofblocks];
        d = new Dimension(400, 400);
        ghostx = new int[12];
        ghostFolytx = new int[12];
        ghosty = new int[12];
        ghostFolyty = new int[12];
        ghostspeed = 3;
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }



    public void playGame(Graphics2D g2d) {
        if (dying) {
            death();
        } else {
            pacMozg();
            pacRajz(g2d);
            szellemMozg(g2d);
            checkLab();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {
        String s1 = "A kezdeshez nyomja meg a SPACE-t!";
        String s2 = "A kredit feltoltesehez usse le a C-t!";
        String s3 = "1 - Konnyu       2 - Nehez";
        String n = "Nehez";
        String k = "Konnyu";
    	
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 400, 360);
        
    	g2d.drawImage(fo, 25, 1, this);
        g2d.setColor(new Color(128, 128, 0));
        g2d.fillRect(35, 150, 300, 80);
        g2d.setColor(Color.white);
        g2d.drawRect(35, 150, 300, 80);
        
        g2d.setFont(ufont2);
        g2d.setColor(Color.white);
        g2d.drawString(s1,70,205);
        g2d.drawString(s2,60,185);
             
        g2d.setColor(new Color(128, 128, 0));
        g2d.fillRect(35, 250, 300, 60);
        g2d.setColor(Color.white);
        g2d.drawRect(35, 250, 300, 60);
        g2d.drawString(s3,90,275);        
        if (pacmanspeed == 6)
        	g2d.drawString(n,165,300);
        if (pacmanspeed == 3)
        	g2d.drawString(k,163,300);
        
        
    }

    private void pontKijelez(Graphics2D g) {
        int i;
        String p;
        String k;
        g.setFont(ufont);
        g.setColor(Color.yellow);
        p = "Pontszam: " + score;
        k = "Kredit: " + kredit;
        g.drawString(p, scrsize / 2 + 40, scrsize + 16);
        g.drawString(k, scrsize / 2 - 80, scrsize + 16);
        for (i = 0; i < pacsleft; i++) {
            g.drawImage(heart, i * 25, scrsize, this);
        }
    }

    private void checkLab() {
        short i = 0;
        boolean finished = true;
        while (i < nrofblocks * nrofblocks && finished) {
            if (screendata[i] == 1) {
                finished = false;
            }
            i++;
        }
        if (finished) {
            ingame = false;
            if (gameListener != null)
                gameListener.won(score);
        }
    }

    public void death() {
        if (gameListener != null)
            gameListener.death();
        pacsleft--;
        if (pacsleft == 0) {
            ingame = false;
            if (gameListener != null)
                gameListener.lastDeath(score);
        }
        continueLevel();
    }


    private void szellemMozg(Graphics2D g2d) {
    	int i;
    	int pos;
    	dx = new int[nrofghosts];
    	dy = new int[nrofghosts];
	    
	       int[] balS ;
	       int[] jobbS;
	       int[] felS;
	       int[] leS;
    	
	       balS = new int[nrofghosts];
	       jobbS= new int[nrofghosts];
	       felS= new int[nrofghosts];
	       leS= new int[nrofghosts];
	       
	       
	       
	       
	       
	       
    	for (i = 0; i < nrofghosts; i++) {
    		pos = ghostx[i] / blocksize + nrofblocks * (int) (ghosty[i] / blocksize); 
    		  Random rd = new Random();
              
              for (int j = 0; j < nrofghosts; j++) {
            	    dx[j] = rd.nextInt(3) - 1;   //-1 0 1              
              }
              
              for (int k = 0; k < nrofghosts; k++) {
            	 if(dx[k] == 0) {
            		 dy[k] = rd.nextInt(3) - 1; 
            	 }else dy[k] = 0;           
              }
                
            
    	
    		
    		if (ghostx[i] % blocksize == 0 && ghosty[i] % blocksize == 0) {   //blokk közepe   			
    	                
    	              balS[i] = screendata[pos-1];
    	      	      jobbS[i] = screendata[pos+1];
    	      	      felS[i] = screendata[pos-15];
    	      	      leS[i] =  screendata[pos+15]; 
    	      	      
    	      	    if(pos != 0) {
   	               	 ghostFolytx[i] = dx[i];
   	               	 ghostFolyty[i] = dy[i];
   	                }    
   	                
    	      	      
    	      	      //blara fal és balra megy -> stop
    	                if (balS[i] == 0 && ghostFolytx[i] == -1 && ghostFolyty[i] == 0) {
    	                	ghostFolytx[i] = 0;
    	                    ghostFolyty[i] = 0;
    	               
    	                }
    	               
    	                if (jobbS[i] == 0 && ghostFolytx[i] == 1 && ghostFolyty[i] == 0) {
    	                    ghostFolytx[i] = 0;
    	                    ghostFolyty[i] = 0;
    	               
    	                }
    	                
    	                if (felS[i] == 0 && ghostFolyty[i] == -1 && ghostFolytx[i] == 0) {
    	                    ghostFolytx[i] = 0;
    	                    ghostFolyty[i] = 0;
    	                
    	                }
    	                
    	                if (leS[i] == 0 && ghostFolyty[i] == 1 && ghostFolytx[i] == 0) {
    	                	ghostFolytx[i] = 0;
    	                	ghostFolyty[i] = 0;
    	              
    	                }
    		
    	              
    	                
    	                

    		}         
    		
    		 ghostx[i] = ghostx[i] + (ghostFolytx[i] * ghostspeed);
             ghosty[i] = ghosty[i] + (ghostFolyty[i] * ghostspeed);
             szellRajz(g2d, ghostx[i] + 1, ghosty[i] + 1);
    		
             //ütköz. 
             if (pacmanx > (ghostx[i] - 12) && pacmanx < (ghostx[i] + 12) && pacmany > (ghosty[i] - 12) && pacmany < (ghosty[i] + 12) && ingame) 
            	 	dying = true;
                 
    	       }
    }

    private void szellRajz(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }

    private void pacMozg() {

    	int pos;        //blokkszám
        int ae;        // blokk akt.ért.
            
        
        if (pacmanx % blocksize == 0 && pacmany % blocksize == 0) {
            pos = pacmanx / blocksize + nrofblocks * (int) (pacmany / blocksize);
            ae = screendata[pos];
            
            
            if (ae == 1) {
                screendata[pos] = 3;
                score++;
            }
                
            if (ae != 0) {
                 pacFolytX = reqdx;
                 pacFolytY = reqdy;
                 viewdx = pacFolytX;
                 viewdy = pacFolytY;
                }    
        
            
       int balS = screendata[pos-1];
       int jobbS = screendata[pos+1];
       int felS = screendata[pos-15];
       int leS =  screendata[pos+15];
       
       if(balS == 0 && pacFolytX == -1 && pacFolytY == 0) {
    	   pacFolytX = 0;
    	   pacFolytY = 0;
       }
       
       if(jobbS == 0 && pacFolytX == 1 && pacFolytY == 0) {
    	   pacFolytX = 0;
    	   pacFolytY = 0;
       }
       
       if(felS == 0 && pacFolytY == -1 && pacFolytX == 0) {
    	   pacFolytX = 0;
    	   pacFolytY = 0;
       }
       
       if(leS == 0 && pacFolytY == 1 && pacFolytX == 0) {
    	   pacFolytX = 0;
    	   pacFolytY = 0;
       }
       
           
            }    
        pacmanx = pacmanx + pacmanspeed * pacFolytX;
        pacmany = pacmany + pacmanspeed * pacFolytY;    	             
    }

    private void pacRajz(Graphics2D g2d) {

        if (viewdx == -1) {
            pacLeft(g2d);
        } else if (viewdx == 1) {
            pacRight(g2d);
        } else if (viewdy == -1) {
            pacUp(g2d);
        } else {
            pacDown(g2d);
        }
    }

    private void pacUp(Graphics2D g2d) {
    	g2d.drawImage(up, pacmanx + 1, pacmany + 1, this);
    }

    private void pacDown(Graphics2D g2d) {
    	g2d.drawImage(down, pacmanx + 1, pacmany + 1, this);
    }

    private void pacLeft(Graphics2D g2d) {
    	g2d.drawImage(left, pacmanx + 1, pacmany + 1, this);
    }

    private void pacRight(Graphics2D g2d) {
    	g2d.drawImage(right, pacmanx + 1, pacmany + 1, this);
    }
    
    

    private void drawMaze(Graphics2D g2d) {    	
        short i = 0;
        int x, y;

        for (y = 0; y < scrsize; y += blocksize) {
            for (x = 0; x < scrsize; x += blocksize) {

                g2d.setColor(new Color(255, 40, 0));
         

                if (leveldata[i] == 0) {            
                	g2d.drawImage(wall, x, y, this);
                }

                if (screendata[i] == 1) {
                    g2d.setColor(Color.white);
                    g2d.fillRect(x + 11, y + 11, 2, 2);                                     
                }
                i++;}
        }
    }

    public void initGame() {
        initLevel();
    }

    public void initLevel() {
        int i;
        for (i = 0; i < nrofblocks * nrofblocks; i++) {
            screendata[i] = leveldata[i];
        }
        continueLevel();        
    }

    private void continueLevel() {

        short i;
        int dx = 1;

        for (i = 0; i < nrofghosts; i++) {

            ghosty[i] = 4 * blocksize;
            ghostx[i] = 4 * blocksize;
            ghostFolyty[i] = 0;
            ghostFolytx[i] = dx;
            dx = -dx;
        }

        pacmanx = 7 * blocksize;
        pacmany = 11 * blocksize;
        pacFolytX = 0;
        pacFolytY = 0;
        reqdx = 0;
        reqdy = 0;
        viewdx = -1;
        viewdy = 0;
        dying = false;
    }
    
    public void nextLevel(){
        score += 50;      
        initLevel();
    }
    
    private void loadImages() {   
    	fo = new ImageIcon("fo.gif").getImage();
    	ghost = new ImageIcon("ghost.gif").getImage();
        heart = new ImageIcon("heart.png").getImage();
        up = new ImageIcon("up.gif").getImage();
        down = new ImageIcon("down.gif").getImage();
        left = new ImageIcon("left.gif").getImage();
        right = new ImageIcon("right.gif").getImage();
        wall = new ImageIcon("wall.gif").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        rajz(g);
    }

    private void rajz(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        pontKijelez(g2d);

        if (ingame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

    }
    
    
    public void konnyu() {
       pacmanspeed = 3;	
       ghostspeed = 1;
       nrofghosts = 3;
    }
    
    public void nehez() {
       pacmanspeed = 6;
       ghostspeed = 3;
       nrofghosts = 6;
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            
                if (ingame && key == KeyEvent.VK_LEFT) {
                    reqdx = -1;
                    reqdy = 0;
                } 
                else if (ingame && key == KeyEvent.VK_RIGHT) {
                    reqdx = 1;
                    reqdy = 0;
                } 
                else if (ingame && key == KeyEvent.VK_UP) {
                    reqdx = 0;
                    reqdy = -1;
                } 
                else if (ingame && key == KeyEvent.VK_DOWN) {
                    reqdx = 0;
                    reqdy = 1;
                } 
                else if (ingame && key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    ingame = false;
                } 
                else if  (key == 'c' || key == 'C'){
                    kredit++;
                }
                else if (key == KeyEvent.VK_SPACE) {
                    if(kredit > 0){
                        kredit--;
                        ingame = true;
                        initGame();                
                   }
                }  
                else if (ingame == false && key == KeyEvent.VK_1 && pacmanspeed == 6) {
                	konnyu();
                }
                else if (ingame == false && key == KeyEvent.VK_2 && pacmanspeed == 3) {
                	nehez();	
                }
                    
            }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                reqdx = 0;
                reqdy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

}