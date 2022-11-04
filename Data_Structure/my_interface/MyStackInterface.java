package my_interface;

/*
  @author yback1223
  @param <E> the type of elements in this Stack

  @version 1.0
 */

public interface MyStackInterface<E> {

  E push(E item);
  /*
    스택의 맨 위에 요소를 추가한다.

    @param item 스택에 추가할 요소
    @return 스택에 추가된 요소
   */

  E pop();
  /*
    스택의 맨 위에 있는 요소를 제거하고 제거된 요소를 반환한다.

    @return 제거된 요소
   */

  E peek();
  /*
    스택의 맨 위에 있는 요소를 제거하지 않고 반환한다.

    @return 스택의 맨 위에 있는 요소
   */

  int search(Object value);
  /*
    스택의 상반 부터 특정 요소가 몇 번째 위치에 있는지 반환
    중복되는 요소가 있을 경우 가장 위에 있는 요소의 위치가 반환된다.

    @param value 스택에서 위치를 찾을 요소
    @return 스택의 상단부터 처음으로 요소와 일치하는 위치를 반환한다.
            일치하는 요소가 없을 경우 -1을 반환
   */

  int size();
  /*
    스택의 요소 개수를 반환한다.

    @return 스택에 있는 요소 개수를 반환
   */

  void clear();
  /*
    스택에 있는 모든 요소를 삭제한다.
   */

  boolean empty();
  /*
    스택의 요소가 비어있는지를 반환한다.

    @return 스택에 요소가 없을 경우 true, 있을 경우 false를 반환
   */
}
