/*
     자바 List Interface
     이 List Interface는 ArrayList, SinglyLinkedList, DoublyLinkedList에 의해 구현된다.
 */

public interface List<E> {

     boolean add(E value);
     /*
          리스트에 요소를 추가한다.

          @param value - 리스트에 추가할 요소
          @return - 리스트에 중복되는 요소가 있을 경우 false를 반환하고, 없을 경우 true를 반환한다.
      */

     void add(int index, E value);
     /*
          리스트의 index위치에 value를 추가한다.
          특정 위치 및 이후의 요소들은 한 칸씩 뒤로 밀려난다.

          @param index - 리스트의 위치
          @param value - 추가할 데이터
      */

     E remove(int index);
     /*
          리스트의 index 위치에 있는 요소를 삭제한다.

          @param index - 리스트에서 삭제할 요소의 위치
          @return - 삭제된 요소를 반환
      */

     boolean remove(Object value);
     /*
          리스트의 요소 중 value를 삭제한다. 값이 value인 요소가 여러 개일 경우, 가장
          처음 발견한 요소 하나만 삭제한다.

          @param value - 리스트에서 삭제할 요소
          @return - 리스트에서 삭제할 요소가 없거나 삭제에 실패할 경우 false를 반환,
          삭제에 성공헀을 경우 true를 반환한다.
      */

     E get(int index);
     /*
          리스트의 index 위치에 있는 요소를 반환한다.

          @param index - 리스트의 위치
          @return - 리스트의 index 위치에 있는 요소를 반환한다.
      */

     void set(int index, E value);
     /*
          리스트의 index 위치에 있는 값을 value로 대체한다.

          @param index - 리스트의 위치
          @return - 새로 대체한 변수(value)
      */

     boolean contains(Object value);
     /*
          리스트의 요소 중 value가 있는지 확인한다.

          @param value - 리스트에서 찾을 변수
          @return - 리스트의 요소 중 value가 있을 경우 true를 반환, 없을 경우
          false를 반환한다.
      */

     int indexOf(Object value);
     /*
          리스트의 요소 중 값이 value인 요소의 위치를 반환한다.

          @param value - 리스트에서 찾을 변수
          @return - 리스트의 요소 중 값이 value인 요소의 위치를 반환, 요소 중에
          value가 없다면 -1을 반환한다.
      */

     int size();
     /*
          리스트 요소의 개수를 반환한다.

          @return - 리스트의 요소 개수를 반환한다.
      */

     boolean isEmpty();
     /*
          리스트가 비어있는지를 확인한다.

          @return - 리스트에 요소가 없을 경우 true를 반환, 요소가 있을 경우 false를 반환
      */

     public void clear();
     /*
          리스트의 모든 요소를 삭제한다.
      */
}
