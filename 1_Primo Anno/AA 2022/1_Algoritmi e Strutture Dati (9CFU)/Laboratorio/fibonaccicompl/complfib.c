#include <stdio.h>

int fib_iter(int,int*);
int fib_rico(int,int*);

int main(void){

    int n,i,scelta1,scelta2,cont_ite = 0,cont_rico = 0,fatt;

    printf("Algoritmo per il calcolo della serie di fibonacci\n");
    printf("Inserire fino a quale iterazione si vuole calcolare(n>0): ");
    scanf("%d",&n);
    printf("Inserire in che modo si vuole calcolare(O = iterativo)(1 = ricorsivo): ");
    scanf("%d",&scelta1);
    printf("Inserire in che modo si vuole stampare i risultati (0 = completi)(1 = complessita'): ");
    scanf("%d",&scelta2);

    if(scelta1 == 0)
        for(i = 1;
            (i <= n);
            i++){
                if(scelta2 == 1){
                    fatt = fib_iter(i,&cont_ite);
                    printf("%d\n",cont_ite);
                    cont_ite = 0;
                }
                else{
                printf("%d' num fib. --> %d numero istruzioni = %d\n",i,fib_iter(i,&cont_ite),cont_ite);
                cont_ite = 0;
                }
            }
    else
        for(i = 1;
            (i <= n);
            i++){
                if(scelta2 == 1){
                    fatt = fib_rico(i,&cont_rico);
                    printf("%d\n",cont_rico);
                    cont_rico = 0;
                }
                else{
                printf("%d' num fib --> %d numero istruzioni = %d\n",i,fib_rico(i,&cont_rico),cont_rico);
                cont_rico = 0;
                }
            }
    return(0);
}
int fib_iter(int n,int *cont_ite){

    int ultimo,penultimo,i,fib=1;

    (*cont_ite)++;

    for(ultimo = 1, penultimo = 0, i = 2;
        (i <= n);
        i++)
    {
            fib = ultimo + penultimo;
            penultimo = ultimo;
            ultimo = fib; 
            (*cont_ite)+=5; 
    }
    (*cont_ite)++;
    return(fib);
}
int fib_rico(int n,int *cont_rico){

    int fib;

    (*cont_rico)++;
    if(n == 0){
        fib = 0;
        (*cont_rico)++;
    }
    else if(n == 1){
        fib = 1;
        (*cont_rico)++;
    }
    else{
        fib = fib_rico(n-2,cont_rico) + fib_rico(n-1,cont_rico);
        (*cont_rico)++;
    }
    return(fib);
}