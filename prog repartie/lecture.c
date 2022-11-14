#include <string.h>
#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <stdlib.h>
#define TAILLE_MAX 1000 // Tab  

int nbpro ;
int nbconnex;

int main( int argc , char *argv[]){
FILE* fichier = fopen("DSJC250.5.txt",  "r");

if (fichier == NULL) {       
  perror("fopen");
  exit(EXIT_FAILURE); 
}

char chaine[TAILLE_MAX] = ""; 
int st[TAILLE_MAX][2];
 
     
while( fgets(chaine, TAILLE_MAX, fichier)!= NULL) {
  if(chaine[0]=='p'){
    char * texte =strdup(chaine);
    char* tab;
    int i=0;
    while(((tab =strsep(&texte," ") )!=NULL) ){
      if(i==2){
        nbpro= atoi( tab);
        printf("le nombre de processeurs est :%d \n", nbpro);
      }
      if(i==3){
        nbconnex = atoi(tab);
        printf("le nombre de connexionx est :%d \n", nbconnex);
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
        printf(" I | %d \n",st[i][0]);
    }
                
      if(p==2){
        int h ;
        h=atoi(tab1);
        st[i][1] = h;
        printf(" J | %d \n",st[i][1]);
         printf("***********************\n");
      }
                
                 
    p++;
               
     }
   }
}

printf("***************************************************************************** \n");
fclose(fichier);


return 0;
}