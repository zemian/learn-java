import java.util.Comparator;

// Lesson 8
// Just like lambda (function without name), Class can be also created without a name.
// These are frequently used when you need a one time instance need.
//
// Further study:
// - Learn how access modifier affect inner class.
//
public class Lesson10_AnonymousClass {
    public static void main(String[] args) {
        // Create an instance of a Runnable object (anonymous sub-class instance)
        Runnable task = new Runnable() {
            public void run() {
                System.out.println("I am inside a anonymous runnable class.");
            }
        };
        // Now Use it
        task.run();

        // Another example by creating anonymous Comparator instance
        Comparator<Integer> comp = new Comparator<>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a; // sort descending order
            }
        };
        Integer[] numbers = {7, 2, 1, 8, 9};
        java.util.Arrays.sort(numbers, comp);
        System.out.println(java.util.Arrays.asList(numbers));

        // Simple anonymous class that has only one method can be replace by lambda directly!
        Integer[] numbers2 = {5, 6, 3, 8, 1, 2};
        java.util.Arrays.sort(numbers2, (a, b) -> b - a);
        System.out.println(java.util.Arrays.asList(numbers2));
    }
}
