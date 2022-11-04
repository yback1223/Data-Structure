package _2_Stack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import my_interface.MyStackInterface;

/**
 * @param <E> the type of elements in this Stack
 */
public class MyStack<E> implements MyStackInterface<E>, Cloneable, Iterable<E> {

  /**
   * The maximum length of array to allocate. 확장 가능한 용적의 한계값이다. Java에서 인덱스는 int 정수로 인덱싱한다. 이론적으로는
   * Integer.MAX_VALUE(2^31 -1) 의 인덱스를 갖을 수 있지만, VM에 따라 배열 크기 제한이 상이하며, 제한 값을 초과할 경우 다음과 같은 에러가
   * 발생한다.
   * <p>
   * "java.lang.OutOfMemoryError: Requested array size exceeds VM limit"
   * <p>
   * 위와 같은 이유로 안정성을 위해 이론적으로 가능한 최댓값에 8을 뺀 값으로 지정하고 있습니다.
   */
  private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

  private static final int DEFAULT_CAPACITY = 10;
  private static final Object[] EMPTY_ARRAY = {};

  private Object[] array; // 요소를 담을 배열
  private int size; // 요소 개수

  public MyStack() {
    this.array = EMPTY_ARRAY;
    this.size = 0;
  }

  public MyStack(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException();
    }
    if (capacity == 0) {
      array = EMPTY_ARRAY;
    } else {
      array = new Object[capacity];
    }
    this.size = 0;
  }

  private void resize() {
    if (Arrays.equals(array, EMPTY_ARRAY)) {
      array = new Object[DEFAULT_CAPACITY];
      return;
    }

    int arrayCapacity = array.length;
    if (size == arrayCapacity) {
      //default growing 1.5x
      int newSize = hugeRangeCheck(arrayCapacity, arrayCapacity + arrayCapacity << 1);
      array = Arrays.copyOf(array, newSize);
      return;
    }
    if (size < (arrayCapacity / 2)) {
      int newCapacity = (arrayCapacity / 2);
      array = Arrays.copyOf(array, Math.max(DEFAULT_CAPACITY, newCapacity));
      return;
    }
  }

  /**
   * resizing할 때 overflow를 방지하기 위한 체크 함수이다. 용적은 MAX_ARRAY_SIZE를 초과할 수 없다.
   *
   * @param oldCapacity resize 하기 전의 용적
   * @param newCapacity resize 하고자 하는 용적
   * @return 최종 크기 반환
   */
  private int hugeRangeCheck(int oldCapacity, int newCapacity) {
    if (MAX_ARRAY_SIZE - size <= 0) {
      throw new OutOfMemoryError("Required stack size too large");
    }
    if (newCapacity >= 0) {
      if (newCapacity - MAX_ARRAY_SIZE <= 0) {
        return newCapacity;
      }
      return MAX_ARRAY_SIZE;
    } else {
      int fiveFourtheSize = oldCapacity + (oldCapacity >>> 2);
      if (fiveFourtheSize <= 0 || fiveFourtheSize >= MAX_ARRAY_SIZE) {
        return MAX_ARRAY_SIZE;
      }
      return fiveFourtheSize;
    }
  }

  @Override
  public E push(E item) {
    if (size == array.length) {
      resize();
    }
    array[size] = item;
    size++;

    return item;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E pop() {
    if (size == 0) {
      throw new EmptyStackException();
    }
    E obj = (E) array[size - 1];
    array[size - 1] = null;
    size--;
    resize();

    return obj;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E peek() {
    if (size == 0) {
      throw new EmptyStackException();
    }
    return (E) array[size - 1];
  }

  @Override
  public int search(Object value) {

    if (value == null) {
      for (int idx = size - 1; idx >= 0; idx--) {
        if (array[idx] == null) {
          return size - idx;
        }
      }
    } else {
      for (int idx = size - 1; idx >= 0; idx--) {
        if (array[idx].equals(value)) {
          return size - idx;
        }
      }
    }
    return -1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      array[i] = null;
    }
    size = 0;
    resize();
  }

  @Override
  public boolean empty() {
    return size == 0;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    MyStack<?> cloneStack = (MyStack<?>) super.clone();

    cloneStack.array = new Object[size];
    System.arraycopy(array, 0, cloneStack.array, 0, size);
    return cloneStack;
  }

  public Object[] toArray() {
    return Arrays.copyOf(array, size);
  }

  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] a) {
    if (a.length < size) {
      return (T[]) Arrays.copyOf(array, size, a.getClass());
    }
    System.arraycopy(array, 0, a, 0, size);
    return a;
  }

  public void sort() {
    sort();
  }

  @SuppressWarnings("unchecked")
  public void sort(Comparator<? super E> c) {
    Arrays.sort((E[]) array, 0, size, c);
  }

  @Override
  public Iterator<E> iterator() {
    return new Iter();
  }

  private class Iter implements Iterator<E> {

    private int now = 0;

    @Override
    public boolean hasNext() {
      return now < size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E next() {
      int cs = now;
      if (cs >= size) {
        throw new NoSuchElementException();
      }
      Object[] data = MyStack.this.array;
      now = cs + 1;
      return (E) data[cs];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
