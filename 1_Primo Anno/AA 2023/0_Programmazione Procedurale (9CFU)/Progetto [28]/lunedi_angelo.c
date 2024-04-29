/**************************************************************/
/*    Progetto per la sessione d'esame invernale 2023/2024    */
/*         Insegnamento di Programmazione Procedurale         */
/*                                                            */
/* Programma per il calcolo della data del Lunedì dell'Angelo */
/*                                                            */
/*   Autori: Sestri Daniele           Matricola: 320713       */
/*           Piovaticci Luca                     328235       */
/**************************************************************/

/*****************************/
/* inclusione delle librerie */
/*****************************/

#include <stdio.h>

/*****************************************/
/* definizione delle costanti simboliche */
/*****************************************/

#define M_GIU  15 /* valore di M per il calendario giuliano */
#define N_GIU   6 /* valore di N per il calendario giuliano */
#define M_GREG 24 /* valore di M per il calendario gregoriano dal 1900 al 2099 */
#define N_GREG  5 /* valore di N per il calendario gregoriano dal 1900 al 2099 */

/********************************/
/* dichiarazione delle funzioni */
/********************************/

int  acquisisci_anno(void);
void calcolo_gauss_var(int,
		       int *,
		       int *);
void calcolo_pasqua(int,
		    int,
		    int,
		    int *,
		    int *);
void calcolo_angelo(int *,
		    int *);
void stampa_risultato(int,
		      int);
void stampa_riga_car(char,
		     int);

/******************************/
/* definizione delle funzioni */
/******************************/

/* definizione della funzione main */
int main(void)
{
  /* dichiarazione delle variabili locali alla funzione */
  int anno,     /* input: anno di riferimento per il calcolo */
      a,	/* lavoro: variabile a del metodo di Gauss */
      f,        /* lavoro: identificatore per 2b + 4c del metodo di Gauss */
      tipo_cal, /* lavoro: indicazione tipo di calendario */
      giorno,   /* output: giorno del Lunedì dell'Angelo */
      mese;     /* output: mese del Lunedì dell'Angelo */

  /* acquisire un anno di riferimento */
  anno = acquisisci_anno();

  /* calcolare i valori di a e dell'espressione 2b + 4c del metodo di Gauss */
  calcolo_gauss_var(anno,
		    &a,
	            &f);
   
  /* scorrimento e selezione dei calendari, calcolo e stampa dei risultati */
  for (tipo_cal = 0;
       tipo_cal < 2; 
       tipo_cal++)
  {
    /* calcolare la data di Pasqua per il calendario di riferimento */
    calcolo_pasqua(a,
		   f,
		   tipo_cal,
		   &giorno,
		   &mese);
     
    /* calcolare la data del Lunedì dell'Angelo */
    calcolo_angelo(&giorno,
		   &mese);
     
    /* stampare il risultato per il calendario di riferimento */
    printf("\n\n Il Lunedì dell'Angelo del %d," 
           " secondo il calendario %s, cade il \n\n", 
	   anno,
	   (tipo_cal) ? "giuliano" : "gregoriano");
    stampa_risultato(giorno,
		      mese);
  }
   
  printf("\n\n");
  return(0);
}

/* definizione della funzione per l'acquisizione dell'anno */
int acquisisci_anno(void)
{
  /* dichiarazione delle variabili locali alla funzione */
  int anno_scelto,	   /* output: anno scelto dall'utente */
      esito_lettura,	   /* lavoro: esito della scanf */
      acquisizione_errata; /* lavoro: esito complessivo dell'acquisizione */

  /* acquisizione dell'anno */
  do
  {
    printf("\n\n Inserire un anno compreso tra il 1900 e il 2099 \n"
	   "\n Digita la tua scelta e premi 'Invio': ");
    
    /* acquisizione e validazione stretta */
    esito_lettura = scanf("%d",
			  &anno_scelto);
    acquisizione_errata = esito_lettura != 1 ||
			  anno_scelto < 1900 ||
                          anno_scelto > 2099;
    if (acquisizione_errata)
      printf("\n Il valore inserito non è corretto! \n\n");
    while (getchar() != '\n');
  } 
  while (acquisizione_errata);
  return(anno_scelto);
}

/* definizione della funzione per il calcolo di a e di 2b + 4c del metodo di Gauss */
void calcolo_gauss_var(int  anno, /* input: anno di riferimento */
		       int *a,    /* output: variabile a del metodo di Gauss */
		       int *f)    /* output: identificatore per 2b + 4c */
{
  /* dichiarazione delle variabili locali alla funzione */
  int b, /* lavoro: variabile b del metodo di Gauss */
      c; /* lavoro: variabile c del metodo di Gauss */

  /* calcolo delle variabili a, b e c del metodo di Gauss */
  *a = anno % 19;
  b = anno % 4;
  c = anno % 7;

  /* calcolo dell'espressione 2b + 4c */
  *f = 2 * b + 
       4 * c;
}

