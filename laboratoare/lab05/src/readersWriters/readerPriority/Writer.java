package readersWriters.readerPriority;

import java.util.concurrent.BrokenBarrierException;

public class Writer extends Thread {
    private final int id;

    public Writer(int id) {
        super();
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Main.barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        do {
            try {
                // shared space is locked
                Main.readWriteSem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Writer " + id + " is writing");
            Main.hasWritten[id] = true;

            Main.readWriteSem.release();

        } while (!Main.hasWritten[id]);
    }
}
