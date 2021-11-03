package oneProducerOneConsumer;

public class Buffer {
    private int a;

    void put(int value) {
        a = value;
    }

    int get() {
        return a;
    }
}
