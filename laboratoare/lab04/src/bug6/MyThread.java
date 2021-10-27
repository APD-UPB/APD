package bug6;

public class MyThread extends Thread {
    @Override
    public void run() {
        Singleton.getInstance();
    }
}
