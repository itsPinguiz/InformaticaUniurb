#include <stdio.h>

int main() {
    float grid[16] = {
        1.3, 0.7, 0.5, 0.2,
        0.6, 0.9, 0.3, 0.0,
        0.8, 1.2, 0.1, 0.7,
        0.3, 0.2, 0.5, 0.1
    };

    for (int i = 0; i < 16; i++) {
        grid[i] -= 0.1;
        if (grid[i] <= 0.3)
            grid[i] = 0.0;
    }

    for (int i = 0; i < 16; i++) {
       int alive_neighbors = 0;
       int j = i - 1;
       int w = i + 1;

       if(
        j >= 0 && 
        grid[j] != 0
        )
          alive_neighbors++; 

        if(
        w <= 15 && 
        grid[w] != 0
        )
          alive_neighbors++; 

        if (grid[i] != 0) {
            if (alive_neighbors == 0)
                grid[i] = 0.0;
        } else {
            if (alive_neighbors == 2)
                grid[i] = 1.0;
        }
    }

    return 0;
}