build:
	mpicc non-blocking.c -o non-blocking
	mpicc sendrecv.c -o sendrecv
	mpicc rsend.c -o rsend
	mpicc bsend.c -o bsend
	mpicc struct.c -o struct
	mpicc barrier.c -o barrier
run-non-blocking:
	mpirun -np 2 non-blocking
run-sendrecv:
	mpirun -np 2 sendrecv
run-rsend:
	mpirun -np 2 rsend
run-bsend:
	mpirun -np 2 bsend
run-struct:
	mpirun -np 2 struct
run-barrier:
	mpirun -np 2 barrier
clean:
	rm non-blocking sendrecv rsend bsend struct barrier out.txt
