/*******************************************************************************/
/* programma per lo studio di telemetrie da testo con statistiche e generatore */
/*******************************************************************************/

/*****************************/
/* inclusione delle librerie */
/*****************************/

#ifdef _WIN32
#include <conio.h>
#else
#include <stdio.h>
#define clrscr() printf("\e[1;1H\e[2J")
#endif
#include <stdlib.h>
#include <string.h>
#include <ctype.h> 
#include <time.h>
#include "lib.h"

/********************************/
/* dichiarazione delle funzioni */
/********************************/

void elabora_file(FILE*,
                  nodo_albero_bin_t*,
                  nodo_albero_bin_t **,
                  int *,
                  int *);
void riempi_struttura(nodo_albero_bin_t *,
                      nodo_albero_bin_t *);
int controlla_id(char *);
void ricerca(nodo_albero_bin_t *,
             nodo_albero_bin_t *,
             int *);
void stampa_struttura(char *,
                      nodo_albero_bin_t *,
                      int,
                      int);
void stampa_statistiche(int,
                        int,
                        int,
                        int,
                        int,
                        int,
                        double);

/******************************/
/* definizione delle funzioni */
/******************************/

/* definizione funzione main */
int main(int argc, char *argv[])
{
    /* dichiarazione delle variabili locali alla funzione */
    FILE *file_p; /* input: file da leggere */
    nodo_albero_bin_t *input = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t)),     /* input: struttura di input */
                      *radice = NULL,                                                      /* lavoro: radice dell'albero */
                      *ricercato = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t)), /* output: struttura dati ricercati */
                      *max = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t)),   /* output: struttura per max o min */
                      *min = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t)),       /* output: struttura per min */
                      *media = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t));     /* output: struttura per la media */
    int n_nodi = 0,         /* lavoro: numero di nodi */
        tot_istruzioni = 0, /* output: numero di istruzioni eseguite */
        ins_istruzioni = 0, /* output: istruzioni fase di inserimento */
        ric_istruzioni = 0, /* output: istruzioni fase di ricerca */
        max_istruzioni = 0, /* output: istruzioni calcolo massimo */
        min_istruzioni = 0, /* output: istruzioni calcolo minimo  */
        med_istruzioni = 0; /* output: istruzioni calcolo media  */
    double inizio = 0,      /* lavoro: inizio clock */
           fine,            /* lavoro: fine clock */
	       tempo_speso;     /* output: tempo esecuzione programma */
    
    inizio = clock();/* inizia clock */

    /* apertura file */
    file_p = fopen(argv[1],"w+");
    if(file_p == NULL) /* stampa errore se file non aperto */
        printf("\nErrore nell'apertura del file\n");
    else
    {
        /* genera telemetrie */
        generatore_pseudocasuale_telemetrie(file_p);

        rewind(file_p); /* riporta puntatore file all'inizio */
        /* estrapola dati da file e crea albero */
        elabora_file(file_p,
                     input,
                     &radice,
                     &tot_istruzioni,
                     &n_nodi); 
        ins_istruzioni = tot_istruzioni; 

        fclose(file_p); /* chiusura file */

        /* riempi le strutture */
        riempi_struttura(radice,
                         max);
        riempi_struttura(radice,
                         min);

        if(n_nodi == 0)
            printf("\nNon sono presenti veicoli.");
        else
        {
            /* acquisizione id da ricercare con validazione e ricercato */   
            ricerca(radice,
                    ricercato,
                    &tot_istruzioni);
            ric_istruzioni = tot_istruzioni;
            /* visita e stampa */
            visita_albero_bin_ant_calcoli(radice, /* caloca massimo,minimo,media */
                                          max,
                                          min,
                                          media,
                                          &max_istruzioni,
                                          &min_istruzioni,
                                          &med_istruzioni);
            stampa_struttura("\nMassimi:", /* stampa max */
                              max,
                              0,
                              n_nodi);
            stampa_struttura("\nMinimi:",  /* stampa min */
                             min,
                             0,
                             n_nodi);
            stampa_struttura("\nMedia:",  /* stampa media */
                            media,
                            1,
                            n_nodi);  
        }
        fine = clock(); /* fine clock */
        /*stampa tempo di calcolo e istruzioni eseguite */
        tempo_speso = (double)(fine - inizio) / CLOCKS_PER_SEC;
        stampa_statistiche(tot_istruzioni,
                            ins_istruzioni,
                            ric_istruzioni,
                            max_istruzioni,
                            min_istruzioni,
                            med_istruzioni,
                            tempo_speso);
    } 
    /* libera memoria */
    free(input);
    free(ricercato);
    free(max);
    free(min);
    free(media);

    tot_istruzioni += 13;
    return 0;
}

/* funzione per leggere file e creare albero con i dati*/
void elabora_file(FILE* file_p,               /* input: file da leggere */
                  nodo_albero_bin_t* input,   /* input: nodo da inserire */
                  nodo_albero_bin_t** radice, /* input: radice dell'albero*/
                  int *tot_istruzioni,          /* output: istruzioni eseguite */
                  int *n_nodi)                /* output: nodi creati  */
{
    /* scorri righe file */
    (void)(fscanf(file_p, "%*[^\n]\n")+1); /* salta indice*/

    while(fscanf(file_p,"%s %d %lf %lf %lf %lf %lf %lf",input->Id,
                                                        &input->GiriM,
                                                        &input->TempM,
                                                        &input->TempC,
                                                        &input->Vel,
                                                        &input->AccX,
                                                        &input->AccY,
                                                        &input->AccZ) == 8)     
        inserisci_in_albero_bin_ric(input, /* inserisci nodo in albero */
                                    radice,
                                    tot_istruzioni,
                                    n_nodi);
}

