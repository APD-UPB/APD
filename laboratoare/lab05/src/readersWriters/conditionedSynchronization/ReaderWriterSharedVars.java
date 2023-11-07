package readersWriters.conditionedSynchronization;

public class ReaderWriterSharedVars {
    // The value to read/write
    volatile int shared_value;
    // TODO: Add semaphores and anything else needed for synchronization
    // Semaphore s;

    ReaderWriterSharedVars(int init_shared_value) {
        this.shared_value = init_shared_value;
        //this.s = new Semaphore(1);
    }

}
