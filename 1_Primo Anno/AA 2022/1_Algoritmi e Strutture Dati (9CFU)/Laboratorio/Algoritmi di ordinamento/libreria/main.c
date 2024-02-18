#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "algo.h"  /*libreria utente algoritmi di ordinamento */

int main(void){

    int *a,n,n1,i;
    long long int count;
    clock_t t1,t2;
    double cpu_time;

    srand(time(NULL));

    printf("Quanti valori provare: ");
    scanf("%d",&n1);

        for(n=1;n<=n1+1;n=n+5000)
        {
        
            a = (int*) calloc(n,sizeof(int));
            count = 0;
            cpu_time = 0;
            
            for(i=1;
                (i<=n-1);
                i++)
                {
                a[i]=1+rand()%10000;
                }

            t1 = clock();
            merge(a,0,n-1,&count);
            t2 = clock();

            cpu_time = ((double) (t2-t1))/CLOCKS_PER_SEC;

            printf("%lf\n",cpu_time);

            free(a);
        }
    return (0);
}
