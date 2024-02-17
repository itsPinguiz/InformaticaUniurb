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

char *generatore_pseudocasuale_lettere(long int *);
char *generatore_pseudocasuale_numeri(long int *,
                                      int,
                                      int,
                                      int);
void generatore_pseudocasuale_telemetrie(FILE*);
int inserisci_in_albero_bin_ric(nodo_albero_bin_t *,
                                nodo_albero_bin_t **,
                                int *,
                                int *);
nodo_albero_bin_t *cerca_in_albero_bin_ric(nodo_albero_bin_t *,
                                           char *,
                                           int *);
void visita_albero_bin_ant_calcoli(nodo_albero_bin_t *,
                                   nodo_albero_bin_t *,
                                   nodo_albero_bin_t *,
                                   nodo_albero_bin_t *,
                                   int *,
                                   int *,
                                   int *);

/******************************/
/* definizione delle funzioni */
/******************************/

/* funzione per generazione casuale */
char *generatore_pseudocasuale_numeri(long int *n, /* output: incremento rand */
                                      int minimo,  /* input : minimo numero generato */
                                      int massimo, /* input: massimo numero generato */
                                      int tipo)    /* input: tipo da generare */
{
  char *output = calloc(12,sizeof(char));

  double div = RAND_MAX / (massimo - minimo);

  srand(time(NULL) + *n); /*inizializza il generatore*/

  /* genera numeri tra min e max */
  if ( tipo == 0)
    sprintf(output,"%d",((rand() %(massimo - minimo + 1)) + minimo));
  else
    sprintf(output,"%lf",(minimo + (rand() / div)));
  *n += 10;

  return output;
}

/* funzione per generazione casuale pseudocasuale telemetrie*/
void generatore_pseudocasuale_telemetrie(FILE *file_p)
{
  /* dichiarazione delle variabili locali alla funzione */
  long int i,
           incremento_seme_1 = 0,
           incremento_seme_2 = 0;
  int validazione = 0,
      numero_elementi;

  do
  {
    printf("\nInserire numero di elementi da generare:\n=> ");
    validazione = scanf("%d",
                        &numero_elementi);
    if(validazione != 1 || numero_elementi < 1)
        printf("\nNumero inserito non valido");
    while (getchar() != '\n');
  } while (validazione != 1 || numero_elementi < 1);

  fprintf(file_p,"Id      GiriM   TempM   TempC   Vel     AccX    AccY    AccZ\n");/* stampa indice */
  for(i = 0;
      i < numero_elementi;
      i++)       
  {
    
    fprintf(file_p,"%s%s\t",generatore_pseudocasuale_lettere(&incremento_seme_1),
                            generatore_pseudocasuale_numeri(&incremento_seme_2,
                                                            100,
                                                            999,
                                                            0));
    fprintf(file_p,"%.6s\t",generatore_pseudocasuale_numeri(&incremento_seme_2,
                                                            0,
                                                            20000,
                                                            0));
    fprintf(file_p,"%.6s\t",generatore_pseudocasuale_numeri(&incremento_seme_2,
                                                            90,
                                                            110,
                                                            1));
    fprintf(file_p,"%.6s\t",generatore_pseudocasuale_numeri(&incremento_seme_2,
                                                            40,
                                                            50,
                                                            1));
    fprintf(file_p,"%.6s\t",generatore_pseudocasuale_numeri(&incremento_seme_2,
                                                            130,
                                                            220,
                                                            1));
    fprintf(file_p,"%.4s\t",generatore_pseudocasuale_numeri(&incremento_seme_2,
                                                            -10,
                                                            +10,
                                                            1));
    fprintf(file_p,"%.4s\t",generatore_pseudocasuale_numeri(&incremento_seme_2,
                                                            0,
                                                            1,
                                                            1));
    fprintf(file_p,"%.4s\n",generatore_pseudocasuale_numeri(&incremento_seme_2,
                                                            -2,
                                                            +2,
                                                            1));
  } 
}

/* definizionefunzione per inserimento in albero di ricerca */
int inserisci_in_albero_bin_ric(nodo_albero_bin_t *input,     /* input: nodo da inserire */
                                nodo_albero_bin_t **radice_p, /* input: radice dell'albero */
                                int * n_istruzioni,           /* output: numero di istruzioni eseguite */
                                int *n_nodi)                  /* nodi attraversati: istruzioni eseguite */
{
  /* dichiarazione delle variabili locali alla funzione */
  int inserito; /* output: controllo inserimento */
  nodo_albero_bin_t *nodo_p, /* lavoro: nodo da scorrere*/
                    *padre_p, /* lavoro: nodo padre */
                    *nuovo_p; /* lavoro: nuovo nodo */

  /* controllo inserimento */
  *n_istruzioni += 4;
  for (nodo_p = padre_p = *radice_p;
      ((nodo_p != NULL) && (strcmp(nodo_p->Id,input->Id)!= 0));
      padre_p = nodo_p, nodo_p = (strcmp(nodo_p->Id,input->Id)<0)?
                                  nodo_p->sx_p:
                                  nodo_p->dx_p)
  {
    *n_istruzioni += 4;
  }
  if (nodo_p != NULL)
  {
    inserito = 0;
    *n_istruzioni += 1;
  }
  else
  {
    /* inserimento */
    inserito = 1;
    *n_nodi += 1;
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
    {
      *radice_p = nuovo_p;
      *n_istruzioni += 1;
    }
    else if (strcmp(padre_p->Id,input->Id)<0)
    {
      padre_p->sx_p = nuovo_p;
      *n_istruzioni += 1;
    }
    else
    {
      padre_p->dx_p = nuovo_p;
      *n_istruzioni += 1;
    }
    *n_istruzioni += 13;
  }
  return(inserito);
}

