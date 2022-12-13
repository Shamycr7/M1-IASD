package bibliotheque;



public abstract class AbstractDicionary implements IDictionnary {

	
	
	protected Object[] keys;
	protected Object[] valeurs;
	
	protected  AbstractDicionary(int n) {
		
		this.keys= new Object[n];
		this.valeurs=new Object[n];
}
	


	public int size() {return keys.length;}
	abstract int indexOf(Object o);
	abstract int newIndexOf(Object o);
	
	public Object get(Object key) throws Exception {
		int i = this.indexOf(key);
		if(i != -1) return valeurs[i];
		else 
			throw
			new Exception(" cette clé n'est pas dans le dicitionare\t" + key);
		
	}
	

	public IDictionnary put(Object key,Object valeur) {
		
		
		int j=indexOf(key);
		if(j==-1) {
			int i=this.newIndexOf(key);
			keys[i]=key;
			valeurs[i]=valeur;
			
		}else valeurs[j]= valeur;
		return this;
	}
	
	public Boolean isEmpty() {
		return this.size()==0;
	}



	
	public Boolean containsKey(Object key) {
		
		return this.indexOf(key)==-1;
	}



	
	public String toString() {
		String s = "taille tab "+ keys.length +"\n";
		for(int i=0;i< keys.length;i++) {
			if(keys[i]!= null) {
				
				s=s+keys[i].toString()+ "-->" + valeurs[i].toString() + "\n";
			}}
		return s;
	
	}


	
	public static void main(String[]args)throws Exception
{
		
	 AbstractDicionary OD = new OrderedDictionary(2);
	 
	 OD.put("lavoisier", "chimiste");
	 System.out.println(OD.get("lavoisier"));
	 System.out.println(OD.size());
	 OD.put("cr7", "cristiano ronaldo 7");
	 System.out.println(OD.get("cr7"));
	 System.out.println(OD.size());
	 OD.put("hey", "hello");
	 System.out.println(OD.get("hey"));
	 System.out.println(OD.size());
	 
	 

}	
	
		
		
}
