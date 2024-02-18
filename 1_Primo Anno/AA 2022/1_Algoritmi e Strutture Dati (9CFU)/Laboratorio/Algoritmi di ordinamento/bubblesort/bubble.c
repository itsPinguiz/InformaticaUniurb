#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void bubble(int a[],int,int *);

int main(void){

    int *a,n,i,count = 0;
    clock_t t1,t2;
    double cpu_time = 0;

    srand(time(NULL));

    printf("Quanti valori provare: ");
    scanf("%d",&n);

        a =(int*) calloc(n,sizeof(int));

        for(i=0;i<=n-1;i++)
            a[i]=1+rand()%1000;

        for(i=0;i<=n-1;i++)
            printf("%d ",a[i]);

        t1 = clock();
        bubble(a,n,&count);
        t2 = clock();

        cpu_time = ((double) (t2-t1))/CLOCKS_PER_SEC;

        printf("\n");

        for(i=0;i<=n-1;i++)
            printf("%d ",a[i]);

        printf("\nNum. istruzioni --> %d Tempo esecuzione --> %lf\n",count,cpu_time);

        free(a);

    return (0);
}
void bubble(int a[],int n,int *count){
    
    int tmp,i,j;

    (*count)++;

    for(i=1;i<n;i++){
        (*count)=(*count)+3;
        for(j=n-1;j>=i;j--){
            (*count)=(*count)+3;
            if(a[j]<a[j-1]){
                tmp = a[j-1];
                a[j-1] = a[j];
                a[j] = tmp;
                (*count)=(*count)+3;
            }
        }
        (*count)++;
    }
    (*count)++;
}
