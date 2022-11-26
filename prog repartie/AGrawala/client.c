#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include<arpa/inet.h>
#include<string.h>

/* Programme client */

int main(int argc, char *argv[]) {

  /* je passe en paramètre l'adresse de la socket du serveur (IP et
     numéro de port) et un numéro de port à donner à la socket créée plus loin.*/

  /* Je teste le passage de parametres. Le nombre et la nature des
     paramètres sont à adapter en fonction des besoins. Sans ces
     paramètres, l'exécution doit être arrétée, autrement, elle
     aboutira à des erreurs.*/
  if (argc != 4){
    printf("utilisation : %s ip_serveur port_serveur port_client\n", argv[0]);
    exit(1);
  }

  /* Etape 1 : créer une socket */   
  int ds = socket(PF_INET, SOCK_DGRAM, 0);

  /* /!\ : Il est indispensable de tester les valeurs de retour de
     toutes les fonctions et agir en fonction des valeurs
     possibles. Voici un exemple */
  if (ds == -1){
    perror("Client : pb creation socket :");
    exit(1); // je choisis ici d'arrêter le programme car le reste
	     // dépendent de la réussite de la création de la socket.
  }
  
  /* J'ajoute des traces pour comprendre l'exécution et savoir
     localiser des éventuelles erreurs */
  printf("Client : creation de la socket réussie \n");
  
  // Je peux tester l'exécution de cette étape avant de passer à la
  // suite. Faire de même pour la suite : n'attendez pas de tout faire
  // avant de tester.
  
  /* Etape 2 : Nommer la socket du client */
 struct sockaddr_in ad;
 ad.sin_addr.s_addr = INADDR_ANY ;
 
 ad.sin_family = AF_INET;
 ad.sin_port = htons((short) atoi(argv[3]));
 socklen_t lgAdr = sizeof(struct sockaddr_in);

 if( (bind(ds,(struct sockaddr*)&ad,lgAdr )) < 0){
    perror("client : erreur bind");
    close(ds);
    exit(1);

 }

 printf("bind reussi!\n");
  /* Etape 3 : Désigner la socket du serveur */
  

  
  /* Etape 4 : envoyer un message au serveur  (voir sujet pour plus de détails)*/
  
  
printf("saisir un message à envoyer (moins de 200 caracteres) \n");
struct sockaddr_in sockServ ;
sockServ.sin_addr.s_addr = inet_addr(argv[1]) ;
sockServ.sin_family = AF_INET;
sockServ.sin_port = htons((short) atoi(argv[2]));
char m[202]; 
fgets(m, sizeof(m),stdin);
m[strlen(m)-1]  = '\0'; 

ssize_t res = sendto(ds,m,strlen(m)+1,0,(struct sockaddr*)&sockServ,lgAdr);

if(res<0){
perror("client: pb d'envoie !");
close(ds);
exit(1);
}

printf("le nombre d'octets déposer dans le buffer:%zd\n",res);
  /* Etape 5 : recevoir un message du serveur (voir sujet pour plus de détails)*/
  
 /* struct sockaddr_in sockClient ;
  socklen_t lgadr ;*/
  char message [200];

   ssize_t rcv = recvfrom(ds,message,sizeof(message),0,(struct sockaddr*)&sockServ,&lgAdr);

if(rcv<0){
perror("client: pb de reception !");
close(ds);
exit(1);
} 

printf("le nombre d'octets recu dans le buffer:%zd  %s\n",rcv,message);

  /* Etape 6 : fermer la socket (lorsqu'elle n'est plus utilisée)*/
  
   close(ds);
  
  printf("Client : je termine\n");
  return 0;
}
