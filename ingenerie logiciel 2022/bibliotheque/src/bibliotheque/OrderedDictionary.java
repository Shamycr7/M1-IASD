package bibliotheque;

public class OrderedDictionary extends AbstractDicionary {
	
	protected OrderedDictionary(int n) {
		super(n);
	}

	public OrderedDictionary() {
		super(0);
	}
	
	public int size() {
		int s=0;
		for(int i=0;i<keys.length;i++) {
			if(keys[i]!=null) s++;
		}
		return s;
	}



	

	
	public int indexOf(Object cle) {
		
		for(int i=0;i< keys.length ;i++) {
			if(cle.equals(keys[i])) return i;
					}
		
		return -1;
	}

	
	public int newIndexOf(Object o) {
		int size =this.size();
		
		if(size == keys.length) {
			Object[] newkey = new Object[keys.length+1];
			Object[] newvaleur = new Object[valeurs.length+1];
			
			for(int i=0;i<keys.length;i++) {
				newkey[i]=keys[i];
				newvaleur[i]=valeurs[i];
			}
			keys =newkey;
			valeurs = newvaleur;
			return keys.length-1;
			
			}
		else return size;
			
		
	
	}}
