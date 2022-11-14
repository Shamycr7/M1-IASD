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
#include <sys/wait.h>



struct params {
    int numero;
    int ds;
    int nbrConnexion;
    char port[10];
    int otherNumb[];
};

int processus(int nbrProcessus){
    for(int i=0; i<nbrProcessus; i++){
        if(fork() == 0) return (i+1);
    }
    return(0);
}
    


void* acceptFnc(void* param){
    struct params *args = (struct params *) param;
    int numero = args->numero;
    int ds = args->ds;
    int nbrConnexion = args->nbrConnexion;
    printf("Client<%d>: Thread Accepte: dans le thread accepte\n", numero);

    struct sockaddr_in adCV;
    socklen_t lgCV = sizeof(struct sockaddr_in);
     int dsCV;
    for(int i =0; i<nbrConnexion ; i++){
        printf("Client<%d> : THREAD ACCEPT : ATTENTE DE CONNECTION DU %d client\n ",numero,i);
        dsCV = accept(ds,(struct sockaddr *) &adCV,&lgCV);
        if(dsCV < 0){
            perror("Client<> preomblem acceptation\n");
            close(ds);
            exit(1);
        }
        printf("Client<%d>: Thread Accepte: le client <%d> %s:%d est connecté  \n", numero, i, inet_ntoa(adCV.sin_addr),  dsCV);


    }
    close(dsCV);
}
    
int creation(int tabLiaison[][2], int nbrProcessus, int nbrLiaison){
        int portR = 7777;
        int tabConnex[nbrProcessus];
        int nbrConnexion = 0;
        
        printf("//-------------------- creation des processus -------------------------//\n");
        
        int numero = processus(nbrProcessus);
        if(numero != 0){
		

		//----creation d'un tableau contenant la liste des processus a connecter avec celle la
		for(int i = 0; i < nbrLiaison; i++){
			if(tabLiaison[i][0] == numero || tabLiaison[i][1] == numero){
				if(tabLiaison[i][0] != numero) tabConnex[nbrConnexion] = tabLiaison[i][0];
				else tabConnex[nbrConnexion] = tabLiaison[i][1];
				//printf("[%d][%d]\n", numero, tabConnexion[nbrConnexion]);
	    		nbrConnexion++;
			}
	    }
        
        
    


    printf("//-------------------- creation des socket -------------------------//\n");

    int dsServ = socket (PF_INET,SOCK_STREAM,0);
    if(dsServ == -1){
        printf("Client<%d> : pb creation socket\n",numero);
        exit(1);
    }
    printf("Client<%d> : creation de la socket réussie \n",numero);


    printf("//-------------------- reglage des ports -------------------------//\n");
    portR = portR + numero;
	char portReseau[10];
	printf(portReseau, "%d", portR);
    

     printf("//-------------------- nommage des sockets -------------------------//\n");
     struct sockaddr_in server;
     server.sin_family = AF_INET;
     server.sin_addr.s_addr = INADDR_ANY;
     server.sin_port = (htons ((short)atoi(portReseau)));
     if(bind(dsServ,(struct sockaddr*)&server,sizeof(server))<0){
        printf("Client<%d>: erreur bind", numero);
        close(dsServ);
        exit(1);
        }
    
     printf("Client<%d> : bind réussie \n",numero);



    printf("//-------------------- nommage des sockets -------------------------//\n");

    int ecoute = listen(dsServ,nbrProcessus);
    if(ecoute < 0){
        printf("Client<%d> : je suis sourd(e) \n",numero);
        close(dsServ);
        exit(1);
    }

    printf("Client<%d> : serveur mise en ecoute \n",numero);

    printf("//-------------------- creation des threads accept  -------------------------//\n");

       
       pthread_t accept;

       struct params paramAcc;
       paramAcc.numero=numero;
       paramAcc.ds=dsServ;
       paramAcc.nbrConnexion=nbrConnexion;

       sprintf(paramAcc.port,"%d",portR);
        
    printf("//-------------------- lancemant du thread d'acceptation -------------------------//\n");

    
        printf("Client<%d> : lacemant du thread ",numero);
        if(pthread_create(&accept,NULL,acceptFnc,&paramAcc)!=0){
            perror("ERREUR CREATION THREAD ACCEPT!\n");
            exit(1);
        }



    printf("//-------------------- creation des threads demmande  -------------------------//\n");

    pthread_t demande;
    struct params paramDmnd;
    paramDmnd.numero = numero;
    //paramDmnd.ds = dsServ;
    paramDmnd.nbrConnexion = nbrConnexion;
    memcpy(paramDmnd.otherNumb,tabConnex,sizeof tabConnex);
    sprintf(paramDmnd.port,"%d",portR);

    printf("//-------------------- lancementdes threads demmande  -------------------------//\n");

    sleep(2);
	printf("Client<%d>: lancement du thread de demande de connexion <>%s\n", numero, paramDemande.port);
	if(pthread_create(&demande, NULL, demandeFnc, &paramDmnd) != 0){
	    perror("erreur creation thread demande");
	    exit(1);
	}

     int *tab;
	    pthread_join(demande, (void**)&(tab));
	    pthread_join(accept, NULL);


    for(int k = 0; k < nbrConnexion; k++){
	    printf("numero[%d] s'est connecte avec le numero <%d> : socket[%d]+++++++\n", numero, tabConnex[k], tab[k]);
	    close(tab[k]);
	}
    free(tab);

    close(dsServ);
    printf("Client %d: fin\n",numero);
    }else{while (wait(NULL)>0);}


    return 0;
 }



