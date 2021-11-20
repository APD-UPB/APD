package sampleForkJoinPoolFuture;

import java.util.concurrent.RecursiveTask;

public class FibonacciCalculator extends RecursiveTask<Integer> {
    private final int n;

    public FibonacciCalculator(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n == 1) {
            return 0;
        }

        if (n == 2) {
            return 1;
        }

        FibonacciCalculator first = new FibonacciCalculator(n - 1);
        FibonacciCalculator second = new FibonacciCalculator(n - 2);

        first.fork();
        second.fork();

        return first.join() + second.join();
    }
}
