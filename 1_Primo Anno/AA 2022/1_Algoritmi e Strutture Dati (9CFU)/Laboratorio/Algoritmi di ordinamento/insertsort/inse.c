#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void insert(int *,int ,long long int *);

int main(void){

    int *a,n,i;
    long long int count = 0;
    clock_t t1,t2;
    double cpu_time = 0;

    srand(time(NULL));

    printf("Quanti valori provare: ");
    scanf("%d",&n);
        
        a =(int*) calloc(n,sizeof(int));

        for(i=0;i<=n-1;i++)
            a[i]=1+rand()%10000;

        for(i=0;i<=n-1;i++)
            printf("%d ",a[i]);

        t1 = clock();
        insert(a,n,&count);
        t2 = clock();

        cpu_time = ((double) (t2-t1))/CLOCKS_PER_SEC;

        printf("\n");

        for(i=0;i<=n-1;i++)
            printf("%d ",a[i]);

        printf("\nNum. istruzioni --> %lld Tempo esecuzione --> %lf\n",count,cpu_time);

        free(a);

    return (0);
}
void insert(int a[], int n,long long int *count)
{
    int i, valore_conf, j;

    (*count)++;

    for (i = 1; (i < n); i++) {
        (*count)=(*count)+6;
        valore_conf = a[i];
        j = i - 1;

        while (j >= 0 && a[j] > valore_conf) {
            a[j + 1] = a[j];
            j = j - 1;
            (*count)=(*count)+2;
        }
        a[j + 1] = valore_conf;
    }
}