/* funzione per riempire struttura */
void riempi_struttura(nodo_albero_bin_t *radice,
                      nodo_albero_bin_t *struttura)
{
    struttura->GiriM = radice->GiriM;
    struttura->TempM = radice->TempM;
    struttura->TempC = radice->TempC;
    struttura->Vel = radice->Vel;
    struttura->AccX = radice->AccX;
    struttura->AccY = radice->AccY;
    struttura->AccZ = radice->AccZ;
}

/* funzione per controllare l'id inserito dall'utente */
int controlla_id(char *id)            /* input: id da controllare */
{
    /* dichiarazione delle variabili locali alla funzione */
    int i,
        controllo = 1;

    for (i=0;i<3; i++)
        if (isdigit(id[i]))
            controllo = 0;    

    for (i=3;i<6; i++)
        if (!isdigit(id[i]))
            controllo = 0;    
    return controllo;
}

/* funzione per acquisire, validare e cercare un dato in un albero */
void ricerca(nodo_albero_bin_t *radice,    /* input: radice dell'albero */
             nodo_albero_bin_t *ricercato, /* input: struttura per risultato ricerca*/
             int *tot_istruzioni)            /* output: numero di istruzioni eseguite */
{
    /* dichiarazione delle variabili locali alla funzione */
    int validazione = 0;
    char id_ricerca[7];

    /* validazione */
    do
    {
        /* acquisizione */
        do
        {
            printf("\n\nInserire ID automobile da ricercare tra quelli stampati, in formato alfanumerico (abc123):\n=> ");
            validazione = scanf("%s",
                                id_ricerca);
            validazione = controlla_id(id_ricerca);
            if(validazione != 1 || strlen(id_ricerca) != 6)
                printf("\nID inserito non valido");
            while (getchar() != '\n');
        } while (validazione != 1 || strlen(id_ricerca )!= 6);
        /* ricerca */
        ricercato = cerca_in_albero_bin_ric(radice,
                                            id_ricerca,
                                            tot_istruzioni);
        if (ricercato == NULL)
            printf("\nID non presente.");
        else
            stampa_struttura("\nTelemetrie automobile:",
                            ricercato,
                            0,
                            1);
    }while(ricercato == NULL);
}


/* funzione per stampare contenuti struttura*/
void stampa_struttura(char *frase,              /* input: frase da stampare */
                      nodo_albero_bin_t *input, /* input: struttura da stampare */
                      int media,
                      int n_nodi)
{
    if(media == 0)
    {
        printf("\n=======================");
        printf("%s",frase);
        printf("\n=======================");
        printf("\nGiriM: %d\nTempM: %.2lf\nTempC: %.2lf",input->GiriM,
                                                        input->TempM,
                                                        input->TempC);                                                                                     
        printf("\nVel: %.2lf\nAccX: %.2lf \nAccY: %.2lf\nAccZ: %.2lf\n",input->Vel,
                                                                        input->AccX,
                                                                        input->AccY,
                                                                    input->AccZ);
    }
    else
    {
        printf("\n=======================");
        printf("\nMedia:");
        printf("\n=======================");
        printf("\nGiriM: %d\nTempM: %.2lf\nTempC: %.2lf",input->GiriM/n_nodi,
                                                         input->TempM/n_nodi,
                                                         input->TempC/n_nodi);                                                                    
        printf("\nVel: %.2lf\nAccX: %.2lf \nAccY: %.2lf\nAccZ: %.2lf\n",input->Vel/n_nodi,
                                                                        input->AccX/n_nodi,
                                                                        input->AccY/n_nodi,
                                                                        input->AccZ/n_nodi);    
    }
}   

void stampa_statistiche(int tot_istruzioni, /* output: numero di istruzioni eseguite */
                        int ins_istruzioni, /* output: istruzioni fase di inserimento */
                        int ric_istruzioni, /* output: istruzioni fase di ricerca */
                        int max_istruzioni, /* output: istruzioni calcolo massimo  */
                        int min_istruzioni, /* output: istruzioni calcolo minimo  */
                        int med_istruzioni, /* output: istruzioni calcolo media  */
                        double tempo_speso) /* output: cps spesi */
{
    tot_istruzioni = tot_istruzioni + med_istruzioni + max_istruzioni + min_istruzioni;
    ric_istruzioni = ric_istruzioni - ins_istruzioni;
    printf("\n=============================================");
    printf("\n                Statistiche                  ");
    printf("\n=============================================");
    printf("\nTempo di esecuzione: %lfcps\n",tempo_speso);
    printf("Numero di istruzioni eseguite:\n");
    printf("-Totali: %d\n",tot_istruzioni);
    printf("-Totali (senza inserimento): %d\n",tot_istruzioni - ins_istruzioni);
    printf("-Inserimento : %d, %.2lf%% del totale.\n",ins_istruzioni,((double)ins_istruzioni/(double)tot_istruzioni)*100);
    printf("-Ricerca : %d, %.2lf%% del totale.\n",ric_istruzioni,((double)(ric_istruzioni)/(double)tot_istruzioni)*100);
    printf("-Massimo : %d, %.2lf%% del totale.\n",max_istruzioni,(((double)max_istruzioni)/(double)tot_istruzioni)*100);
    printf("-Minimo  : %d, %.2lf%% del totale.\n",min_istruzioni,(((double)min_istruzioni)/(double)tot_istruzioni)*100);
    printf("-Media   : %d, %.2lf%% del totale.\n",med_istruzioni,(((double)med_istruzioni)/(double)tot_istruzioni)*100);
}
