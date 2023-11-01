package bug6;
/*
 *  Nothing to change here. The TODO is in Singleton.java.
*/
public class MyThread extends Thread {
    @Override
    public void run() {
        Singleton.getInstance();
    }
}
