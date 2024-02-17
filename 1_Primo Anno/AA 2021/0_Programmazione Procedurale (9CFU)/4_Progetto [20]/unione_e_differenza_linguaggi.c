/*******************************************************/
/* programma per differenza e unione tra due linguaggi */
/*******************************************************/

/*****************************/
/* inclusione delle librerie */
/*****************************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/*****************************************/
/* definizione delle costanti simboliche */
/*****************************************/

#define MAX_SEQ 100 /* lunghezza massima singola sequenza */
#define MAX_LING 50 /* numero massimo sequenze per linguaggio */

/********************************/
/* dichiarazione delle funzioni */
/********************************/

int input_n_sequenze(int);

char **alloca_memoria(int,
                      int*,
                      int);

int controllo_sequenza(char **,
                       int);

char **input_sequenze(int *,
                      int);

char **acquisici_linguaggio(int *,
                            int *,
                            int *,
                            int);

void differenza_linguaggi(char **,
                          char **,
                          char **,
                          int *);

char *unione_linguaggi(char **,
                       char **,
                       int,
                       int);

void stampa_linguaggi(char **,
                      char **,
                      char **,
                      char **,
                      int *);

/******************************/
/* definizione delle funzioni */
/******************************/

/* definizione della funzione main */
int main(void)
{
    /* dichiarazione delle variabili locali alla funzione */
    char **linguaggio1, /* input: sequenze del primo linguaggio */
         **linguaggio2, /* input: sequenze del secondo linguaggio */
         **differenza, /* output: sequenze ottenute dalla differenza */
         **unione; /* output: sequenze ottenute dall'unione  */

    int n_sequenze[3] = {0, /* input: numero di sequenze per linguaggio */
                         0,
                         0}, 
        i, /* lavoro: variabile per cilco for */
        errore_mem = 0, /* lavoro: controllo errori di allocazione memoria*/
        lunghezza_max[3]; /* lavoro: lunghezze delle sequenze più lunghe */
    
    /* stampa istruzioni per utente*/
    printf("\nProgramma per unione e differenza tra linguaggi.\n");
    printf("\nLe sequenze possono solo contenere caratteri dell'alfabeto {a,e,i,o,u},");
    printf("\nad eccezione della lettera 'E' che rappresenta la sequenza vuota.");
    printf("\nAll'interno di un linguaggio non va ripetuta una stessa sequenza.\n");

    /**************************/
    /* Acquisizione Linguaggi */
    /**************************/

    linguaggio1 = acquisici_linguaggio(lunghezza_max,
                                       n_sequenze,
                                       &errore_mem,
                                       0);

    linguaggio2 = acquisici_linguaggio(lunghezza_max,
                                       n_sequenze,
                                       &errore_mem,
                                       1);

    /* trova il massimo tra le due lunghezze */
    if (lunghezza_max[0] >= lunghezza_max[1])
        lunghezza_max[2] = lunghezza_max[0];
    else
        lunghezza_max[2] = lunghezza_max[1];
    
    if (errore_mem != 1)
    {   
        /**************/
        /* Differenza */
        /**************/  
    
        /* alloca memoria */
        differenza = alloca_memoria(n_sequenze[0] + n_sequenze[1], 
                                    &errore_mem,
                                    lunghezza_max[2]);
        if (errore_mem != 1 &&
            (n_sequenze[0] != 0 ||
            n_sequenze[1] != 0))
        {
            /* calcolo differenza */ 
            differenza_linguaggi(differenza,
                                linguaggio1,
                                linguaggio2,
                                n_sequenze);      
        }
    
        /**********/
        /* Unione */
        /**********/

        /* alloca memoria */
        unione = alloca_memoria(n_sequenze[0] + n_sequenze[1],
                                &errore_mem,
                                lunghezza_max[2]);                
        
        if (errore_mem != 1 &&
            (n_sequenze[0] != 0 ||
            n_sequenze[1] != 0))
        {   
            /* copia valori */
            for (i = 0;
                 i < n_sequenze[2];
                 i++)
                strcpy(unione[i],
                       differenza[i]);
            
            /* se il secondo linguaggio e' vuoto non svolge i calcoli */
            if (n_sequenze[1] == 0)
            {
                for (i = 0;
                     i < n_sequenze[0];
                     i++)
                    strcpy(unione[i],
                           linguaggio1[i]);    
            }
            /* se il primo linguaggio e' vuoto non svolge i calcoli */
            if (n_sequenze[0] == 0)
            {
                for (i = 0;
                     i < n_sequenze[1];
                     i++)
                    strcpy(unione[i],
                           linguaggio2[i]);    
            }
            /* se i linguaggi non sono vuoti svolge i calcoli */
            if (n_sequenze[0] != 0 &&
                n_sequenze[1] != 0)
            {
                /* calcolo unione */
                unione_linguaggi(unione,
                                 linguaggio2,
                                 n_sequenze[2],
                                 n_sequenze[1]);
            }
        }   

        /********/
        /*Stampa*/
        /********/

        stampa_linguaggi(linguaggio1,
                         linguaggio2,
                         differenza,
                         unione,
                         n_sequenze);
    }
    return 0;
}

