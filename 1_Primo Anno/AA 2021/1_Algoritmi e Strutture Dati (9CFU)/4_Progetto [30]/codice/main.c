/**************************************************/
/* programma per lo studio di telemetrie da testo */
/**************************************************/

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
#include <unistd.h>
#include "lib.h"

/********************************/
/* dichiarazione delle funzioni */ 
/********************************/ 

int menu(); 
void riempi_struttura(nodo_albero_bin_t *,
                      nodo_albero_bin_t *);
void elabora_file(FILE*,
                  nodo_albero_bin_t *,
                  nodo_albero_bin_t **);
int controlla_id(char *);
void ricerca(nodo_albero_bin_t *,
             nodo_albero_bin_t *);
void stampa_struttura(char *,
                      nodo_albero_bin_t *,
                      int,
                      int);

/******************************/
/* definizione delle funzioni */
/******************************/

/* definizione funzione main */
int main(int argc, char *argv[])
{
    /* dichiarazione delle variabili locali alla funzione */
    FILE *file_p;    /* input: file da leggere */
    int n_nodi = 0,      /* lavoro: numero di nodi */
        scelta_menu = 0; /* lavoro: variabile per navigazione menu*/
    nodo_albero_bin_t *input = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t)),     /* input: struttura di input */
                      *radice = NULL,                                                      /* lavoro: radice dell'albero */
                      *ricercato = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t)), /* output: struttura dati ricercati */
                      *max = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t)),   /* output: struttura per max*/
                      *min = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t)),   /* output: struttura per min */
                      *media = (nodo_albero_bin_t *)malloc(sizeof(nodo_albero_bin_t));     /* output: struttura per la media */

    /* apertura file */
    file_p = fopen(argv[1],"r");
    if(file_p == NULL) /* stampa errore se file non aperto */
        printf("\nErrore nell'apertura del file. Assicurarsi di aver specificato il nome esatto del file negli argomenti del comando di esecuzione.\n");
    else
    {
        scelta_menu = menu();
        /* estrapola dati da file e crea albero */
        elabora_file(file_p,
                     input,
                     &radice); 
        fclose(file_p); /* chiusura file */
        riempi_struttura(radice,
                         max);
        riempi_struttura(radice,
                         min);
        visita_albero_bin_ant_calcoli(radice, /* caloca massimo,minimo,media */
                                      max,
                                      min,
                                      media,
                                      &n_nodi);
        while(scelta_menu != 0)
        {
            switch(scelta_menu)
            {
                case 1:
                    clrscr(); /* pulisci schermo */
                    /* stampa veicoli nell'albero */
                    if(n_nodi == 0)
                        printf("\nNon sono presenti veicoli.\n");
                    else
                    {
                        n_nodi = 0;
                        visita_albero_bin_ant(radice,
                                              &n_nodi);
                    }
                    break;
                case 2:
                    clrscr(); /* pulisci schermo */
                    /* acquisizione id da ricercare con validazione e ricercato */   
                    ricerca(radice,
                            ricercato);
                    break;
                case 3:
                    clrscr(); /* pulisci schermo */
                    /* visita e stampa */
                    stampa_struttura("\nMassimi:",           /* stampa max */
                                    max,
                                    0,
                                    n_nodi);
                    stampa_struttura("\nMinimi:",            /* stampa min */
                                    min,
                                    0,
                                    n_nodi);
                    stampa_struttura("\nMedia:",             /* stampa media */
                                    media,
                                    1,
                                    n_nodi);  
                    break;
            }
            printf("\n\nPremi invio per tornare al menu.");
            while (getchar() != '\n');
            scelta_menu = menu();
        }
    } 
    /* libera memoria */
    free(input);
    free(ricercato);
    free(max);
    free(min);
    free(media);
    free(radice);
    return 0;
}

/* funzione di stampa menu e acquisizione scelta */
int menu()
{
    /* dichiarazione delle variabili locali alla funzione */
    int scelta = 0,         /* output: scelta utente */
        validazione = 0; /* lavoro: variabile validazione scanf */

    clrscr();  /* pulisci schermo */
    do
    {
        printf("\n\t\t===================================");
        printf("\n\t\t=              MENU               =");
        printf("\n\t\t===================================");
        printf("\n\n\t\tScegliere tra le segueti opzioni:");
        printf("\n\t\t1. Stampa telemetrie");
        printf("\n\t\t2. Cerca telemetria");
        printf("\n\t\t3. Stampa Minimo,Massimo e Media");
        printf("\n\t\t0. Esci");
        printf("\n\t\t=> ");
        validazione = scanf("%d",&scelta);

        if(validazione != 1 || (scelta > 3 || scelta < 0))
        {
            sleep(1);
            clrscr();
        }
        while (getchar() != '\n');
    } while (validazione != 1 || (scelta > 3 || scelta < 0));
    return scelta;
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

/* funzione per leggere file e creare albero con i dati*/
void elabora_file(FILE* file_p,               /* input: file da leggere */
                  nodo_albero_bin_t* input,   /* input: nodo da inserire */
                  nodo_albero_bin_t** radice) /* input: radice dell'albero*/
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
                                    radice);
}

/* funzione per controllare l'id inserito dall'utente */
int controlla_id(char *id) /* input: id da controllare */
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

    if (strcmp("esci",id) == 0)
        controllo = 2;
    return controllo;
}

/* funzione per acquisire, validare e cercare un dato in un albero */
void ricerca(nodo_albero_bin_t *radice,    /* input: radice dell'albero */
             nodo_albero_bin_t *ricercato) /* input: struttura per risultato ricerca*/
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
            printf("\n\nInserire ID automobile da ricercare, in formato alfanumerico (abc123), oppure scrivere 'esci' per uscire:\n=> ");
            validazione = scanf("%s",
                                id_ricerca);
            validazione = controlla_id(id_ricerca);
            if((validazione != 1 || strlen(id_ricerca) != 6) && validazione != 2)
                printf("\nID inserito non valido");
            while (getchar() != '\n');
        } while ((validazione != 1 || strlen(id_ricerca) != 6) && validazione != 2);
        /* ricerca */
        if(validazione == 1)
        {
            ricercato = cerca_in_albero_bin_ric(radice,
                                                id_ricerca);
            if (ricercato == NULL)
                printf("\nID non presente.");
            else
                stampa_struttura("\nTelemetrie automobile:",
                                ricercato,
                                0,
                                1);
        }
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
