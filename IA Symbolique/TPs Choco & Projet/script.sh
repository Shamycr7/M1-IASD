#script pour générer plusieur fichiers en fixant un nombre de variables, un nombre de contraintes et une taille de domaines
#La dureté d'une contrainte = Le rapport entre le nombre de tuples interdits de la contrainte et le nombre total des tuples de la contrainte 
#Le nombre total des tuples  = produit des tailles des domaines. (Dans notre cas les domaines auront tous la même taille). 
#Le nombre de tuples interdits d'une contrainte =  produit des tailles des domaines - nombre total de tuple de la contrainte


#Exécution de urbcsp : ./urbcsp nb_variables nb_valeurs(taille_domaine) nb_contraintes nb_tuples_dans_chaque_contraintes nb_reseaux

#Note : Après avoir tester cette combinaison s'avère être un mauvais exemple .
Start=180
End=310
for ((i=$End ; i>=$Start ; i=$i-3))
do
	./urbcsp 35 20 204 $i 20 > csp/csp$i.txt
done
