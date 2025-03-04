#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <time.h>

// prototipi 
void launcher(int*, int);
int *randArray(int);
void printArray(int*, int);


//
void bubbleSort(int*, int);



int * randArray(int n) {
    srand(time(NULL));
    int *v = malloc(n*sizeof(int));
    for(int i = 0; i < n; i++) v[i] = rand();
    return v;
}

void launcher(int *a, int n) {
    printArray(a, n);
    printf("Lancio il Bubble Sort... ");
    clock_t begin = clock();
    bubbleSort(a, n);
    clock_t end = clock();
    printf("fatto. Tempo: %g msec.\n", (double)(end - begin) * 1000 / CLOCKS_PER_SEC);
    printArray(a, n);
    // t2-t1 è il tempo impiegato da bubbleSort
}

void printArray(int *a, int n) {
    printf("L'array ha %d elementi\n", n);
    for(int i = 0; i < n; i++) printf("array[%d] = %d\n", i, a[i]);
    printf("Fine array.\n");
}


int main(int argc, char *argv[]) {
    // test
    // for (int i = 0; i < argc; i++) printf("argomento n. %d è %s\n", i, argv[i]);
    /*
    Lettura da linea di comando
    */
   	// int v[7] = {64, 34, 25, 12, 22, 11, 90};
    // launcher(v, 7);
    printf("argc: %d\n", argc);
    for (int i = 0; i < argc; i++){
        printf("argomento n. %d è %s\n", i, argv[i]);
    }
    if(argc > 1){
        printf("argv[1] è %s, la condizione è %d\n", argv[1], strcmp(argv[1], "rnd"));
        if(strcmp(argv[1], "rnd") == 0){
            // chiamata con random
            printf("chiamata rnd\n");
            int n = atoi(argv[2]);
            int* arr = randArray(n);
            launcher(arr, n);
        } else {
            // chiamata con lista da terminale
            printf("chiamata argv\n");
            int args[argc - 1];
            for (int i = 0; i < argc; i++){
                args[i] = atoi(argv[i]);
                printf("elemento n. %d è %d\n", i, args[i]);
            }
            launcher(args, argc - 1);
        }
    } else {
        printf("Nessuna argomento da linea di comando");
    }
	
}
