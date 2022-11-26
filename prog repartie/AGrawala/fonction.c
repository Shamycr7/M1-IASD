#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include<arpa/inet.h>
#include<string.h>

void lecture (char* filename , int* nbpro,int* nbconnex){


      
    FILE *fichier = fopen(filename, "r");
    if(fichier == NULL){
    	printf("Fichier vide !!\n");
    	exit(1);
    }
    char phrase[256];
    char chaine[1000000];
    
    // nbpro = 0;
     //	nbconnex = 0;

    
    
    
    int j = 0;
    while(fgets(phrase, 255, fichier) != NULL){
    	strcat(chaine, phrase);
    	char *str = strdup(phrase);
    	char *val;
    	int i = 0;
    	if(phrase[0] == 'p'){
    		while(val = strsep(&str, " ")){
    			if(i == 2) *nbpro = atoi(val);
    			if(i == 3) *nbconnex = atoi(val);
    			i++;
    		}
    		break;
    	}
    }

    //printf("%d %d \n", nbpro,&nbconnex );



    fclose(fichier);
    }

