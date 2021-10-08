package sampleExecutorService;
import java.util.concurrent.ExecutorService;

public class MyRunnable implements Runnable {
	private final int a;
	private final ExecutorService tpe;

	public MyRunnable(ExecutorService tpe, int a) {
		this.a = a;
		this.tpe = tpe;
	}

	@Override
	public void run() {
		if (a > 10) {
			tpe.shutdown();
			return;
		}
		System.out.println(a);
		tpe.submit(new MyRunnable(tpe, a + 3));
	}

}
