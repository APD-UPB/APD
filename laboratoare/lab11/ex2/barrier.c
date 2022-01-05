#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>

int main (int argc, char *argv[])
{
    int  numtasks, rank;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    if (rank == 0) {
        printf("1\n");
    }

    if (rank == 1) {
        printf("2\n");
    }

    if (rank == 2) {
        printf("3\n");
    }

    MPI_Finalize();
}
