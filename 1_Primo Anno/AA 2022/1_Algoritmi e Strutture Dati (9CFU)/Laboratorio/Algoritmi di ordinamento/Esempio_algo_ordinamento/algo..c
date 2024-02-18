#include <stdio.h>
#include <math.h>
#include <stdlib.h>

void funzione(int a[], int b[], int q, int k, int n, int min, int max);

int main (void)
{
	int z, tmp;
	int a[]={9,8,7,6,5,4,3,2,1};
	int n=9; /*elementi array*/

	int *b;/*array di appoggio*/
	b = (int *)calloc(n,sizeof(int));

	if (n >= 2)
	funzione(a, b, 0, n-1, n, a[0], a[0]);
	else if (n == 1)
	{
		if (a[0] >= a[1])
		{
		tmp = a[0];
		a[0] = a[1];
		a[1] = tmp;
		}
	}

	for (z=0; (z<=n-1); z++)
			printf("%d", b[z]);


	return(0);
}


void funzione(int a[], int b[], int q, int k, int n, int mx, int mn)
{
	
	int i, min,max;
	
	min = mn;
	max = mx;

	for (i=0; (i<=n-1); i++)
	{
		if (a[i] != '\0')
		{
			if (a[i] <= min)
			{
				min = a[i];
				b[q] = min;
			}

				
			if (a[i] >= max)
			{
				max = a[i];
				b[k] = max;
			}


		}
	}
	
	for (i=0; (i<=n-1); i++)
	{
		if (a[i] == min) 
			a[i] = '\0';
		if (a[i] == max)
			a[i] = '\0';

	}

	if (q < k-1)
	funzione(a, b, q+1, k-1, n, b[q], b[k]);
}
			


	
		
