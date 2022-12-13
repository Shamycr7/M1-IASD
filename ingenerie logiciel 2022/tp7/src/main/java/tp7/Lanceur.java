package tp7;

import java.util.Stack;

public class Lanceur {
	
	protected Stack<ICommande> history ;
	
	public boolean hasNextUNdo() {return (!history.isEmpty());}

	public Lanceur () {
		this.history = new Stack<ICommande>();}
	
	public void pushCommande(ICommande c) {
		c.excute();
		history.push(c);} 
	
	public void undoLasttCommand() {
		history.lastElement().undo();
		history.pop();
	}
	
	public Stack<ICommande> getHistory(){return history;}
	

}
