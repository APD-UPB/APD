package bug1;

/**
 * Why is value set correct even though we do not use
 * locks/synchronized?
 * Do NOT modify this file.
 */
public class MyThread implements Runnable {
    public static int value = 0;

    @Override
    public void run() {
        for (int i = 0; i < Main.N; i++)
            value = value + 3;
    }
}
