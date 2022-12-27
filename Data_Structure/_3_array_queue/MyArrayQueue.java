package _3_array_queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
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

    public E poll() {

        if (size == 0) {
            return null;
        }
        front = (front + 1) % array.length;

        @SuppressWarnings("unchecked")
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

    @Override
    public Object clone() {

        try {
            @SuppressWarnings("unchecked")
            MyArrayQueue<E> clone = (MyArrayQueue<E>) super.clone();

            clone.array = Arrays.copyOf(array, array.length);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    /**
     * Comparator를 넘겨주지 않은 경우해당 객체의 Comparable에 구현된 정렬 방식을 사용한다.
     * 만약 구현되어 있지 않으면 cannot be cast to clas java.lang.Comparable에러가 발생한다.
     * 만약 구현되어 있을 경우 null로 파라미터를 넘기면 Arrays.sort()가
     * 객체의 CompareTo 메소드에 정의된 방식대로 정렬한다.
     */
    public void sort() {
        sort(null);
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {

        // null 접근 방지를 위해 toArray로 요소만 있는 배열을 얻어 이를 정렬한 뒤 덮어씌운다.
        Object[] res = toArray();
        Arrays.sort((E[]) res, 0, size, c);
        clear();
        // 정렬된 res의 원소를 array에 1부터 채운다.
        // array의 front = 0 인덱스는 비워야 하므로 1번부터 채워야 하는 것이다.
        System.arraycopy(res, 0, array, 1, res.length);this.rear = this.size = res.length;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<E> {

        private int count = 0;
        private int len = array.length;
        private int now = (front + 1) % len;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            int cs = count;
            int ns = now;
            if (cs >= size) {
                throw new NoSuchElementException();
            }
            Object[] data = MyArrayQueue.this.array;
            count = cs + 1;
            now = (ns + 1) % len;
            return (E) data[ns];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
