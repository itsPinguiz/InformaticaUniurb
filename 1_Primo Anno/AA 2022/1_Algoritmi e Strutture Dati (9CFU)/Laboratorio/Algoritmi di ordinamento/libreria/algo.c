#include <stdlib.h>
#include "algo.h"

void bubble(int a[],int,long long int *);
void insert(int a[],int,long long int *);
void quick(int a[],int,int,long long int *);
void heap(int a[],int,long long int *);
void setacc(int a[],int,int,long long int *);
void selec(int a[],int,long long int *);
void merge(int a[],int,int,int long long *);
void fondi(int a[],int,int,int,int long long *);


void bubble(int a[],int n,long long int *count){
    
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
void insert(int a[], int n,long long int *count){
    
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
void quick(int a[],int sx,int dx,long long int *count){

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
    quick(a,sx,j-1,count);
    (*count)++;
    quick(a,j+1,dx,count);

    }
}
void heap(int a[],int n,long long int *count){

    int tmp,dx,sx;

    (*count)++;

    for(sx = n/2;(sx >= 1);sx--){
        (*count)=(*count)+3;
        setacc(a,sx,n,count);
    }
    (*count)=(*count)+2;
    for(dx = n;(dx > 1);dx--){
        (*count)=(*count)+6;
        tmp = a[1];
        a[1] = a[dx];
        a[dx] = tmp;
        setacc(a,1,dx-1,count);
    }
    (*count)++;
}
void setacc(int a[],int sx,int dx,long long int *count){

    int nuovo_val,i,j;

    (*count)=(*count)+3;

    for(nuovo_val = a[sx],i = sx,j = 2*i;(j <= dx);){
        (*count)=(*count)+3;
        if((j < dx) && (a[j+1]>a[j])){
            (*count)++;
            j++;
        }
        if(nuovo_val < a[j]){
            (*count)=(*count)+3;
            a[i] = a[j];
            i = j;
            j = 2*i;
        }
        else{
            j = dx + 1;
            (*count)++;
        }
    }
    (*count)=(*count)+2;
    if(i != sx){
        a[i] = nuovo_val;
        (*count)++;
        }
}
void selec(int a[],int n,int long long *count)
{
    int valore_min,
        indice_valore_min,
        i,
        j;

    (*count)=(*count)+1;
    for (i = 0;
        (i < n - 1);
        i++)
    {
        (*count)=(*count)+8;
        for (valore_min = a[i], indice_valore_min = i, j = i + 1;
            (j < n);
            j++)
        {
            (*count)=(*count)+3;
            if (a[j] < valore_min)
            {
                (*count)=(*count)+2;
                valore_min = a[j];
                indice_valore_min = j;
            }
        }
    if (indice_valore_min != i)
    {
        (*count)=(*count)+2;
        a[indice_valore_min] = a[i];
        a[i] = valore_min;
    } 
    }
    (*count)=(*count)+1;
}
void merge(int a[], int sx, int dx,int long long *count)
{
    int mx;

    (*count)=(*count)+1;

    if (sx < dx)
    {
        (*count)=(*count)+2;
        mx = (sx + dx) / 2;
        merge(a,sx,mx,count);
        (*count)=(*count)+1;
        merge(a,mx + 1,dx,count);
        (*count)=(*count)+1;
        fondi(a,sx,mx,dx,count);
    }
}
void fondi(int a[], int sx, int mx, int dx,int long long *count)
{
    int *b, /* array di appoggio */
        i,  /* indice per la parte sinistra di a (da sx ad m) */
        j,  /* indice per la parte destra di a (da m + 1 a dx) */
        k;  /* indice per la porzione di b da sx a dx */

    /* fondi ordinatamente le due parti finche' sono entrambe non vuote */

    (*count)=(*count)+4;

    b = (int *)calloc(dx + 1,sizeof(int));
    for (i = sx, j = mx + 1, k = 0;((i <= mx) && (j <= dx));k++)
    {
        (*count)=(*count)+3;
        if (a[i] <= a[j])
        {
            (*count)=(*count)+2;
            b[k] = a[i];
            i++;
        }
        else
        {
            (*count)=(*count)+2;
            b[k] = a[j];
            j++;
        }
    }
    (*count)=(*count)+1;
    while (i<=mx) 
    {
        (*count)=(*count)+3;
        b[k] = a[i];
        i++;
        k++;
    }
    (*count)=(*count)+2;
    while (j<=dx) 
    {
        (*count)=(*count)+3;
        b[k] = a[j];
        j++;
        k++;
    }
    (*count)=(*count)+2;
    for (k=sx; k<=dx; k++)
    {
        (*count)=(*count)+3;
        a[k] = b[k-sx];
    }
    (*count)=(*count)+1;
    free(b);
} 