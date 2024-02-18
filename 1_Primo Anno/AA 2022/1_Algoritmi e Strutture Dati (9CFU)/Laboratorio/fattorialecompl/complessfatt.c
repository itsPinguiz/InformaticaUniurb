#include <stdio.h>
#include <time.h>

int fatt_iter(int, int *);
int fatt_rico(int, int *);

int main(void){

    int n,scelta1,i,scelta2,cont_ite = 0,cont_rico = 0,fatt;
    clock_t t1,t2;
    double cpu_time1,cpu_time2;

    printf("Algoritmo per il calcolo della serie fattoriale\n");
    printf("Inserire fino a quante volte  si vuole calcolare il fattoriale (n>0): ");
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
                    t1 = clock();
                    fatt = fatt_iter(i,&cont_ite);
                    t2 = clock();
                    cpu_time1 = (double) ((t2 - t1) / CLOCKS_PER_SEC);
                    printf("%d tempo-->%f\n",cont_ite,cpu_time1);
                    cont_ite = 0;
                }
                else{
                printf("%d' num fib. --> %d numero istruzioni = %d\n",i,fatt_iter(i,&cont_ite),cont_ite);
                cont_ite = 0;
                }
            }
    else 
        for(i = 1;
            (i <= n);
            i++){
                if(scelta2 == 1){
                    t1 = clock();
                    fatt = fatt_rico(i,&cont_rico);
                    t2 = clock();
                    cpu_time2 = (double) ((t2 - t1) / CLOCKS_PER_SEC);
                    printf("%d tempo-->%f\n",cont_rico,cpu_time2);
                    cont_rico = 0;
                }
                else{
                printf("%d' num fib --> %d numero istruzioni = %d\n",i,fatt_rico(i,&cont_rico),cont_rico);
                cont_rico = 0;
                }
            }   
    return(0);
}
int fatt_iter(int n,int  *cont_ite){

    int i,fatt =1 ;

    (*cont_ite)++;

    for(i = 1;
        (i <= n );
        i++)
    {
        fatt = fatt * i;
        (*cont_ite)+=3;
    }

    (*cont_ite)++;
    return(fatt);
}
 int fatt_rico( int n,int *cont_rico){

    int fatt;

    (*cont_rico)++;
    if(n == 1 || n == 0){
        fatt = 1;
        (*cont_rico)++;
    }
    else{ 
        fatt = n * fatt_rico(n-1,cont_rico);
        (*cont_rico)++;
    }
    return(fatt);
}