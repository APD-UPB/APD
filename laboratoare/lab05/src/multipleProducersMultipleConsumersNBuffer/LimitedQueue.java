package multipleProducersMultipleConsumersNBuffer;

import java.util.LinkedList;

/**
 * @author Gabriel Gutu <gabriel.gutu at upb.ro>
 * 
 *         DO NOT MODIFY
 */
public class LimitedQueue extends LinkedList {

    private final int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(Object o) {
        boolean added = super.add(o);
        while (added && size() > limit) {
           super.remove();
        }
        return added;
    }

}
