#include <stdio.h>

unsigned int fatt_iter(unsigned int);
unsigned int fatt_rico(unsigned int);

int main(void){

    unsigned int n,i,scelta;

    printf("Algoritmo per il calcolo della serie fattoriale\n");
    printf("Inserire fino a quale iterazione si vuole calcolare(n>0): ");
    scanf("%d",&n);
    printf("Inserire in che modo si vuole calcolare(O = iterativo)(1 = ricorsivo): ");
    scanf("%d",&scelta);

    if(scelta == 0)
        for(i = 1;
            (i <= n);
            i++) 
                printf("fatt di %d --> %d\n",i,fatt_iter(i));
    else
        for(i = 1;
            (i <= n);
            i++) 
                printf("fatt di %d --> %d\n",i,fatt_rico(i));   

    return(0);
}
unsigned int fatt_iter(unsigned int n){

    unsigned int i,fatt =1 ;

    for(i = 1;
        (i <= n );
        i++)
    {
            fatt = fatt * i;  
    }
    return(fatt);
}
unsigned int fatt_rico(unsigned int n){

    unsigned int fatt;

    if(n == 1 || n == 0)
        fatt = 1;
    else 
        fatt = n * fatt_rico(n-1);

    return(fatt);
}