package multipleProducersMultipleConsumersNBuffer;

/**
 * DO NOT MODIFY
 */
public class Consumer implements Runnable {
    private final Buffer buffer;
    private final int id;
    private static int i = 0;

    public Consumer(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    public int getNextI() {
        int value;
        synchronized (Consumer.class) {
            value = i;
            i++;
        }
        return value;
    }

    @Override
    public void run() {
        while (true) {
            int i = getNextI();
            if (i >= Main.N_PRODUCERS * Main.N)
                break;
            synchronized (Main.results) {
                Main.results[buffer.get()]++;
            }
        }
        System.out.println("Consumer " + id + " finished Correctly");
    }
}
