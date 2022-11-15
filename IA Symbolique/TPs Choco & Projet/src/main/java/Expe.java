import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class Expe {

	private static Model lireReseau(BufferedReader in) throws Exception{
		Model model = new Model("Expe");
		int nbVariables = Integer.parseInt(in.readLine());				// le nombre de variables
		int tailleDom = Integer.parseInt(in.readLine());				// la valeur max des domaines
		IntVar []var = model.intVarArray("x",nbVariables,0,tailleDom-1); 	
		int nbConstraints = Integer.parseInt(in.readLine());			// le nombre de contraintes binaires		
		for(int k=1;k<=nbConstraints;k++) { 
		String chaine[] = in.readLine().split(";");
		IntVar portee[] = new IntVar[]{var[Integer.parseInt(chaine[0])],var[Integer.parseInt(chaine[1])]}; 
		int nbTuples = Integer.parseInt(in.readLine());				// le nombre de tuples		
		Tuples tuples = new Tuples(new int[][]{},true);
		for(int nb=1;nb<=nbTuples;nb++) { 
			chaine = in.readLine().split(";");
				int t[] = new int[]{Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1])};
				tuples.add(t);
			}
			model.table(portee,tuples).post();	
		}
		in.readLine();
		return model;
}	
		
			
	public static void main(String[] args) throws Exception{
		int i=310;
		BufferedWriter writeFile=new BufferedWriter(new FileWriter("courbe.csv"));
		ThreadMXBean thread = ManagementFactory.getThreadMXBean();
		while(i>=180){
			System.out.println("Fichier csp"+i+"\n");
			String ficName = "csp/csp"+i+".txt";
			int nbRes=20;
			int nbResWithSol=0;
			int nbNod=0;
			BufferedReader readFile = new BufferedReader(new FileReader(ficName));
			long startCpuTime = thread.getCurrentThreadCpuTime();
			for(int nb=1 ; nb<=nbRes; nb++) { 	
				Model model=lireReseau(readFile);
				
				if(model==null) {
					System.out.println("Problème de lecture de fichier !\n");
					return;
				}
				System.out.println("Réseau lu "+nb+"\n\n");  
				Solver s=model.getSolver();
				
		        if(s.solve()) {    	
		            System.out.println("Réseau "+nb+" a des solutions\n\n");
		            nbResWithSol++;
		            
			    }
		        else {
		        	if (s.isStopCriterionMet()) System.out.println("Le solveur n'as pas pu trouver de solution et n'as pu démontrer qu'il en existe dans la limite du temps donné\n\n");
		        }
		        nbNod+=s.getNodeCount();
		       
			}
			long cpuTime = thread.getCurrentThreadCpuTime() - startCpuTime;
			int pourcentage = (nbResWithSol*100)/nbRes;
			int noeudAvg=nbNod/nbRes;
			double durete=(((20*20)-(double)i)/(20*20));
			System.out.println("**************************\n\n");
			System.out.println(nbResWithSol+"/"+nbRes+" des réseaux ont des solutions =====>> "+pourcentage+"% des réseaux ont des solutions");
			String StringPourc=Integer.toString(pourcentage);
			String StringDurete=Double.toString(durete);
			String StringCpuTime=Long.toString(cpuTime);
			String StringNodAvg=Integer.toString(noeudAvg);
			writeFile.write(StringDurete+";"+StringPourc+";"+StringCpuTime+";"+StringNodAvg+"\n");
			i=i-3;
		}
		writeFile.close();
		return;
	}
}