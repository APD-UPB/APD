package oneProducerOneConsumer;

/**
 * DO NOT MODIFY
 */
public class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.N; i++) {
            int value = buffer.get();
            if (value != i) {
                System.out.println("Wrong value. I was supposed to get " + i + " but I received " + value);
                System.exit(1);
            }
        }
        System.out.println("I finished Correctly");
    }

}
