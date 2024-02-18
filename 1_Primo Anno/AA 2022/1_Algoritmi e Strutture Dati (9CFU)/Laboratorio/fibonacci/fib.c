#include <stdio.h>

void fib_iter(unsigned int);
unsigned int fib_rico(unsigned int);

int main(void){

    unsigned int n,i,scelta;

    printf("Algoritmo per il calcolo della serie di fibonacci\n");
    printf("Inserire fino a quale iterazione si vuole calcolare(n>0): ");
    scanf("%d",&n);
    printf("Inserire in che modo si vuole calcolare(O = iterativo)(1 = ricorsivo): ");
    scanf("%d",&scelta);

    if(scelta == 0)
        fib_iter(n);
    else
        for(i = 1;
        (i < n);
        i++) 
            printf("%d' num serie di fib. --> %d\n",i+1,fib_rico(i));   

    return(0);
}
void fib_iter(unsigned int n){

    unsigned int ultimo,penultimo,i,fib;

    for(ultimo = 1, penultimo = 0, i = 2;
        (i <= n);
        i++)
    {
            fib = ultimo + penultimo;
            penultimo = ultimo;
            ultimo = fib; 
            printf("%d' num serie di fib. --> %d\n",i,fib);  
    }
}
unsigned int fib_rico(unsigned int n){

    unsigned int fib;

    if(n == 1 || n == 0)
        fib = 1;
    else 
        fib = fib_rico(n-2) + fib_rico(n-1);

    return(fib);
}