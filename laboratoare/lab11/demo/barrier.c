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
        char message_to_write[5] = "hello";
    	MPI_File_write_at(out, 0, message_to_write, 5, MPI_CHAR, MPI_STATUS_IGNORE);
    }
    	
    MPI_Barrier(MPI_COMM_WORLD);
    	
    if (rank == 1) {
    	char message_to_read[5];
    	MPI_File_read_at(out, 0, message_to_read, 5, MPI_CHAR, MPI_STATUS_IGNORE);
    	printf("\nMesajul citit este: ");
        for (int i = 0; i < 5; i++)
    	    printf("%c", message_to_read[i]);
        printf("\n");
    }

    MPI_File_close(&out);
    
    MPI_Finalize();
    return 0;
}


