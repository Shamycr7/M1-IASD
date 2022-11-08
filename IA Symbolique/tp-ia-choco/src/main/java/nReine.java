import java.util.stream.IntStream;
import java.util.Scanner;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class nReine {
	
	private static int n  ;
	
	public static void main(String [] args) {
		
		Scanner c =new Scanner(System.in);
		
		System.out.print("Entrez n : ");
        n = c.nextInt();
        
		Model model = new Model("nReins");
		
		
		
	//	IntVar[] r = model.intVarArray("Q", n, 1, n, false);
		
		IntVar[] r = model.intVarArray("r",n,1,n);
		
		IntVar[] diag = IntStream.range(0, n).mapToObj(i -> r[i].sub(i).intVar()).toArray(IntVar[]::new);
		IntVar[] diag1 = IntStream.range(0, n).mapToObj(i -> r[i].add(i).intVar()).toArray(IntVar[]::new);
		model.post(
		    model.allDifferent(r),
		    model.allDifferent(diag),
		    model.allDifferent(diag1)
		);
		
		
		   // Affichage du réseau de contraintes créé
        System.out.println("*** Réseau Initial ***");
        System.out.println(model);
        

        // Calcul de la première solution8
        
        if(model.getSolver().solve()) {
        	System.out.println("\n\n*** Première solution ***");        
        	System.out.println(model);
        }

              
    	// Calcul de toutes les solutions
    //	System.out.println("\n\n*** Autres solutions ***");        
     //   while(model.getSolver().solve()) {    	
          //  System.out.println("Sol "+ model.getSolver().getSolutionCount()+"\n"+model);
	  //  }
   
 
        
        // Affichage de l'ensemble des caractéristiques de résolution
      	System.out.println("\n\n*** Bilan ***");        
        model.getSolver().printStatistics();
		
		
	}
	

}
