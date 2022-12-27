package _3_array_queue;

public class testSortUsingComparable {

    public static void main(String[] args) {
        MyArrayQueue<Student2> myQ = new MyArrayQueue<>();

        myQ.offer(new Student2("윤대혁", 25));
        myQ.offer(new Student2("조병화", 25));
        myQ.offer(new Student2("송근일", 24));
        myQ.offer(new Student2("최이재", 28));

        myQ.sort();

        for (Object obj : myQ.toArray()) {
            System.out.println(obj);
        }
    }
}

class Student2 implements Comparable<Student2> {
    String name;
    int age;

    public Student2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "name : " + name +
            ", age =" + age;
    }

    @Override
    public int compareTo(Student2 o) {
        return o.age - this.age;
    }
}

