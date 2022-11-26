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





#include "reseau.h"



//#define TAILLE_MAX 1000 // Tab  

int main( int argc , char *argv[]){

int nbpro =0;
int nbconnex =0 ;
char chaine[256] = ""; 






FILE* fichier = fopen("DSJC250.5.txt",  "r");

if (fichier == NULL) {       
  perror("fopen");
  exit(EXIT_FAILURE); 
}




int st[nbconnex /2][2];
 
     
while( fgets(chaine, 255, fichier)!= NULL) {
  if(chaine[0]=='p'){
    char * texte =strdup(chaine);
    char* tab;
    int i=0;
    while(((tab =strsep(&texte," ") )!=NULL) ){
      if(i==2){
        nbpro= atoi( tab);
       // printf("le nombre de processeurs est :%d \n", nbpro);
      }
      if(i==3){
        nbconnex = atoi(tab);
      //  printf("le nombre de connexionx est :%d \n", nbconnex);
      }
       i++;
     
     }
  }
           
  if(chaine[0]=='e'){
    char * t =strdup(chaine);
    char* tab1;
    int p=0;
    int i=0; int j =0;          

                  
    while(((tab1 =strsep(&t," ") )!=NULL)  ){
     if(p==1){
        int k;
        k=atoi(tab1);
        st[i][0] = k;
        //printf(" I | %d \n",st[i][0]);
    }
                
      if(p==2){
        int h ;
        h=atoi(tab1);
        st[i][1] = h;
       // printf(" J | %d \n",st[i][1]);
        // printf("***********************\n");
      }
                
                 
    p++;
               
     }
   }
}

   int reseau = creation(st, nbpro, nbconnex / 2 );

printf("***************************************************************************** \n");
fclose(fichier);


return 0;
}