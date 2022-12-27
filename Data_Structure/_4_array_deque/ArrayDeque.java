package _4_array_deque;

import my_interface.MyQueueInterface;

public class ArrayDeque<E> implements MyQueueInterface<E> {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final int DEFAULT_CAPACITY = 64;

    private Object[] array;
    private int size;

    private int front;
    private int rear;

    public ArrayDeque() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    public ArrayDeque(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.array = new Object[capacity];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    private void resize(int newCapacity) {
        int arrayCapacity = array.length;
        newCapacity = hugeRangeCheck(arrayCapacity, newCapacity);
    }

    private int hugeRangeCheck(int oldCapacity, int newCapacity) {
        if (MAX_ARRAY_SIZE <= size) {
            throw new OutOfMemoryError("Required queue length too large");
        }
        if (newCapacity >= 0) {
            if (newCapacity <= MAX_ARRAY_SIZE) {
                return newCapacity;
            }
            return MAX_ARRAY_SIZE;
        } else {
            int fiveFourthsSize = oldCapacity + (oldCapacity >>> 2);
            if (fiveFourthsSize <= 0 || fiveFourthsSize >= MAX_ARRAY_SIZE) {
                return MAX_ARRAY_SIZE;
            }
            return fiveFourthsSize;
        }
    }

    @Override
    public boolean offer(E item) {
        return offerLast(item);
    }

    public boolean offerLast(E item) {
        int oldCapacity = array.length;

        if ((front - 1 + oldCapacity) % oldCapacity == rear) {
            resize(oldCapacity + (oldCapacity >> 1));
        }

        array[front] = item;
        front = (front - 1 + array.length) % array.length;
        size++;

        return true;
    }

    public boolean offerFirst(E item) {
        int oldCapacity = array.length;


    }
}
