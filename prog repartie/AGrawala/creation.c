#include <stdio.h> 
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <pthread.h>
# include <sys/wait.h>

void lecture(argv[1],)



int main(int argc, char *argv[]) {

	if(argc < 2){
        printf("utilisation: %s nom_fichier\n", argv[0]);
        exit(1);
    }

    
        char phrase[256];
        char chaine[1000000];
        
        int nbpro = 0;
        int nbconnex = 0;

        
        FILE *fichier = fopen(argv[1], "r");
        if(fichier == NULL){
            printf("Fichier vide !!\n");
            exit(1);
        }
        int j = 0;
        while(fgets(phrase, 255, fichier) != NULL){
            strcat(chaine, phrase);
            char *str = strdup(phrase);
            char *val;
            int i = 0;
            if(phrase[0] == 'p'){
                while(val = strsep(&str, " ")){
                    if(i == 2) nbpro = atoi(val);
                    if(i == 3) nbconnex = atoi(val);
                    i++;
                }
                break;
            }
        }
    int tab[nbconnex / 2][2];
    while(fgets(phrase, 255, fichier) != NULL){
    	strcat(chaine, phrase);
    	char *str = strdup(phrase);
    	char *val;
    	int i = 0;
    	if(phrase[0] == 'e'){
    		while(val = strsep(&str, " ")){
    			if(i == 1) tab[j][0] = atoi(val);
    			if(i == 2) tab[j][1] = atoi(val);
    			i++;
    		}
    		j++;
    	}
    }
   

   
 


	
    fclose(fichier);

    return 0;
}
