package sampleExecutorServiceCompletableFuture;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class MyRunnable implements Runnable {
	private final ExecutorService tpe;
	private final String path;
	private final String filename;
	private final AtomicInteger counter;
	private final CompletableFuture<String> completableFuture;

	public MyRunnable(
			ExecutorService tpe,
			String path,
			String filename,
			AtomicInteger counter, CompletableFuture<String> completableFuture
	) {
		this.tpe = tpe;
		this.path = path;
		this.filename = filename;
		this.counter = counter;
		this.completableFuture = completableFuture;
	}

	@Override
	public void run() {
		File file = new File(path);
		if (file.isFile()) {
			if (file.getName().equals(filename)) {
				completableFuture.complete(file.getAbsolutePath());
				tpe.shutdown();
			}
		} else if (file.isDirectory()) {
			var files = file.listFiles();
			if (files != null) {
				for (var f : files) {
					counter.incrementAndGet();
					Runnable t = new MyRunnable(tpe, f.getPath(), filename, counter, completableFuture);
					tpe.submit(t);
				}
			}
		}

		int left = counter.decrementAndGet();
		if (left == 0) {
			completableFuture.complete(null);
			tpe.shutdown();
		}
	}
}
