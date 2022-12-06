
#include <stdio.h>
#include "image_ppm.h"

int main(int argc, char* argv[])
{
  char cNomImgLue[250], cNomImgEcrite[250], imageSortie[250];
  int nH, nW, nTaille;
  int som;
  if (argc != 4) 
     {
       printf("Usage: ImageIn.pgm ImageOut.pgm \n"); 
       exit (1) ;
     }
   
   sscanf (argv[1],"%s",cNomImgLue) ;
   sscanf (argv[2],"%s",cNomImgEcrite);
   sscanf (argv[3],"%s",imageSortie);

   OCTET *ImgIn, *ImgOut,*ImgNoise;
   
   lire_nb_lignes_colonnes_image_pgm(cNomImgLue, &nH, &nW);
   nTaille = nH * nW;
  
   allocation_tableau(ImgIn, OCTET, nTaille);
   lire_image_pgm(cNomImgLue, ImgIn, nH * nW);
   allocation_tableau(ImgOut, OCTET, nTaille);
   lire_image_pgm(cNomImgEcrite, ImgOut, nH * nW);

    allocation_tableau(ImgNoise, OCTET, nTaille);

   
	
  // for (int i=0; i < nTaille; i++)
    //ImgOut[i]= ImgIn[i];
    double moy;
    double moyenne = 0;
     double variance= 0;
     double ecart_T =0 ;
      //double moyenne = 0;

 for (int i=0; i < nH-1; i++)
   for (int j=0; j < nW-1; j++)
     {
		
         double val =ImgIn[i*nW+j]-ImgOut[i*nW+j]; 
         val = val < -128 ? -128 : val > 127 ? 127 : val;
         val +=128;
         moy+=val;
       ImgNoise[i*nW+j]=val;

     }
     moyenne =moy/(float)nTaille;
    printf("moyenne :%f\n", moyenne);

    for (int i = 0; i < nH - 1; i++)
        for (int j = 0; j < nW - 1; j++)
        {
            double val=ImgNoise[i*nW+j];
            variance+=(val-moyenne)*(val-moyenne);
        }

    variance =variance/(float)nTaille;
    printf("variance :%f\n", variance);
    printf("ecart-type :%f\n", sqrt(variance));  

    
   ecrire_image_pgm(imageSortie, ImgNoise,  nH, nW);
   free(ImgIn);free(ImgOut);free(ImgNoise);
   return 1;
}