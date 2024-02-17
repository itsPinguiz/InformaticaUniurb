#include <stdio.h>

int main()
{
    int i,
        j,
        k,
        n = 4, /*colonne A*/
        m = 4, /*colonne B*/
        o = 2; /*righe A*/
double  A[2][4] = {{4.3,2.1,3.3,5.2},
                   {4.8,9.3,2.3,0.5}}, 
        B[4][4] = {{2.3,1.1,4.3,6.2},
                   {5.3,5.5,3.5,7.3},
                   {4.9,2.3,4.3,2.1},
                   {2.3,0.5,1.8,1.1}},
        C[3][4];

    /* effettuare la moltiplicazione */
    for(i = 0; i < o; i++)
        for(j = 0; j < m; j++)
            for(k = 0; k < n; k++)
                C[i][j] += A[i][k] * B[k][j];
            
    /* stampare la matrice prodotto AB */
    printf("\nRisultato: \n\n");
    for(i = 0; i < o; i++)
    {
        for(j = 0; j < m; j++)
            printf("%4.2f ", C[i][j]);
        printf("\n");
    }  
}