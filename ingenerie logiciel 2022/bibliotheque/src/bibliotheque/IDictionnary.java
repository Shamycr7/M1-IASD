package bibliotheque;

public interface IDictionnary {
	
	
	Object get(Object key) throws Exception;
	IDictionnary put(Object key,Object valeur);
	Boolean isEmpty();
	Boolean containsKey(Object key);

}
