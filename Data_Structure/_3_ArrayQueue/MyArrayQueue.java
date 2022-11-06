package _3_ArrayQueue;

import java.rmi.NoSuchObjectException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import my_interface.MyQueueInterface;

/**
 * @param <E> the type of elements int this Queue
 */
public class MyArrayQueue<E> implements MyQueueInterface<E>, Cloneable, Iterable<E> {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private static final int DEFAULT_CAPACITY = 64;

    private Object[] array;
    private int size;
    private int front;
    private int rear;

    public MyArrayQueue() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    public MyArrayQueue(int capacity) {
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
        Object[] newArray = new Object[newCapacity];

        for (int i = 1, j = front + 1; i <= size; i++, j++) {
            newArray[i] = array[j % arrayCapacity];
        }

        this.array = newArray;
        this.front = 0;
        this.rear = this.size;
    }

    /**
     * resizing할 때, overflow를 방지하기 위한 확인 함수
     *
     * @param oldCapacity resize 하기 전의 용적
     * @param newCapacity resize 하고자 하는 용적
     * @return 최종 크기 반환
     */
    private int hugeRangeCheck(int oldCapacity, int newCapacity) {
        if (MAX_ARRAY_SIZE - size <= 0) {
            throw new OutOfMemoryError("Required queue length large");
        }

        if (newCapacity >= 0) {
            if (newCapacity - MAX_ARRAY_SIZE <= 0) {
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
        int oldCapacity = array.length;
        if ((rear + 1) % oldCapacity == front) {
            resize(oldCapacity + (oldCapacity << 1));
        }
        rear = (rear + 1) % array.length;

        array[rear] = item;
        size++;

        return true;
    }

    @SuppressWarnings("unchecked")
    public E poll() {

        if (size == 0) {
            return null;
        }
        front = (front + 1) % array.length;

        E item = (E) array[front];
        array[front] = null;
        size--;

        if (array.length > DEFAULT_CAPACITY && (size < array.length / 4)) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }
        return item;
    }

    public E remove() {

        E item = poll();

        if (item == null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0) {
            return null;
        }

        E item = (E) array[(front + 1) % array.length];
        return item;
    }

    public E element() {
        E item = peek();
        if (item == null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {
        if (value == null) {
            return false;
        }
        int start = (front + 1) % array.length;

        for (int i = 0, idx = start; i < size; i++, idx = (idx + 1) % array.length) {
            if (value.equals(array[idx])) {
                return true;
            }
        }
        return false;
    }

    public void clear() {

        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        front = rear = size = 0;
    }

    public Object[] toArray() {
        return toArray(new Object[size]);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        final T[] res;
        if (a.length < size) {
            if (front <= rear) {
                return (T[]) Arrays.copyOfRange(array, front + 1, rear + 1, a.getClass());
            }
            res = (T[]) Arrays.copyOfRange(array, 0, size, a.getClass());
            int rearLength = array.length - 1 - front;
            if (rearLength > 0) {
                System.arraycopy(array, front + 1, res, 0, rearLength);
            }
            System.arraycopy(array, 0, res, rearLength, rear + 1);
            return res;
        }

        if (front <= rear) {
            System.arraycopy(array, front + 1, a, 0, size);
        } else {
            int rearLength = array.length - 1 - front;
            if (rearLength > 0) {
                System.arraycopy(array, front + 1, a, 0, rearLength);
            }
            System.arraycopy(array, 0, a, rearLength, rear + 1);
        }
        return a;
    }
}