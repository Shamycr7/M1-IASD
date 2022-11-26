

struct params {
    int numero;
    int ds;
    int nbrConnexion;
    char port[10];
    int numeroAutres[];
};


int processus(int nbrProcessus){
	int i;
	for(i = 0; i < nbrProcessus; i++){
    	if(fork() == 0) return (i + 1);
    	// sleep(1);
    }
    return 0;
}


void *accepteFonc(void * param){
    struct params *args = (struct params *) param;
    int numero = args->numero;
    int ds = args->ds;
    int nbrConnexion = args->nbrConnexion;
    printf("Client<%d>: Thread Accepte: dans le thread accepte\n", numero);

    struct sockaddr_in adCv;
    socklen_t lgCv = sizeof (struct sockaddr_in);
    int dsCv;
    for(int i = 0; i < nbrConnexion; i++){
        printf("Client<%d>: Thread Accepte: attente de connection du client <%d> \n", numero, i);
        dsCv = accept(ds, (struct sockaddr *) &adCv, &lgCv);
        if(dsCv < 0){ 
            perror ( "Client: Thread Demande: probleme accept :");
            close(ds);
            exit (1);
        }
        printf("Client<%d>: Thread Accepte: le client <%d> %s:%d est connecté  \n", numero, i, inet_ntoa(adCv.sin_addr),  dsCv);
    }
    close(dsCv);
}


void *demandeFonc(void *param){
    struct params *args = (struct params *) param;
    int numero = args->numero;
    int nbrConnexion = args->nbrConnexion;
    int numeroAutres[nbrConnexion];
    for(int i = 0; i < nbrConnexion; i++) numeroAutres[i] = args->numeroAutres[i];
    char *portChar = args->port;
    int port = atoi(args->port);
    int *tabDs = malloc(sizeof(int) * nbrConnexion); // contient le descripteur des connexion
    int i = 0;
    printf("Client<%d>: Thread Demande: dans le thread demande avec le port <%s>\n", numero, portChar);

    while(i < nbrConnexion){
        printf("Client<%d>: Thread Demande: mon port <%d>\n", numero, port);
        int portAutre = 0;

        // Il faut faire la demande sur le bon numero de port 
        portAutre = port + numeroAutres[i] - numero;
        sprintf(portChar, "%d", portAutre);
        printf("Client<%d>: Thread Demande: port autre <%d>, numero autre <%d>\n", numero, portAutre, numeroAutres[i]);

        tabDs[i] = socket(PF_INET, SOCK_STREAM, 0);
        if(tabDs[i] == -1){
            printf("Client : pb creation socket\n");
            exit(1);
        }
        printf("Client<%d>: Thread Demande: creation de la socket <%d> pour le numero <%d> : ok\n", numero, tabDs[i], numeroAutres[i]);

        struct sockaddr_in adrAutreClient;
        adrAutreClient.sin_family = AF_INET;
        adrAutreClient.sin_addr.s_addr = INADDR_ANY;
        adrAutreClient.sin_port = htons((short)atoi(portChar));
        int lgAdrPr = sizeof(struct sockaddr_in);
        printf("Client<%d>: Thread Demande: envoie du demande vers le processus numero <%s> \n", args->numero, portChar);
        int conn = connect(tabDs[i], (struct sockaddr *) &adrAutreClient, lgAdrPr);
        if(conn < 0){
        	printf("<%d>", numero);
            perror ("Client: Thread Demande: pb au connect :");
            close (tabDs[i]);
            exit (1);
        }
        printf("Client<%d>: Thread Demande: demande de connexion au processus <%d> reussie\n", args->numero, numeroAutres[i]);
        i++;
    }

    // printf("<%d>==================================\n", numero);
    // for(int k = 0; k < nbrConnexion; k++){
    //     printf("[%d]", tabDs[k]);
    // }
    // printf("\n==================================\n");
    pthread_exit(tabDs);
}


