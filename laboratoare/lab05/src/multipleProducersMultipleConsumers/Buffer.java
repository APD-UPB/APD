package multipleProducersMultipleConsumers;

public class Buffer {
    private int a;

    public void put(int value) {
        a = value;
    }

    public int get() {
        return a;
    }
}
