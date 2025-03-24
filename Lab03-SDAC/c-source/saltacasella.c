#include <stdio.h>
#include <stdlib.h>

int saltacasella(int** mat, int i, int j, int n, int N);
static int driver(int** mat, int N);
static void stampamatrice(int** mat, int N);

long Cont = 0;

int saltacasella(int** mat, int i, int j, int n, int N) {
    static int max = 0;
    if(i < 0 || j < 0 || i >= N || j >= N) return 2; // indici fuori posizione

    if(mat[i][j] != 0) return 1; // cella occupata

    mat[i][j] = n;
    Cont++;
    if(n > max) max = n;
    if(Cont % 500000000 == 0 ) printf("Cont = %ld\n", Cont);

    if(n == N*N) {
        printf("\nvittoria - Cont = %ld\n", Cont);
        return 0;
    }

    int m = ++n;

    int moves[8][2] = {
        {i, j-3}, // sx
        {i-3, j}, // up
        {i, j+3}, // dx
        {i+3, j}, // down
        {i-2, j-2}, // no
        {i-2, j+2}, // ne
        {i+2, j+2}, // se
        {i+2, j-2}  // so
    };
    // printf("Proviamo %d ...\n", m);
    for(int h = 0; h < 8; h++){
        // printf("Prova %d: (%d, %d) ...\n", h,moves[h][0], moves[h][1]);
        if(saltacasella(mat, moves[h][0], moves[h][1], m, N) == 0) return 0; // vittoria ricorsiva
    }
    mat[i][j] = 0;
    // printf("Torniamo indietro\n");
    return 3; /* no soluzione */
}


static int driver(int** mat, int N) {
    for(int i = 0; i < N; i++){
        for(int j = 0; j < N; j++) {
            int r = saltacasella(mat, i, j, 1, N);
            printf("Cella (%d, %d) res = %d\n", i, j, r);
            if(r == 0) return 0;
        }   
    }
        
    return(3);
}

static void stampamatrice(int** mat, int N) {
    for(int i = 0; i < N; i++) {
        for(int j = 0; j < N; j++) printf("%4d", mat[i][j]);
        printf("\n");
    }
}

int main(int argc, char **argv) {
    if(argc != 2) {
        printf("./saltacasella <size>");
        exit(3);
    }
    int N = atoi(argv[1]);
    int **mat = malloc(N * sizeof(int*));
    for(int i = 0; i < N; i++) mat[i] = calloc(N, sizeof(int));
    if(driver(mat, N) == 0)
        stampamatrice(mat, N);
    else
        printf("\nnon trovato\n");
    return 0;
}