int creation(int tabLiaison[][2], int nbrProcessus, int nbrLiaion){

	int portR = 5555;
	int tabConnexion[nbrProcessus];
	int nbrConnexion = 0;

	//--------------------------------------------------------creation des processus 
	int numero = processus(nbrProcessus);
	if(numero != 0){
		//printf("<%d>\n", numero); =

		//--------------------------------------------------------creation d'un tableau contenant la liste des processus a connecter avec celle la
		for(int i = 0; i < nbrLiaion; i++){
			if(tabLiaison[i][0] == numero || tabLiaison[i][1] == numero){
				if(tabLiaison[i][0] != numero) tabConnexion[nbrConnexion] = tabLiaison[i][0];
				else tabConnexion[nbrConnexion] = tabLiaison[i][1];
				//printf("[%d][%d]\n", numero, tabConnexion[nbrConnexion]);
	    		nbrConnexion++;
			}
	    }

	    //printf("<%d><%d>\n", numero, nbrConnexion);

		//--------------------------------------------------------creation des sockets d'ecoute
		int dsSer = socket(PF_INET, SOCK_STREAM, 0);
	    if(dsSer == -1){
	        printf("Client<%d> : pb creation socket\n", numero);
	        exit(1);
	    }
	    //printf("Client<%d>: creation de la socket <%d> serveur : ok\n", numero, dsSer); =

		//--------------------------------------------------------reglage des port
	    portR = portR + numero;
	    char portReseau[10];
	    sprintf(portReseau, "%d", portR); 

	    //--------------------------------------------------------nommage des sockets
	    struct sockaddr_in server;
	    server.sin_family = AF_INET;
	    server.sin_addr.s_addr = INADDR_ANY;
	    server.sin_port = (htons((short)atoi(portReseau)));
	    if(bind(dsSer, (struct sockaddr*)&server, sizeof(server)) < 0){
	        perror("Client<%d>: erreur bind"), numero;
	        close(dsSer); 
	        exit(1); 
	    }
	    //printf("Client<%d>: nommage : ok\n", numero); =
	  
	  	//--------------------------------------------------------ecoute des sockets
	    int ecoute = listen(dsSer, nbrProcessus);
	    if(ecoute < 0){
	        printf("Client<%d>: je suis sourd(e)\n", numero);
	        close (dsSer);
	        exit (1);
	    } 
	    //printf("Client<%d>: mise en écoute du socket serveur: ok\n", numero); =

	    //--------------------------------------------------------creation du thread d'acceptation de connexion
	    pthread_t demande, accepte;
	    struct params paramAccepte;
	    paramAccepte.numero = numero;
	    paramAccepte.ds = dsSer;
	    paramAccepte.nbrConnexion = nbrConnexion;
	    sprintf(paramAccepte.port, "%d", portR); 

	    //--------------------------------------------------------lancement du thread d'acceptation de connexion
	    printf("Client<%d>: lancement du thread d'acceptation de connexion\n", numero);
	    if(pthread_create(&accepte, NULL, accepteFonc, &paramAccepte) != 0){
	        perror("erreur creation thread accepte");
	        exit(1);
	    }

	    //--------------------------------------------------------creation du thread de demande de connexion
	    struct params paramDemande;
	    paramDemande.numero = numero;
	    paramDemande.nbrConnexion = nbrConnexion;
	    memcpy(paramDemande.numeroAutres, tabConnexion, sizeof tabConnexion);
	    sprintf(paramDemande.port, "%d", portR); 

	    //--------------------------------------------------------lancement du thread de demande de connexion
	    sleep(2);
	    printf("Client<%d>: lancement du thread de demande de connexion <>%s\n", numero, paramDemande.port);
	    if(pthread_create(&demande, NULL, demandeFonc, &paramDemande) != 0){
	        perror("erreur creation thread demande");
	        exit(1);
	    }
	   
	    int *tabDs;
	    pthread_join(demande, (void**)&(tabDs));
	    pthread_join(accepte, NULL);

	    for(int k = 0; k < nbrConnexion; k++){
	        printf("numero[%d] s'est connecte avec le numero <%d> : socket[%d]+++++++\n", numero, tabConnexion[k], tabDs[k]);
	        close(tabDs[k]);
	    }
	    free(tabDs);

	    close (dsSer);
	    printf("Client<%d>: je termine______________________________________________\n", numero);
	}else{

		//--------------------------------------------------------attente de la terminaison de tout les processus
		while(wait(NULL) > 0);
	}

	//--------------------------------------------------------
	return 0;
}