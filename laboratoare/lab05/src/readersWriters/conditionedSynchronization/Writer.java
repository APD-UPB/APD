package readersWriters.conditionedSynchronization;

public class Writer extends Thread {
    private final int id;
    private final int number_of_writes;
    private final long wait_time_ms;
    private final ReaderWriterSharedVars shared_vars;
    private double start_time;
    private double completion_time;

    public Writer(int id, int number_of_writes, long wait_time_ms, ReaderWriterSharedVars shared_vars) {
        this.id = id;
        this.number_of_writes = number_of_writes;
        this.wait_time_ms = wait_time_ms;
        this.shared_vars = shared_vars;
    }

    @Override
    public void run() {
        this.start_time = System.currentTimeMillis() / 1000.0;

        for (int i = 0; i < number_of_writes; i++) {
            // TODO: Add synchronization
            write();
        }

        this.completion_time = System.currentTimeMillis() / 1000.0 - this.start_time;
        System.out.println("Writer with ID = " + this.id + " ended after " + completion_time + "s");
    }

    public double getCompletion_time() {
        return completion_time;
    }

    public void write() {
        // This is an example of updating the value based on the previous one.
        // Reading should also be OK for writers, since they have exclusive access.

        // Compute the new value
        int new_value = shared_vars.shared_value + 3;

        // Print an informative message (for debug)

        System.out.println(Utils.get_current_time_str() + " | Writer " + this.id + " writes " + shared_vars.shared_value);
        this.shared_vars.shared_value = new_value;

        // sleep wait_time_ms to simulate a longer write operation
        try {
            sleep(wait_time_ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}