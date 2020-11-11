// Lesson 4
// Function is for grouping reusable statements into a unit that we can invoke
// together.
//
// Further study:
// - Learn about early "return" statement in function
// - Learn about StackOverFlow error by bad recursive function
// - Learn about what makes function signature unique
// - Notice the usage of "static" keyword on function
public class Lesson04_Functions {
    public static void main(String[] args) {
        printHi();

        printName("Zemian");
        printName("Kenny");
        printName("Hiya", "Zemian");

        printNameList();
        printNameList("Matthew", "Mark", "Luke", "John");

        System.out.println(sum(1, 2));
        System.out.println(sum(0.1, 0.2));

        walk(3);
    }

    // Simple function
    public static void printHi() {
        System.out.println("Hi Zemian");
    }

    // Function with parameter
    public static void printName(String name) {
        System.out.println("Hi " + name);
    }

    // Function overloading - by number of arguments
    public static void printName(String greeting, String name) {
        System.out.println(greeting + " " + name);
    }

    // Function that accept zero or more arguments
    public static void printNameList(String ... names) {
        for (String name : names) {
            System.out.println(name);
        }
    }

    // Function that returns value
    public static int sum(int a, int b) {
        return a + b;
    }

    // Function overloading - by type
    public static double sum(double a, double b) {
        return a + b;
    }

    // Recursive function
    public static void walk(int level) {
        if (level > 0) {
            System.out.println("Walking on level " + level);
            walk(level - 1);
        }
    }
}
