#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
 
int main(int argc, char* argv[]) {
    int size, rank, value;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    if (rank == 0) {
        MPI_Barrier(MPI_COMM_WORLD);
 
        value = 12345;
        printf("[P0] MPI process sends value %d.\n", value);
        MPI_Rsend(&value, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
    } else {
        MPI_Request request;
        MPI_Status status;
        int flag;
        MPI_Irecv(&value, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &request);
 
        MPI_Barrier(MPI_COMM_WORLD);
 
        MPI_Test(&request, &flag, &status);

        if (flag) {
            printf("[P1] The receive operation is over\n");
        } else {
            printf("[P1] The receive operation is not over yet\n");
            MPI_Wait(&request, &status);
        }
        printf("[P1] MPI process received value %d.\n", value);
    }
 
    MPI_Finalize();
    return 0;
}