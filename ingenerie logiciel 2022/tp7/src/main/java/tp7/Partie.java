package tp7;

import java.util.ArrayList;



public class Partie {
	
	protected int nbrbidon;
	protected ArrayList<Bidon> listesBidons ;
	protected int volAatteindre;
	
	protected Lanceur l;

	public Partie(int nbrbidon, int[] capacitésBidons, int volAatteindre) {
	
		this.nbrbidon = nbrbidon;
		this.listesBidons = new ArrayList<Bidon> (nbrbidon) ;
		this.volAatteindre = volAatteindre;
		this.l = new Lanceur ();
		
		for(int i =0;i< nbrbidon;i++) {listesBidons.add(i, new Bidon());)}
	}

	public Partie() {}
	
	
	
	
		

}

partie p = new partie (3)