/*****************************/
/* inclusione delle librerie */
/*****************************/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "lib.h"


/********************************/
/* dichiarazione delle funzioni */
/********************************/

int inserisci_in_albero_bin_ric(nodo_albero_bin_t *,
                                nodo_albero_bin_t **);
void visita_albero_bin_ant(nodo_albero_bin_t *,
                                int *);
nodo_albero_bin_t *cerca_in_albero_bin_ric(nodo_albero_bin_t *, 
                                           char *);             
void visita_albero_bin_ant_calcoli(nodo_albero_bin_t *,
                                   nodo_albero_bin_t *,
                                   nodo_albero_bin_t *,
                                   nodo_albero_bin_t *,
                                   int *);

/******************************/
/* definizione delle funzioni */
/******************************/

/* definizione funzione per visita anticipata */
void visita_albero_bin_ant(nodo_albero_bin_t *nodo_p, /* input: nodo da visitare */
                           int *n_nodi)               /* output: numero di nodi visitati */
{
  if (nodo_p != NULL)
  {
    /* stampa in righe di 10 */
    if((*n_nodi % 10) == 0)
      printf("\n");
    *n_nodi += 1;
    printf("[%s] ",(nodo_p->Id));
    visita_albero_bin_ant(nodo_p->sx_p,
                          n_nodi);
    visita_albero_bin_ant(nodo_p->dx_p,
                          n_nodi);
  }
}

/* definizione funzione per calcolo massimi,minimi e media */
void visita_albero_bin_ant_calcoli(nodo_albero_bin_t *nodo_p, /* input: nodo da visitare */
                                   nodo_albero_bin_t *max,    /* output: struttura con risultati */
                                   nodo_albero_bin_t *min,    /* output: struttura con risultati */
                                   nodo_albero_bin_t *media,  /* output: struttura con risultati */
                                   int *n_nodi)               /* output: numero di nodi visitati */
{
  if (nodo_p != NULL)
  {
    /* calcola max */
    if ( nodo_p->GiriM > max->GiriM)
      max->GiriM = nodo_p->GiriM;
    if ( nodo_p->TempC > max->TempC)
      max->TempC = nodo_p->TempC;
    if ( nodo_p->TempM > max->TempM)
      max->TempM = nodo_p->TempM;
    if ( nodo_p->Vel > max->Vel)
      max->Vel = nodo_p->Vel;  
    if ( nodo_p->AccX > max->AccX)
      max->AccX = nodo_p->AccX;
    if ( nodo_p->AccY > max->AccY)
      max->AccY = nodo_p->AccY;
    if ( nodo_p->AccZ > max->AccZ)
      max->AccZ = nodo_p->AccZ;
    /* calcola min */
    if ( nodo_p->GiriM < min->GiriM)
      min->GiriM = nodo_p->GiriM;
    if ( nodo_p->TempC < min->TempC)
      min->TempC = nodo_p->TempC;
    if ( nodo_p->TempM < min->TempM)
      min->TempM = nodo_p->TempM;
    if ( nodo_p->Vel < min->Vel)
      min->Vel = nodo_p->Vel;
    if ( nodo_p->AccX < min->AccX)
      min->AccX = nodo_p->AccX;
    if ( nodo_p->AccY < min->AccY)
      min->AccY = nodo_p->AccY;
    if ( nodo_p->AccZ < min->AccZ)
      min->AccZ = nodo_p->AccZ;
    /* calcla media */
    media->GiriM += nodo_p->GiriM;
    media->TempC += nodo_p->TempC;
    media->TempM += nodo_p->TempM;
    media->Vel += nodo_p->Vel;
    media->AccX += nodo_p->AccX;
    media->AccY += nodo_p->AccY;
    media->AccZ += nodo_p->AccZ;
    *n_nodi += 1;
    visita_albero_bin_ant_calcoli(nodo_p->sx_p,
                                  max,
                                  min,
                                  media,
                                  n_nodi);
    visita_albero_bin_ant_calcoli(nodo_p->dx_p,
                                  max,
                                  min,
                                  media,
                                  n_nodi);
  }
}
/* definizione funzione per ricerca in albero binario di ricerca */
nodo_albero_bin_t *cerca_in_albero_bin_ric(nodo_albero_bin_t *radice_p, /* input: radice dell'albero */
                                           char *Id)                    /* input: id da ricercare */
{
  /* dichiarazione delle variabili locali alla funzione */
  nodo_albero_bin_t *nodo_p; /* lavoro: nodo da scorrre*/

  /* ricerca */
  for (nodo_p = radice_p;
      ((nodo_p != NULL) && (strcmp(nodo_p->Id,Id)!= 0));
      nodo_p = (strcmp(nodo_p->Id,Id)<0)?
                nodo_p->sx_p:
                nodo_p->dx_p);
  return(nodo_p);
}

/* definizionefunzione per inserimento in albero di ricerca */
int inserisci_in_albero_bin_ric(nodo_albero_bin_t *input,     /* input: nodo da inserire */
                                nodo_albero_bin_t **radice_p) /* input: radice dell'albero */
{
  /* dichiarazione delle variabili locali alla funzione */
  int inserito; /* output: controllo inserimento */
  nodo_albero_bin_t *nodo_p, /* lavoro: nodo da scorrere*/
                    *padre_p, /* lavoro: nodo padre */
                    *nuovo_p; /* lavoro: nuovo nodo */

  /* controllo inserimento */
  for (nodo_p = padre_p = *radice_p;
      ((nodo_p != NULL) && (strcmp(nodo_p->Id,input->Id)!= 0));
      padre_p = nodo_p, nodo_p = (strcmp(nodo_p->Id,input->Id)<0)?
                                  nodo_p->sx_p:
                                  nodo_p->dx_p);
  if (nodo_p != NULL)
  {
    inserito = 0;
  }
  else
  {
    /* inserimento */
    inserito = 1;
    nuovo_p = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t));
    /* copia dati */
    strcpy(nuovo_p->Id,input->Id);
    nuovo_p->GiriM = input->GiriM;
    nuovo_p->TempC = input->TempC;
    nuovo_p->TempM = input->TempM;
    nuovo_p->Vel = input->Vel;
    nuovo_p->AccX = input->AccX;
    nuovo_p->AccY = input->AccY;
    nuovo_p->AccZ = input->AccZ;

    nuovo_p->sx_p = nuovo_p->dx_p = NULL;
    if (nodo_p == *radice_p)
      *radice_p = nuovo_p;
    else if (strcmp(padre_p->Id,input->Id)<0)
    {
      padre_p->sx_p = nuovo_p;

    }
    else
      padre_p->dx_p = nuovo_p;
  }
  return(inserito);
}

