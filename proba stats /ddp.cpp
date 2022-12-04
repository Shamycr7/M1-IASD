#include <stdio.h>
#include "image_ppm.h"

int main(int argc, char* argv[])
{
  char cNomImgLue[250], cNomImgEcrite[250];
  int nH, nW, nTaille, S;
  
  if (argc != 2) 
     {
       printf("Usage: ImageIn.pgm  \n"); 
       exit (1) ;
     }
   
   sscanf (argv[1],"%s",cNomImgLue) ;
   //sscanf (argv[2],"%s",cNomImgEcrite);
   

   OCTET *ImgIn, *ImgOut;
   
   lire_nb_lignes_colonnes_image_pgm(cNomImgLue, &nH, &nW);
   nTaille = nH * nW;
  
   allocation_tableau(ImgIn, OCTET, nTaille);
   lire_image_pgm(cNomImgLue, ImgIn, nH * nW);
   allocation_tableau(ImgOut, OCTET, nTaille);
	
   //   for (int i=0; i < nTaille; i++)
   // {
   //  if ( ImgIn[i] < S) ImgOut[i]=0; else ImgOut[i]=255;
   //  }
  FILE *f;

int t[256];
float c[256];
for(int i=0;i<256;i++){
    t[i]=0;
}
f= fopen("fich1.txt" ,"w");
for(int i=0;i<nW;i++) 
    for(int j=0;j<nH;j++) 
       t[ImgIn[i*nW+j]]++; 

c [0]=float(t[0])/nTaille;

for (int i = 1; i<256;i++){
   
 c[i] =c[i-1]+float(t[i])/nTaille;
 //printf(" %d %f\n",i,c[i]);
 
 }

for (int i = 0; i<255;i++){
    
      
        fprintf(f,"%d %f \n",i,(float)(t[i])/(float)nTaille);
    
}
//printf("%f\n",c);
fclose(f);   
//nh hauteur nw largeur 
 
   return 1;
}
