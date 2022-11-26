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



struct parametre {
    int numero_P;
    int descripteur;
    int nombre_connexion;
    char port[10];
    int otherNumb[];
};

int proc(int nbprecessus){
    for(int i=0; i<nbprecessus; i++){
        if(fork() == 0) return (i+1);
    }
    return(0);
}
    
void *demande(void *param){
    struct parametre *args = (struct parametre *) param;
    int numero_P = args->numero_P;
    int nombre_connexion = args->nombre_connexion;
    int otherNumb[nombre_connexion];
    for(int i = 0; i < nombre_connexion; i++) otherNumb[i] = args->otherNumb[i];
    char *portChar = args->port;
    int port = atoi(args->port);
    int *tabDs = malloc(sizeof(int) * nombre_connexion); // contient le descripteur des connexion
    int i = 0;
    printf("Client<%d>: Thread Demande: dans le thread demande avec le port <%s>\n", numero_P, portChar);

    while(i < nombre_connexion){
        printf("Client<%d>: Thread Demande: mon port <%d>\n", numero_P, port);
        int portAutre = 0;

        // Il faut faire la demande sur le bon numero de port 
        portAutre = port + otherNumb[i] - numero_P;
        sprintf(portChar, "%d", portAutre);
        printf("Client<%d>: Thread Demande: port autre <%d>, numero autre <%d>\n", numero_P, portAutre, otherNumb[i]);

        tabDs[i] = socket(PF_INET, SOCK_STREAM, 0);
        if(tabDs[i] == -1){
            printf("Client : pb creation socket\n");
            exit(1);
        }
        printf("Client<%d>: Thread Demande: creation de la socket <%d> pour le numero <%d> : ok\n", numero_P, tabDs[i], otherNumb[i]);

        struct sockaddr_in adrAutreClient;
        adrAutreClient.sin_family = AF_INET;
        adrAutreClient.sin_addr.s_addr = INADDR_ANY;
        adrAutreClient.sin_port = htons((short)atoi(portChar));
        int lgAdrPr = sizeof(struct sockaddr_in);
        printf("Client<%d>: Thread Demande: envoie du demande vers le processus numero <%s> \n", args->numero, portChar);
        int conn = connect(tabDs[i], (struct sockaddr *) &adrAutreClient, lgAdrPr);
        if(conn < 0){
        	printf("<%d>", numero_P);
            perror ("Client: Thread Demande: pb au connect :");
            close (tabDs[i]);
            exit (1);
        }
        printf("Client<%d>: Thread Demande: demande de connexion au processus <%d> reussie\n", args->numero, otherNumb[i]);
        i++;
    }

    // printf("<%d>==================================\n", numero);
    // for(int k = 0; k < nbrConnexion; k++){
    //     printf("[%d]", tabDs[k]);
    // }
    // printf("\n==================================\n");
    pthread_exit(tabDs);
}


void* accepte(void* param){
    struct parametre *args = (struct parametre *) param;
    int numero_P = args->numero_P;
    int descripteur = args->descripteur;
    int nombre_connexion = args->nombre_connexion;
    printf("Client<%d>: Thread Accepte: dans le thread accepte\n", numero_P);

    struct sockaddr_in adCV;
    socklen_t lgCV = sizeof(struct sockaddr_in);
     int dsCV;
    for(int i =0; i<nombre_connexion ; i++){
        printf("Client<%d> : THREAD ACCEPT : ATTENTE DE CONNECTION DU %d client\n ",numero_P,i);
        dsCV = accept(descripteur,(struct sockaddr *) &adCV,&lgCV);
        if(dsCV < 0){
            perror("Client<> preomblem acceptation\n");
            close(descripteur);
            exit(1);
        }
        printf("Client<%d>: Thread Accepte: le client <%d> %s:%d est connecté  \n", numero_P, i, inet_ntoa(adCV.sin_addr),  dsCV);


    }
    close(dsCV);
}
    
