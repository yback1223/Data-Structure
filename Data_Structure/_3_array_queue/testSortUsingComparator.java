package _3_array_queue;

import java.util.Comparator;

public class testSortUsingComparator {

    public static void main(String[] args) {
        MyArrayQueue<Student> myQ = new MyArrayQueue<>();

        myQ.offer(new Student("윤대혁", 25));
        myQ.offer(new Student("조병화", 25));
        myQ.offer(new Student("송근일", 24));
        myQ.offer(new Student("최이재", 28));

        //sort() 메소드의 파라미터로 사용자가 설정한 Comparator를 넘겨준다.
        myQ.sort(customComparator);

        for (Object obj : myQ.toArray()) {
            System.out.println(obj);
        }
    }

    // 사용자 설정 comparator(비교기)
    static Comparator<Student> customComparator = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2) {
            return o2.age - o1.age;
        }
    };
}

class Student {
    String name;
    int age;


    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "name : " + name +
            ", age =" + age;
    }
}
