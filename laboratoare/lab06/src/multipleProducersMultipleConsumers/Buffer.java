package multipleProducersMultipleConsumers;

public class Buffer {
	int value;

	void put(int value) {
		this.value = value;
	}

	int get() {
		return value;
	}
}