/* definizione della funzione per il calcolo del giorno e del mese di Pasqua */
void calcolo_pasqua(int  a,	   /* input: variabile a del metodo di Gauss */
		    int  f,	   /* input: identificatore per 2b + 4c */ 
		    int  tipo_cal, /* input: tipo di calendario */
		    int *giorno,   /* output: giorno di Pasqua */
		    int *mese)	   /* output: mese di Pasqua */
{
  /* dichiarazione delle variabili locali alla funzione */
  int d, /* lavoro: variabile d del metodo di Gauss */
      e, /* lavoro: variabile e del metodo di Gauss */
      m, /* lavoro: parametro M del metodo di Gauss */
      n; /* lavoro: parametro N del metodo di Gauss */

  /* scelta dei parametri M e N in base al tipo di calendario */
  if(tipo_cal)
  {
    m = M_GIU;
    n = N_GIU;
  }
  else
  {
    m = M_GREG;
    n = N_GREG;
  }
  
  /* calcolare il giorno e il mese in base al metodo di Gauss */
  d = (19 * a +
       m) % 30;
  e = (f + 
       6 * d +
       n) % 7;
  *giorno = d + e;
  if (*giorno < 10)
  {
    *giorno += 22;
    *mese = 3;
  }
  else
  {
    *giorno -= 9;
    *mese = 4;
  }

  /* gestione delle eventuali eccezioni */
  if (*mese == 4)
  {
    if (*giorno == 26)
      *giorno = 19;
    if (*giorno == 25 && 
        d == 28       &&
	e == 6 	      && 
        a > 10)
      *giorno = 18;
  }
}

/* definizione della funzione per il calcolo del Lunedì dell'Angelo */
void calcolo_angelo(int *giorno, /* input/output: giorno */
		    int *mese)	 /* input/output: mese */
{
  ++*giorno;
  if (*mese == 3 && 
      *giorno > 31)
  {
    *giorno -= 31;
    ++*mese; 
  }
}

/* definizione della funzione per la stampa del risultato */
void stampa_risultato(int giorno, /* input: giorno del Lunedì dell'Angelo */
		      int mese)   /* input: mese del Lunedì dell'Angelo */
{
  /* dichiarazione delle variabili locali alla funzione */
  char contenitore_ris[6]; /* output: contenitore dei dati per la stampa */
  int  prima_cifra,	   /* lavoro: prima cifra da sinistra del giorno */
       seconda_cifra,	   /* lavoro: seconda cifra da sinistra del giorno */
       v,	           /* lavoro: contatore per asse verticale */
       o;		   /* lavoro: contatore per avanzamento orizzontale */

  /* ricavare la prima e la seconda cifra del giorno */
  prima_cifra = giorno / 10;
  seconda_cifra = giorno % 10;

  /* caricare il contenitore dei dati per la stampa */ 
  contenitore_ris[0] = '0' + prima_cifra;
  contenitore_ris[1] = '0' + seconda_cifra;
  contenitore_ris[2] = ' ';
  if (mese == 3)
  {
    contenitore_ris[3] = 'M';
    contenitore_ris[4] = 'A';
  }
  else
  {
    contenitore_ris[3] = 'A';
    contenitore_ris[4] = 'P';
  }
  contenitore_ris[5] = 'R';

  /* emissione dei dati utilizzando la stampa riga per riga */
  for (v = 0;
       v < 5;
       v++)
  {
    for (o = 0;
	 o < 6;
	 o++)
      stampa_riga_car(contenitore_ris[o], v);
    printf("\n");
  }
}

/* definizione della funzione per la stampa del carattere */
void stampa_riga_car(char carattere, /* input: carattere da stampare */ 
		     int  v)	     /* input: indicazione posizione asse verticale */
{
  /* stampare la v-esima riga del carattere */
  switch(carattere)
  {
    case ' ':
      printf("%5s", "");
      break;
    case '0':
      printf(v == 0 || v == 4 ? " ***** " : " *   * ");
      break;
    case '1':
      if (v == 1)
        printf("  **   ");
      else if (v == 2)
        printf(" * *   ");
      else if (v == 4)
        printf(" ***** ");
      else
        printf("   *   ");
      break;
    case '2':
      if (v == 1)
        printf("     * ");
      else if (v == 3)
        printf(" *     ");
      else
	printf(" ***** ");
      break;
    case '3':
      printf(v == 1 || v == 3 ? "     * " : " ***** "); 
      break;
    case '4':
      if (v == 0 || 
          v == 1) 
        printf(" *   * ");
      else if (v == 2)
        printf(" ***** ");
      else
        printf("     * ");
      break;
    case '5':
    case '6':
      if (v == 1)
        printf(" *     ");
      else if (v == 3)
        printf(carattere == '5' ? "     * " : " *   * ");
      else
        printf(" ***** ");
      break;
    case '7':
      printf(v == 0 ? " ***** " : "     * ");
      break;
    case '8':
      printf(v == 0 || v == 2 || v == 4 ? " ***** " : " *   * ");
      break;
    case '9':
      if (v == 1)
        printf(" *   * ");
      else if (v == 3)
        printf("     * ");
      else
        printf(" ***** ");
      break;
    case 'A':
      if (v == 0)
        printf("   *   ");
      else if (v == 1)
        printf("  * *  ");
      else if (v == 2)
        printf(" ***** ");
      else
        printf(" *   * ");
      break;
    case 'M':
      if (v == 1)
        printf(" ** ** ");
      else if (v == 2)
        printf(" * * * ");
      else
        printf(" *   * ");
      break;
    case 'P':
      if (v == 0 ||
          v == 2)
        printf(" ***** ");
      else if (v == 1)
        printf(" *   * ");
      else
        printf(" *     ");
      break;
    case 'R':
      if (v == 1)
        printf(" *   * ");
      else if (v == 3)
        printf(" *  *  ");
      else if (v == 4)
        printf(" *   * ");
      else
        printf(" ***** ");
      break;
  }
}
