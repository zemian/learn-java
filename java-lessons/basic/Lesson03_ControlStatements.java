// Lesson 3
// A statement is a line of executable code.
// We can control which statement(s) to execute and
// which ones to skip over, or even repeat them.
//
// Further study:
// - Learn about "switch" control statement (alternate to like if/elseif/else)
// - Learn about "do-while" loop
// - Learn about "continue" and "break" in loop
//
// Ref:
// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/flow.html
public class Lesson03_ControlStatements {
    public static void main(String[] args) {
        // Shortcut - increment/decrement operators
        int n = 0;
        System.out.println(n);
        n = n + 1;
        System.out.println(n);
        n++;
        System.out.println(n);
        n--;
        System.out.println(n);

        // More fancier operator
        n = n + 5;
        System.out.println(n);
        n += 5;
        System.out.println(n);

        // Comparison & Boolean Result
        int a = 1, b = 3;
        boolean compareResult = a < b;
        System.out.println(compareResult);
        System.out.println(a == b);
        System.out.println(a >= b);

        // Condition Code Branching
        if (a < b) {
            System.out.println("Condition is true");
        } else {
            System.out.println("Condition is false");
        }

        if (a == b) {
            System.out.println("a is equal to b");
        } else if (a > b) {
            System.out.println("a is greater than b");
        } else {
            System.out.println("a is lesser than b");
        }

        // For Loop - repeat
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }

        // Array Loop
        int [] numbers = {7, 8, 9};
        for (int i = 0; i < 3; i++) {
            int value = numbers[i];
            System.out.println(value);
        }

        // For Each Loop (with value directly without index counter)
        for (int value : numbers) {
            System.out.println(value);
        }

        // While Loop
        int counter = 0;
        while (counter < 3) {
            System.out.println(counter);
            counter++;
        }
    }
}
