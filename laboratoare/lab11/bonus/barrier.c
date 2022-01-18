#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>
    
int main(int argc, char **argv) {
    
    MPI_File out;
    int rank, numtasks;
    int ierr;
    
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);
    
    remove("out.txt");

    ierr = MPI_File_open(MPI_COMM_WORLD, "out.txt", MPI_MODE_CREATE | MPI_MODE_RDWR, MPI_INFO_NULL, &out);
    if (ierr) {
        if (rank == 0) fprintf(stderr, "%s: Couldn't open output file %s\n", argv[0], argv[2]);
        MPI_Finalize();
        exit(1);
    }
    
    if (rank == 0) {
    	MPI_File_write_shared(out, "h", 1, MPI_CHAR, MPI_STATUS_IGNORE);
    }
    	
    if (rank == 1) {
        MPI_File_write_shared(out, "e", 1, MPI_CHAR, MPI_STATUS_IGNORE);
    }
    
    if (rank == 2) {
        MPI_File_write_shared(out, "l", 1, MPI_CHAR, MPI_STATUS_IGNORE);
    }
    
    if (rank == 3) {
        MPI_File_write_shared(out, "l", 1, MPI_CHAR, MPI_STATUS_IGNORE);
    }

    if (rank == 4) {
        MPI_File_write_shared(out, "o", 1, MPI_CHAR, MPI_STATUS_IGNORE);
    }

    MPI_File_close(&out);
    printf("Process [%d] finished writing\n", rank);

    
    MPI_Finalize();
    return 0;
}


