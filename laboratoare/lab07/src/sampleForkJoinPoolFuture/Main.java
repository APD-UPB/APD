package sampleForkJoinPoolFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        FibonacciCalculator calculator = new FibonacciCalculator(10);
        forkJoinPool.execute(calculator);
        System.out.println(calculator.get());
        forkJoinPool.shutdown();
    }
}
