package sampleForkJoinPool;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool fjp = new ForkJoinPool(4);
        fjp.invoke(new Task("files"));
        fjp.shutdown();
    }
}
