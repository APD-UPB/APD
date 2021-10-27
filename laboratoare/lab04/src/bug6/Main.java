package bug6;

public class Main {
    public static int NUMBER_OF_THREADS = 8;

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i] = new MyThread();
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (Singleton.getNumberOfInstances() == 1) {
            System.out.println("Correct");
        } else {
            System.out.println("Incorrect, there should be exactly one instance");
        }
    }
}
