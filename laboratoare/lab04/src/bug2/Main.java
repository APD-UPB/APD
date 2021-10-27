package bug2;

/**
 * DO NOT MODIFY
 */
public class Main {

    public static void main(String[] args) {
        Thread thread = new Thread(new MyThread());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(MyThread.i);
    }

}