/* definizione della funzione per l'acquisizione del numero di sequenze da inserire */
int input_n_sequenze(int id_linguaggio) /* lavoro: identificatore per stampa */
{
    /* dichiarazione delle variabili locali alla funzione */
    int esito_lettura, /* lavoro: variabile di controllo per scanf */
        n_sequenze; /* output: numero di sequenze */
    
    /* acquisizione numero sequenze con validazione stretta */
    printf("\n%s linguaggio: \n",
           (id_linguaggio == 0)?"Primo":"Secondo");

    do 
    {        
        printf("Digitare il numero di sequenze da inserire (>= 0 e <100): ");
        esito_lettura = scanf("%d", 
                              &n_sequenze);

        if (esito_lettura != 1 || 
            n_sequenze < 0 ||
            n_sequenze > MAX_LING) 
            printf("Valore non accettabile.\n");
        while ( getchar() != '\n');          
    } 
    while (esito_lettura != 1 ||
           n_sequenze < 0 ||
           n_sequenze > MAX_LING); 

    return n_sequenze;
}

/* definizione della funzione per l'allocazione dinamica di memoria */
char **alloca_memoria(int n_sequenze, /* lavoro: numero di sequenze a cui allocare memoria */                      
                      int *errore_mem, /* output: variabile di controllo errori allocazione*/
                      int max_seq) /* lavoro: lunghezza massima sequenza */
{
    /* dichiarazione delle variabili locali alla funzione */
	char **linguaggio; /* output: linguaggio a cui allocare memoria */

    int i; /* lavoro: variabile per ciclo for*/
    
    /* allocazione puntatore a puntatore a char */
    if (n_sequenze == 0)
        linguaggio = NULL;
    else
    {
        linguaggio = calloc(n_sequenze,
                            sizeof(char*)); 
    
        /* allocazione scelta con controllo */
        if (linguaggio != NULL)   
        {
            for (i = 0;
                 i < n_sequenze;
                 i++)
            {            
                linguaggio[i] = calloc(max_seq,
                                       sizeof(char));
                if (linguaggio[i] != NULL)
                    *errore_mem = 0;
                else 
                    *errore_mem = 1;
            }
        }
        else 
            *errore_mem = 1;
    }
    
    /* stampa errore se l'allocazione e' fallita */
    if (*errore_mem == 1)
        printf("\nImpossibile allocare memoria necessaria.");

    return linguaggio; 
} 

