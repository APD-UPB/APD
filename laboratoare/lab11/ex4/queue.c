#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stddef.h>

typedef struct {
    int size;
    int arr[1000];
} queue;

int main (int argc, char *argv[]) {
    int numtasks, rank;

    queue q;
    // TODO: declare the types and arrays for offsets and block counts

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    // TODO: create the MPI data type, using offsets and block counts, and commit the data type

    srand(time(NULL));
 
    // First process starts the circle.
    if (rank == 0) {
        // First process starts the circle.
        // Generate a random number and add it in queue.
        // Sends the queue to the next process.
    } else if (rank == numtasks - 1) {
        // Last process close the circle.
        // Receives the queue from the previous process.
        // Generate a random number and add it in queue.
        // Sends the queue to the first process.
    } else {
        // Middle process.
        // Receives the queue from the previous process.
        // Generate a random number and add it in queue.
        // Sends the queue to the next process.
    }

    // TODO: Process 0 prints the elements from queue
    
    // TODO: free the newly created MPI data type

    MPI_Finalize();
}