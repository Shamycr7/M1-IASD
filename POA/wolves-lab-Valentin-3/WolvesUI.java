import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class WolvesUI extends JPanel {

	private int squaresize;
	private Wolves game;
	Hashtable<Integer, String> strategies = new Hashtable<Integer, String>();
	int myStrategy;
	
	public WolvesUI(Wolves game, int squaresize, int strategy) {
		
		/*this.game = game;
		game.attach(this);
		this.squaresize = squaresize;
		setPreferredSize(new Dimension(game.getNumbCols()*squaresize,game.getNumbRows()*squaresize));*/
		this.game = game;
		   this.myStrategy = strategy;
		   game.attach(this);
		   this.squaresize = squaresize;
		   setPreferredSize(new Dimension(game.getNumbCols()*squaresize,game.getNumbRows()*squaresize));
		   
		   strategies.put(1, "SuperSmartWolf");
		   strategies.put(2, "HungrySSWolf");
		   strategies.put(3, "RandomWolf");
		   strategies.put(4, "SleepingWolf");
		   strategies.put(5, "ZoningWolf");
		   //strategies.put(6, "SuperSmartWolf");

		   System.out.println("Chosen strategy : "+strategies.get(strategy));
	}

	public void paintComponent(Graphics g) {
		   Graphics2D g2 = (Graphics2D)g;
		   Image img = Toolkit.getDefaultToolkit().getImage("./images/wolf.png");
		   //draw background
		   drawgrid(g2);
		   for (int i=0; i<game.getNumbCols(); i++)
		     for (int j=0; j<game.getNumbRows(); j++)
		       if (game.isWolf(i,j)) {
		         if(myStrategy == 1) {
			    		   g2.setColor(Color.BLUE);
			    	   }
			    	   if(myStrategy == 2) {
			    		   g2.setColor(Color.BLACK);      
			    	   }
			    	   if(myStrategy == 3) {
			    		   g2.setColor(Color.ORANGE);
			    	   }
			    	   if(myStrategy == 4) {
			    		   g2.setColor(Color.red);
			    	   }
			    	   if(myStrategy == 5) {
			    		   g2.setColor(Color.magenta);
			    	   }
			    	   if(myStrategy == 6) {
			    		   g2.setColor(Color.DARK_GRAY);
			    	   }        
		        //g2.drawImage(img, i, j, 15, 15, null);
		        
		        g2.fill(new Rectangle2D.Double(i*squaresize+1,j*squaresize+1,squaresize-1,squaresize-1));
		       } else if (game.isPrey(i,j)) {
		         g2.setColor(Color.YELLOW);
		         g2.fill(new Rectangle2D.Double(i*squaresize+1,j*squaresize+1,squaresize-1,squaresize-1));
		 }
		   //System.out.println("--------------");
		}

	public void drawgrid(Graphics2D g2) {
		//fillbackground
		g2.setColor(Color.LIGHT_GRAY);
		g2.fill(getVisibleRect());
		//drawgrid
		g2.setColor(Color.GRAY);
		for (int i=0;i<=game.getNumbCols();i++)
			g2.drawLine(i*squaresize, 0, i*squaresize,game.getNumbRows()*squaresize);
		for (int i=0;i<=game.getNumbRows();i++)
			g2.drawLine(0,i*squaresize,game.getNumbCols()*squaresize,i*squaresize);
	}

	public void update() {
		repaint();
	}
	
}
