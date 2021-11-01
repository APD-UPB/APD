package bug1;
/*
	Two threads incrementing the same value in parallel should be in a race condition. However, something strage happens.
	Modify the code such that we can see the bad consequences of the race condition.
 */

public class Main {
	public static final int N = 100000;
	public static int N_ITERATIONS = 100;

	public static void main(String[] args) {
		Thread[] threads = new Thread[2];
		boolean sw = true;
		for (int j = 0; j < N_ITERATIONS; j++) {
			MyThread.value = 0;
			for (int i = 0; i < 2; i++) {
				threads[i] = new Thread(new MyThread());
				threads[i].run();
			}
			for (int i = 0; i < 2; i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (MyThread.value != 2 * 3 * N) {
				System.out.println("i was different than " + 2 * 3 * N + " it is " + MyThread.value);
				sw = false;
			}
		}
		if (sw) {
			System.out.println("Something is wrong. The value is always correct.");
		}
	}
}
