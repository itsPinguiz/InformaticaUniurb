/*****************************/
/* inclusione delle librerie */
/*****************************/
#include <stdio.h>
#include <stdlib.h>

/*****************************/
/* definzione struttura dato */
/*****************************/
typedef struct nodo_albero_bin
{
  char Id[7];
	int GiriM;
	double TempM,
         TempC,
         Vel,
         AccX,
         AccY,
         AccZ;  
  struct nodo_albero_bin *sx_p, *dx_p;
} nodo_albero_bin_t;

/*************************/
/* extern delle funzioni */
/*************************/

extern void generatore_pseudocasuale_telemetrie(FILE*);
extern int inserisci_in_albero_bin_ric(nodo_albero_bin_t *,
                                       nodo_albero_bin_t **,
                                       int *,
                                       int *);
extern void visita_albero_bin_ant_calcoli(nodo_albero_bin_t *,
                                          nodo_albero_bin_t *,
                                          nodo_albero_bin_t *,
                                          nodo_albero_bin_t *,
                                          int *,
                                          int *,
                                          int *);
extern nodo_albero_bin_t *cerca_in_albero_bin_ric(nodo_albero_bin_t *,
                                                  char *,
                                                  int *);



