#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include<arpa/inet.h>
#include<string.h>

#include "fonction.h"



int main(int argc, char *argv[]) {

    if(argc != 2){

        printf("ERRUER ARGUMENT %s \n",argv[0]);
        exit(1);
    }

    int nbpro; int nbconnex;
   lecture (argv[1] , &nbpro, &nbconnex);
   printf("%d %d \n", nbpro,nbconnex );



    return EXIT_SUCCESS;
}