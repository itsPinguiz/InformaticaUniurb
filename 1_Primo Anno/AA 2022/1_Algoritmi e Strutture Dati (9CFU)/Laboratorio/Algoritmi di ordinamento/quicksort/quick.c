#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void quicksort(int a[],int,int,int *);

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
        quicksort(a,0,n-1,&count);
        t2 = clock();

        cpu_time = ((double) (t2-t1))/CLOCKS_PER_SEC;

        printf("\n");

        for(i=0;i<=n-1;i++)
            printf("%d ",a[i]);

        printf("\nNum. istruzioni --> %d Tempo esecuzione --> %lf\n",count,cpu_time);

        free(a);

    return (0);
}
void quicksort(int a[],int sx,int dx,int *count){

int i, j, pivot, temp;

    (*count)++;

    if(sx<dx){

        pivot=sx;
        i=sx;
        j=dx;

        while(i<j){
            (*count)=(*count)+3;

            while(a[i]<=a[pivot]&&i<dx){
                
                i++;
                (*count)++;
            }

            while(a[j]>a[pivot]){

                j--;
                (*count)++;
            }

            if(i<j){

                temp=a[i];
                a[i]=a[j];
                a[j]=temp;
                (*count)=(*count)+3;
            }
        }
    temp=a[pivot];
    a[pivot]=a[j];
    a[j]=temp;
    (*count)=(*count)+8;
    quicksort(a,sx,j-1,count);
    (*count)++;
    quicksort(a,j+1,dx,count);

    }
}
