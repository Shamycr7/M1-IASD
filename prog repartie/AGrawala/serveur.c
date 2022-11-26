#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include<arpa/inet.h>
#include<string.h>

/* Programme serveur */

int main(int argc, char *argv[]) {

  /* Je passe en paramètre le numéro de port qui sera donné à la socket créée plus loin.*/

  /* Je teste le passage de parametres. Le nombre et la nature des
     paramètres sont à adapter en fonction des besoins. Sans ces
     paramètres, l'exécution doit être arrétée, autrement, elle
     aboutira à des erreurs.*/
  if (argc != 2){
    printf("utilisation : %s port_serveur\n", argv[0]);
    exit(1);
  }

  /* Etape 1 : créer une socket */   
  int ds = socket(PF_INET, SOCK_DGRAM, 0);

  /* /!\ : Il est indispensable de tester les valeurs de retour de
     toutes les fonctions et agir en fonction des valeurs
     possibles. Voici un exemple */
  if (ds == -1){
    perror("Serveur : pb creation socket :");
    exit(1); // je choisis ici d'arrêter le programme car le reste
	     // dépendent de la réussite de la création de la socket.
  }
  
  /* J'ajoute des traces pour comprendre l'exécution et savoir
     localiser des éventuelles erreurs */
  printf("Serveur : creation de la socket réussie \n");
  
  // Je peux tester l'exécution de cette étape avant de passer à la
  // suite. Faire de même pour la suite : n'attendez pas de tout faire
  // avant de tester.
  
  /* Etape 2 : Nommer la socket du seveur */
 struct sockaddr_in adr;
 adr.sin_addr.s_addr = INADDR_ANY ;
 adr.sin_family = AF_INET;
 adr.sin_port = htons((short) atoi(argv[1]));


 if( (bind(ds,(struct sockaddr*)&adr,sizeof(adr) )) < 0){
    perror("client : erreur bind");
    close(ds);
    exit(1);

 }

 printf("bind reussi!\n"); 
   /*char* c=NULL;
 scanf("%s",c);
*/
  /* Etape 4 : recevoir un message du client (voir sujet pour plus de détails)*/
 char messagesRecus[200]; 
  
   // variables utilisées pour récupérer l'adresse de l'expéditeur/ client.
  struct sockaddr_in addrC ;
  socklen_t lgAddrC = sizeof(struct sockaddr_in) ;
  
  printf("Serveur: j'attends de recevoir un premier message\n");
  int rcv = recvfrom(ds, messagesRecus, sizeof(long int), 0, (struct sockaddr*)&addrC, &lgAddrC);
    printf("recv %s %d\n",messagesRecus,rcv);
 
  if (rcv < 0){
    perror("erreur a la reception :");
    return 1;
  }

  // qui a envoyé le message reçu ? 
  printf("Serveur: le client %s:%d m'a envoyé un message  \n", inet_ntoa(addrC.sin_addr),  ntohs(addrC.sin_port));

  /* Etape 5 : envoyer un message au serveur (voir sujet pour plus de détails)*/
char m[202]; 
fgets(m, sizeof(m),stdin);
m[strlen(m)-1]  = '\0'; 

ssize_t res = sendto(ds,m,strlen(m)+1,0,(struct sockaddr*)&addrC,lgAddrC);

if(res<0){
perror("client: pb d'envoie !");
close(ds);
exit(1);
}

printf("le nombre d'octets déposer dans le buffer:%zd\n",res);

  
  /* Etape 6 : fermer la socket (lorsqu'elle n'est plus utilisée)*/
  
  
  printf("Serveur : je termine\n");
  return 0;
}
