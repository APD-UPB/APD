package bug3;

/**
 * Why is value set correct even though we use different locks in
 * different threads?
 *
 * Modify a single line of code to obtain the expected behaviour (race condition since the monitors are different).
 */
public class MyThread implements Runnable {
    static final String a = "LOCK";
    static final String b = "LOCK";
    int id;
    static int value = 0;

    MyThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        if (id == 0) {
            synchronized (a) {
                for (int i = 0; i < Main.N; i++)
                    value = value + 3;
            }
        } else {
            synchronized (b) {
                for (int i = 0; i < Main.N; i++)
                    value = value + 3;
            }
        }
    }
}
