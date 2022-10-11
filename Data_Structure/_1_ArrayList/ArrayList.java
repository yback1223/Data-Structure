package _1_ArrayList;

import java.util.Arrays;
import my_interface.List;


public class ArrayList<E> implements List<E>, Cloneable, Iterable<E> {

     private static final int DEFAULT_CAPACITY = 10;

     private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
     /*
          The maximum length of array to allocate.
          확장 가능한 용적의 한계값
          Java에서 인덱스는 int 정수로 인덱싱을 하고, 이론적으로 Integer.MAX_VALUE(2^31 - 1)의 인덱스를 가질 수 있다.
          하지만 VM에 따라서 배열의 크기 제한이 상이하며, 제한 값을 초과할 경우 다음의 에러가 발생한다.

          java.lang.OutOfMemoryError: Requested array size exceeds VM limit

          제한 값의 초과를 방지해야 위의 에러가 뜨지 않으므로, 이론적으로 가능한 최댓값에 8을 뺀 값으로 최대 크기를 지정한다.
      */

     private static final Object[] EMPTY_ARRAY = {};
     private int size;

     Object[] array;

     public ArrayList() {
          this.array = EMPTY_ARRAY;
          this.size = 0;
     }

     public ArrayList(int capacity) {
          if (capacity < 0)
               throw new IllegalArgumentException();

          if (capacity == 0) {
               array = EMPTY_ARRAY;
          } else {
               array = new Object[capacity];
          }
          this.size = 0;
     }

     private void resize() {
          int array_capacity = array.length;

          // if (array_capacity == 0)
          if (Arrays.equals(array, EMPTY_ARRAY)) {
               array = new Object[DEFAULT_CAPACITY];
               return;
          }

          // if array is full
          if (size == array_capacity) {

               // default growing 1.5x
               int new_capacity = hugeRangeCheck(array_capacity, array_capacity + (array_capacity << 1));

               // copy
               array = Arrays.copyOf(array, new_capacity);
               return;
          }

          // if array is less than half full
          if (size < (array_capacity >> 1)) {
               int new_capacity = array_capacity >>> 1;

               // copy
               array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
               return;
          }
     }

     private int hugeRangeCheck(int oldCapacity, int newCapacity) {
          /*
               resizing할 때, overflow를 방지하기 위한 체크 함수
               용적은 MAX_ARRAY_SIZE를 초과할 수 없다.

               @param oldCapacity - resize하기 전의 용전
               @param newCapacity - resize하고자 하는 용적
               @return - 최종 크기 반환
           */
          if (MAX_ARRAY_SIZE - size <= 0) {
               throw new OutOfMemoryError("Required array length too large");
          }

          if (newCapacity >= 0) { // no overflow
               if (newCapacity - MAX_ARRAY_SIZE <= 0) {
                    return newCapacity;
               }
               return MAX_ARRAY_SIZE;
          } else { // newCapacity is overflow
               int fiveFourtheSize = oldCapacity + (oldCapacity >> 2);

               if (fiveFourtheSize <= 0 || fiveFourtheSize >= MAX_ARRAY_SIZE) {
                    return MAX_ARRAY_SIZE;
               }
               return fiveFourtheSize;
          }
     }

     @Override
     public boolean add(E value) {
          addLast(value);
          return true;
     }

     public void addLast(E value) {
          if (size == array.length) {
               resize();
          }
          array[size] = value;
          size++;
     }

     public void addFirst(E value) {
          add(0, value);
     }

     @Override
     public void add(int index, E value) {
          if (index > size || index < 0) {
               throw new IndexOutOfBoundsException();
          }

          if (index == size) {
               addLast(value);
               return;
          }

          if (size == array.length) {
               resize();
          }

          for (int i = size; i > index; i--) {
               array[i] = array[i - 1];
          }
          array[index] = value;
          size++;
     }
}