/* definizione funzione per ricerca in albero binario di ricerca */
nodo_albero_bin_t *cerca_in_albero_bin_ric(nodo_albero_bin_t *radice_p, /* input: radice dell'albero */
                                           char *Id,                    /* input: id da ricercare */
                                           int * n_istruzioni)          /* output: numero di istruzioni eseguite */
{
  /* dichiarazione delle variabili locali alla funzione */
  nodo_albero_bin_t *nodo_p; /* lavoro: nodo da scorrre*/

  /* ricerca */
  for (nodo_p = radice_p;
      ((nodo_p != NULL) && (strcmp(nodo_p->Id,Id)!= 0));
      nodo_p = (strcmp(nodo_p->Id,Id)<0)?
                nodo_p->sx_p:
                nodo_p->dx_p)
  {
    *n_istruzioni += 3;
  }

  *n_istruzioni += 3;
  return(nodo_p);
}

/* funzione per generazione casuale */
char *generatore_pseudocasuale_lettere(long int *n) /* output : incremento rand */
{
  int i;
  char alfabeto[26] = {"abcdefghijklmnopqrstuvwxyz"},
       *sequenza = calloc(3,sizeof(char));

  srand(time(NULL) + *n); /*inizializza il generatore*/
  
  /* genera numeri tra min e max */
  for (i = 0;
       i < 3; 
       i ++)
  {
    sequenza[i] =  alfabeto[rand() %(25 + 1)];
    *n += 20;
  }
  return sequenza;
}

/* definizione funzione per calcolo massimi,minimi e media */
void visita_albero_bin_ant_calcoli(nodo_albero_bin_t *nodo_p, /* input: nodo da visitare */
                                   nodo_albero_bin_t *max,    /* output: struttura con risultati */
                                   nodo_albero_bin_t *min,    /* output: struttura con risultati */
                                   nodo_albero_bin_t *media,  /* output: struttura con risultati */
                                   int *max_istruzioni,         /* output: numero di istruzioni eseguite max */
                                   int *min_istruzioni,         /* output: numero di istruzioni eseguite min */
                                   int *med_istruzioni)         /* output: numero di istruzioni eseguite media*/
{
  if (nodo_p != NULL)
  {
    /* calcola max */
    if ( nodo_p->GiriM > max->GiriM)
    {
      max->GiriM = nodo_p->GiriM;
      *max_istruzioni += 1;
    }
    if ( nodo_p->TempC > max->TempC)
    {
      max->TempC = nodo_p->TempC;
      *max_istruzioni += 1;
    }
    if ( nodo_p->TempM > max->TempM)
    {
      max->TempM = nodo_p->TempM;
      *max_istruzioni += 1;
    }
    if ( nodo_p->Vel > max->Vel)
    {
      max->Vel = nodo_p->Vel; 
      *max_istruzioni += 1; 
    }
    if ( nodo_p->AccX > max->AccX)
    {
      max->AccX = nodo_p->AccX;
      *max_istruzioni += 1;
    }
    if ( nodo_p->AccY > max->AccY)
    {
      max->AccY = nodo_p->AccY;
      *max_istruzioni += 1;
    }
    if ( nodo_p->AccZ > max->AccZ)
    {
      max->AccZ = nodo_p->AccZ;
      *max_istruzioni += 1;
    }
    /* calcola min */
    if ( nodo_p->GiriM < min->GiriM)
    {
      min->GiriM = nodo_p->GiriM;
      *min_istruzioni += 1;
    }
    if ( nodo_p->TempC < min->TempC)
    {
      min->TempC = nodo_p->TempC;
      *min_istruzioni += 1;
    }
    if ( nodo_p->TempM < min->TempM)
    {
      min->TempM = nodo_p->TempM;
      *min_istruzioni += 1;
    }
    if ( nodo_p->Vel < min->Vel)
    {
      min->Vel = nodo_p->Vel;
      *min_istruzioni += 1;
    }
    if ( nodo_p->AccX < min->AccX)
    {
      min->AccX = nodo_p->AccX;
      *min_istruzioni += 1;
    }
    if ( nodo_p->AccY < min->AccY)
    {
      min->AccY = nodo_p->AccY;
      *min_istruzioni += 1;
    }
    if ( nodo_p->AccZ < min->AccZ)
    {
      min->AccZ = nodo_p->AccZ;
      *min_istruzioni += 1;
    }
    /* calcla media */
    media->GiriM += nodo_p->GiriM;
    media->TempC += nodo_p->TempC;
    media->TempM += nodo_p->TempM;
    media->Vel += nodo_p->Vel;
    media->AccX += nodo_p->AccX;
    media->AccY += nodo_p->AccY;
    media->AccZ += nodo_p->AccZ;;
    *med_istruzioni += 7;
    *max_istruzioni += 7;
    *min_istruzioni += 7;
    visita_albero_bin_ant_calcoli(nodo_p->sx_p,
                                  max,
                                  min,
                                  media,
                                  max_istruzioni,
                                  min_istruzioni,
                                  med_istruzioni);
    visita_albero_bin_ant_calcoli(nodo_p->dx_p,
                                  max,
                                  min,
                                  media,
                                  max_istruzioni,
                                  min_istruzioni,
                                  med_istruzioni);
  }
  *med_istruzioni += 1;
  *max_istruzioni += 1;
  *min_istruzioni += 1;
}

