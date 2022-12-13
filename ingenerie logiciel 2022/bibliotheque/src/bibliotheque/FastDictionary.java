package bibliotheque;

public class FastDictionary extends AbstractDicionary{
	
	public FastDictionary(int n ) {
		super(n);
	}
	
	public int size() {
		int s = 0;
		for(int i=0;i<keys.length;i++) {
			if(keys[i] != null)
				s++;
		}
		return s;
	}
	
	public boolean mustGrow() {
		
		int i = this.size();
		int j = this.keys.length;
		if(j==0) return true ;
		else 
			return (i/j) > 0.75 ;
	}
	
	public void grow() {
		System.out.println("Growing a fast dico");
		Object[] oldKeys = keys;
		Object[] oldvalues = valeurs;
		keys = new Object[oldKeys.length + 5];
		valeurs = new Object[oldKeys.length + 5];
		
		for(int i = 0;i<oldKeys.length;i++) {
			if(oldKeys[i]!= null) {
				this.put(oldKeys[i], oldvalues[i]);
			}
		}
		
	System.out.println("la taille du tableau " + keys.length + "\n");	
	}
	
	int indexOf(Object key) {
		int hash= key.hashCode();
		if(hash<0) hash = -1*hash;
		int i = hash % keys.length;
		while((!(key.equals(keys[i]))) && keys[i] != null) {
			i = (i+1)%keys.length;}
		if(keys[i]==null) return -1;
		else return i ;
				
	}

	@Override
	int newIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
