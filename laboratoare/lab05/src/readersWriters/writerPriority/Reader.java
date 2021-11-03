package readersWriters.writerPriority;

import java.util.concurrent.BrokenBarrierException;

public class Reader extends Thread {
    private final int id;

    public Reader(int id) {
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
            // TODO

            Main.currentReaders++;
            // TODO

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Reader " + id + " is reading");
            Main.hasRead[id] = true;

            // TODO
            Main.currentReaders--;

            // TODO

        } while (!Main.hasRead[id]);
    }
}
