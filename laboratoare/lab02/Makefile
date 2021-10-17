build:
	gcc mutex.c -o mutex -lpthread -Wall
	gcc barrier.c -o barrier -lpthread -Wall
	gcc multiply_seq.c -o multiply_seq -lpthread -Wall
	gcc multiply_outer.c -o multiply_outer -lpthread -Wall
	gcc multiply_middle.c -o multiply_middle -lpthread -Wall
	gcc multiply_inner.c -o multiply_inner -lpthread -Wall
	gcc strassen.c -o strassen -lpthread -Wall
	gcc strassen_par.c -o strassen_par -lpthread -Wall
clean:
	rm -rf mutex barrier multiply_seq multiply_outer multiply_middle multiply_inner strassen strassen_par
