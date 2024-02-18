#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void funzione(int a[], int b[], int q, int k, int n, int min, int max,int *cont);

int main (void)
{
	int z,n,j,cont;
	int *b,*a;

	srand(time(0));

	printf("Inserire quanti valori testare: ");
	scanf("%d",&n); 

	for(z = 1; z <= n; z++){

		cont = 0;
	    a = (int *)calloc(z,sizeof(int));
	    b = (int *)calloc(z,sizeof(int));

	    for(j = 0; j <= z-1; j++)
   			a[j]=1+rand()%10000;

		funzione(a, b, 0, z-1, z, a[0], a[0],&cont);

 		printf("%d\n",cont);

		free(b);
		free(a);
	}	
	return(0);
}


void funzione(int a[], int b[], int q, int k, int n, int mx, int mn,  int *cont)
{
	
	int i, min,max,countmin = 0,countmax = 0;
	
	min = mn;
	max = mx;

	(*cont)=(*ccont)+3;

	for (i=0; (i<=n-1); i++)
	{
		(*cont)=(*cont)+3;
		if (a[i] != '\0')
		{
			(*cont)=(*cont)+2;
			if (a[i] <= min){
				(*cont)++;
				min = a[i];
			}

			if (a[i] >= max){
				(*cont)++;
				max = a[i];
			}


		}
	}

	b[q]= min;
	b[k]= max;

	(*cont)=(*cont)+4;
	
	for (i=0; (i<=n-1); i++)
	{
		(*cont)=(*cont)+4;
		if (a[i] == min && countmin == 0){ 
			a[i] = '\0';
			(*cont)=(*cont)+2;
			countmin = 1;
		}
		if (a[i] == max && countmax == 0){ 
			a[i] = '\0';
			(*cont)=(*cont)+2;
			countmax = 1;
		}

	}

	(*cont)++;

	if (q < k-1){
		(*cont)++;
		funzione(a, b, q+1, k-1, n, b[q], b[k],cont);
	}
}
			


	
		