int mise_enplace(int tabLiaison[][2], int nbrProcessus, int nbrLiaison){
        int portR = 7777;
        int tabConnex[nbrProcessus];
        int nombre_connexion = 0;
        
        printf("//-------------------- creation des processus -------------------------//\n");
        
        int numero_P = proc(nbrProcessus);
        if(numero_P != 0){
		

		//----creation d'un tableau contenant la liste des processus a connecter avec celle la
		for(int i = 0; i < nbrLiaison; i++){
			if(tabLiaison[i][0] == numero_P || tabLiaison[i][1] == numero_P){
				if(tabLiaison[i][0] != numero_P) tabConnex[nombre_connexion] = tabLiaison[i][0];
				else tabConnex[nombre_connexion] = tabLiaison[i][1];
				//printf("[%d][%d]\n", numero, tabConnexion[nbrConnexion]);
	    		nombre_connexion++;
			}
	    }
        
        
    


    printf("//-------------------- creation des socket -------------------------//\n");

    int dsServ = socket (PF_INET,SOCK_STREAM,0);
    if(dsServ == -1){
        printf("Client<%d> : pb creation socket\n",numero_P);
        exit(1);
    }
    printf("Client<%d> : creation de la socket réussie \n",numero_P);


    printf("//-------------------- reglage des ports -------------------------//\n");
    portR = portR + numero_P;
	char portReseau[10];
	printf(portReseau, "%d", portR);
    

     printf("//-------------------- nommage des sockets -------------------------//\n");
     struct sockaddr_in server;
     server.sin_family = AF_INET;
     server.sin_addr.s_addr = INADDR_ANY;
     server.sin_port = (htons ((short)atoi(portReseau)));
     if(bind(dsServ,(struct sockaddr*)&server,sizeof(server))<0){
        printf("Client<%d>: erreur bind", numero_P);
        close(dsServ);
        exit(1);
        }
    
     printf("Client<%d> : bind réussie \n",numero_P);



    printf("//-------------------- nommage des sockets -------------------------//\n");

    int ecoute = listen(dsServ,nbrProcessus);
    if(ecoute < 0){
        printf("Client<%d> : je suis sourd(e) \n",numero_P);
        close(dsServ);
        exit(1);
    }

    printf("Client<%d> : serveur mise en ecoute \n",numero_P);

    printf("//-------------------- creation des threads accept  -------------------------//\n");

       
       pthread_t accept;

       struct parametre paramAcc;
       paramAcc.numero_P=numero_P;
       paramAcc.descripteur=dsServ;
       paramAcc.nombre_connexion=nombre_connexion;

       sprintf(paramAcc.port,"%d",portR);
        
    printf("//-------------------- lancemant du thread d'acceptation -------------------------//\n");

    
        printf("Client<%d> : lacemant du thread ",numero_P);
        if(pthread_create(&accept,NULL,accepte,&paramAcc)!=0){
            perror("ERREUR CREATION THREAD ACCEPT!\n");
            exit(1);
        }



    printf("//-------------------- creation des threads demmande  -------------------------//\n");

    pthread_t demande;
    struct parametre paramDmnd;
    paramDmnd.numero_P = numero_P;
    //paramDmnd.ds = dsServ;
    paramDmnd.nombre_connexion = nombre_connexion;
    memcpy(paramDmnd.otherNumb,tabConnex,sizeof tabConnex);
    sprintf(paramDmnd.port,"%d",portR);

    printf("//-------------------- lancementdes threads demmande  -------------------------//\n");

    sleep(2);
	printf("Client<%d>: lancement du thread de demande de connexion <>%s\n", numero_P, paramDemande.port);
	if(pthread_create(&demande, NULL, demandeFnc, &paramDmnd) != 0){
	    perror("erreur creation thread demande");
	    exit(1);
	}

     int *tab;
	    pthread_join(demande, (void**)&(tab));
	    pthread_join(accept, NULL);


    for(int k = 0; k < nombre_connexion; k++){
	    printf("numero[%d] s'est connecte avec le numero <%d> : socket[%d]+++++++\n", numero_P, tabConnex[k], tab[k]);
	    close(tab[k]);
	}
    free(tab);

    close(dsServ);
    printf("Client %d: fin\n",numero_P);
    }else{while (wait(NULL)>0);}


    return 0;
 }