/* definizione funzione per controllo sequenze inserite */
int controllo_sequenza(char **linguaggio, /* lavoro: linguaggio da controllare */
                       int id_sequenza) /* lavoro: numero sequenza da controllare */
{
    /* dichiarazione delle variabili locali alla funzione */
    int i, /* lavoro: variabile di controllo scanf */
        lung_sequenza = strlen(linguaggio[id_sequenza]), /* lavoro: lunghezza sequenza*/
        vuoto = 0, /* lavoro: presenza carattere sequenza vuota */
        esito_ctrl = 1; /* output: esito del controllo */
    
    const char alfabeto[6] = {'a', /* lavoro: alfabeto da utilizzare */
                              'e',
                              'i',
                              'o',
                              'u',
                              'E'}; 

    /* controllo rispetto lunghezza massima */
    if (lung_sequenza > (MAX_SEQ - 1))
    {
        printf("\nValore non accettabile:");
        printf("\nla sequenza supera il numero di caratteri consentito.");
        esito_ctrl = 0;
    }
    else
    {
        /* controlli */
        for ( i = 0;
              i < lung_sequenza;
              i++ )
        { 
            /* controllo presenza lettere alfabeto o carattere sequenza vuota*/
            if ( linguaggio[id_sequenza][i] != alfabeto[0] &&
                 linguaggio[id_sequenza][i] != alfabeto[1] &&
                 linguaggio[id_sequenza][i] != alfabeto[2] &&
                 linguaggio[id_sequenza][i] != alfabeto[3] &&
                 linguaggio[id_sequenza][i] != alfabeto[4] &&
                 linguaggio[id_sequenza][i] != alfabeto[5])
            {
                printf("\nValore non accettabile:");
                printf("\nla sequenza non contiene solo caratteri dell'alfabeto.");
                esito_ctrl = 0;
            }
            else
            {
                /* controllo numero di 'E' nella sequenza */
                if (linguaggio[id_sequenza][i] == alfabeto[5])
                    vuoto++;
            }             
        }
        /* controllo se 'E' sia l'unico carattere della sequenza */
        if (esito_ctrl != 0 &&
            vuoto != 0 &&
            vuoto != lung_sequenza)
        {
            printf("\nValore non accettabile:");
            printf("\nla sequenza vuota puo' contenere unicamente la lettera 'E'");
            esito_ctrl = 0;
        }
    }

    /* se una sequenza contiene piu' 'E' viene ridotta ad 'E' */
    if (esito_ctrl != 0 &&
        linguaggio[id_sequenza][0] == 'E')
        strcpy(linguaggio[id_sequenza],"E");

    /* controllo unicita' sequenza */ 
    if ( esito_ctrl == 1)
    {
        for (i = 0;
             i < id_sequenza;
             i++ )
        {           
            if ( strcmp(linguaggio[id_sequenza],
                        linguaggio[i]) == 0 ) 
            {
                printf("\nValore non accettabile:");
                printf("\nla sequenza e' gia' stata inserita.");
                esito_ctrl = 0;                
            }        
        } 
    }      

    return esito_ctrl;  
}

/* definizione funzione per l'acquisizione delle sequenze */
char **input_sequenze(int *lunghezza_max, /* lavoro: lunghezza della sequenze piu' lunga */
                      int n_sequenze) /* lavoro: numero di sequenze da richiedere */
{
    /* dichiarazione delle variabili locali alla funzione */
    int i, /* lavoro: variabile per ciclo for */
        esito_lettura, /* lavoro: variaible controllo scanf */
        esito_ctrl; /* lavoro: variabile esito controllo su sequenza */

    char **linguaggio = calloc(n_sequenze, /* output: insieme di sequenze da inserire*/
                               sizeof(char *));;

    if (linguaggio != NULL)
    {
        /* acquisici le sequenze */
        for (i = 0; 
             i < n_sequenze;
             i++)
        {
            /* validazione stretta */
            do
            {
                printf("\nInserire sequenza numero %d (con lunghezza <100 e >0): ",
                       i+1);
                esito_lettura = scanf("%ms",
                                      &linguaggio[i]);

                esito_ctrl = controllo_sequenza(linguaggio,
                                                i);
            
                if ( esito_lettura != 1 )
                    printf("\nL'acquisizione non e' avvenuta con successo.");
                while (getchar() != '\n');
            } 
            while (esito_lettura != 1 ||
                esito_ctrl != 1 );

            /* contollo sequenza di lunghezza maggiore */
            if (*lunghezza_max < strlen(linguaggio[i]))
                *lunghezza_max = strlen(linguaggio[i]);
        } 
    }
    return linguaggio;   
}

/* definizione funzione per acquisire un linguaggio */
char **acquisici_linguaggio(int *lunghezza_max,
                            int *n_sequenze,
                            int *errore_mem,
                            int id_linguaggio)
{
    /* dichiarazione delle variabili locali alla funzione */
    char **linguaggio = NULL; /* output: linguaggio da acquisire */

    /* acquisizione numero sequenze */
    n_sequenze[id_linguaggio] = input_n_sequenze(id_linguaggio);
    
    if (*errore_mem != 1)
    {
        linguaggio = input_sequenze(lunghezza_max,
                                    n_sequenze[id_linguaggio]);
    }
    return linguaggio;
}


