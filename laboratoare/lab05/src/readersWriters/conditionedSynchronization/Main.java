package readersWriters.conditionedSynchronization;

import java.util.List;

// Hint: All the TODOs are in Reader, Writer and ReaderWriterSharedVars

public class Main {
    public static final int WRITERS = 4;
    public static final int READERS = 8;
    public static final long readers_wait_time_ms = 100;
    public static final long writers_wait_time_ms = 100;

    public static final int number_of_reads_per_thread = 100;
    public static final int number_of_writes_per_thread = 20;

    public static final int initial_shared_value = 10;

    public static void main(String[] args) throws InterruptedException {
        Thread[] readers = new Reader[READERS];
        Thread[] writers = new Writer[WRITERS];

        ReaderWriterSharedVars shared_vars = new ReaderWriterSharedVars(initial_shared_value);

        for (int i = 0; i < READERS; i++) {
            readers[i] = new Reader(i, number_of_reads_per_thread, readers_wait_time_ms, shared_vars);
        }

        for (int i = 0; i < WRITERS; i++) {
            writers[i] = new Writer(i, number_of_writes_per_thread, writers_wait_time_ms, shared_vars);
        }

        List<Thread> threads = Utils.concatenateAndShuffleArrays(readers, writers);

        for(Thread t: threads) {
            t.start();
        }

        for(Thread t : threads) {
            t.join();
        }


        System.out.println("Readers completion times: ");
        for(Thread thread: readers) {
            Reader reader = (Reader) thread;
            System.out.print(reader.getCompletion_time() + " ");
        }
        System.out.println();

        System.out.println("Writers completion times: ");
        for(Thread thread: writers) {
            Writer writer = (Writer) thread;
            System.out.print(writer.getCompletion_time() + " ");
        }
        System.out.println();

        int expected_final_value = initial_shared_value + 3 * number_of_writes_per_thread * WRITERS;
        System.out.println("Final value of Shared Var is " + shared_vars.shared_value + " (expected: " + expected_final_value + ")" );


    }
}
