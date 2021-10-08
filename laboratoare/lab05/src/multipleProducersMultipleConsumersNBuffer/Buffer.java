package multipleProducersMultipleConsumersNBuffer;

import java.util.Queue;

/**
 * @author Gabriel Gutu <gabriel.gutu at upb.ro>
 *
 */
public class Buffer {
    
    Queue queue;
    
    public Buffer(int size) {
        queue = new LimitedQueue(size);
    }

	void put(int value) {
        queue.add(value);        
	}

	int get() {
        return (int)queue.poll();
	}
}