/* definizione funzione per calcolare la differenza tra il primo e il secondo linguaggio */
void differenza_linguaggi(char **differenza, /* output: insieme da calcolare */
                          char **linguaggio1, /* lavoro: primo linguaggio */
                          char **linguaggio2, /* lavoro: secondo linguaggio */
                          int *n_sequenze) /* lavoro: numero sequenze per linguaggio */
{
    /* dichiarazione delle variabili locali alla funzione */
    int i, /* lavoro: variabile per ciclo for */
        j, /* lavoro: variabile per ciclo for */
        disuguaglianza = 0; /* lavoro: contatore disuguaglianza tra linguaggi */

    /* calcolo differenza */
    for (i = 0;
         i < n_sequenze[0];
         i++)
    {
        for (j = 0;
             j < n_sequenze[1];
             j++)
        {
            if (strcmp(linguaggio1[i],
                       linguaggio2[j]) != 0 )
                disuguaglianza ++;                
        }
        if (disuguaglianza == n_sequenze[1])
        {
            differenza[n_sequenze[2]] = linguaggio1[i];
            n_sequenze[2]++;
        }
        disuguaglianza = 0;
    }    
}

/* dichiarazione funzione per unione dei due linguaggi */
char *unione_linguaggi(char **linguaggio1, /* lavoro: primo linguaggio da unire */
                       char **linguaggio2, /* lavoro: secondo linguaggio da unire */
                       int n, /* lavoro: numero sequenze primo linguaggio */
                       int m) /* lavoro: numero sequenze secondo linguaggio */
{ 
    /* caso induttivo */
    if ( m != 0)
    {
        strcpy(linguaggio1[n],
               unione_linguaggi(linguaggio1,
                                linguaggio2,
                                n + 1,
                                m - 1));
    } 
    return linguaggio2[m];  
}

/* dichiarazione funzione per stampa linguaggi */
void stampa_linguaggi(char **linguaggio1, /* lavoro: primo linguaggio */
                      char **linguaggio2, /* lavoro: secondo linguaggio */
                      char **differenza, /* lavoro: linguaggio da stampare */
                      char **unione, /* lavoro: linguaggio da stampare */
                      int *n_sequenze) /* lavoro: numero di sequenze dei linguaggi */
{
    /* dichiarazione delle variabili locali alla funzione */
    int i, /* lavoro: variabile per ciclo for */
        j, /* lavoro: variabile per ciclo for */
        sequenze[2], /* lavoro: numero di sequenze da stampare */
        id_stampa; /* lavoro: id linguaggio da stampare */

    /* se entrambi i linguaggi sono vuoti */
    if (n_sequenze[0] == 0 &&
        n_sequenze[1] == 0)
         printf("\nGli insiemi ottenuti dalla differenza e unione sono vuoti\n");
    else
    {
        /* se il primo linguaggio e' vuoto */
        if (n_sequenze[0] == 0)
        {
            printf("\nL'insieme ottenuto dalla differenza e' vuoto");

            sequenze[0] = n_sequenze[1] + n_sequenze[2];  
            id_stampa = 1;        
        }

        /* se il secondo linguaggio e' vuoto */
        if ( n_sequenze[1] == 0)
        {
            sequenze[0] = n_sequenze[0];
            sequenze[1] = n_sequenze[0];
            id_stampa = 2;
        }

        /* se nessun linguaggio e' vuoto */
        if (n_sequenze[0] != 0 &&
            n_sequenze[1] != 0 )
        {
            sequenze[0] = n_sequenze[2] + n_sequenze[1];
            sequenze[1] = n_sequenze[2];
            id_stampa = 2;
        }

        for (i = 0;
             i < id_stampa;
             i++)
        {
            /* stampa insiemi  */
            printf("\nInsieme ottenuto %s = {",
                   (i == 1)?"dalla differenza":"dall'unione");

            for (j = 0;
                 j < sequenze[i];
                 j++)
            {           
                printf("%s",
                       (i == 1)?differenza[j]:unione[j]);  

                if (j != (sequenze[i] - 1))
                    putchar(',');  
            }
            printf("}");
        }
        printf("\n");    
    }
}
