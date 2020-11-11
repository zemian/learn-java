// Lesson 4b
// Java supports anonymous function (without name) and it's called Lambda.
// You often use these to create a one time function usage that pass to other function
// as argument.
//
// Further study:
// - See https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
// - Learn about Java Stream https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
//
public class Lesson04b_AnonymousFunctions {
    public static void main(String[] args) {
        // Creating a anonymous function/closure/lambda and pass into a caller
        doAction(() -> {
            System.out.println("Hi");
        });

        // Or, usually want to shorter into one line for single expresion
        doAction(() -> System.out.println("Hi"));

        // Java Stream uses lambda a lot
        int[] numbers = {7, 8, 9};
        int[] doubleNumbers = java.util.Arrays.stream(numbers).map((num) -> num * 2).toArray();
        System.out.println(doubleNumbers[0]);
        System.out.println(doubleNumbers[1]);
        System.out.println(doubleNumbers[2]);

        // Or if there is only one argument, you don't need parenthesis
        doubleNumbers = java.util.Arrays.stream(numbers).map(num -> num * 2).toArray();
        System.out.println(doubleNumbers[0]);
    }

    /*
    How to design function that accept other function:
    See https://stackoverflow.com/questions/29945627/java-8-lambda-void-argument

        Use Supplier if it takes nothing, but returns something.

        Use Consumer if it takes something, but returns nothing.

        Use Callable if it returns a result and might throw (most akin to Thunk in general CS terms).

        Use Runnable if it does neither and cannot throw.
     */

    // A function that accepts a function as parameter
    public static void doAction(Runnable callback) {
        System.out.println("About to invoke a anonymous function");
        callback.run();
        System.out.println("Anonymous function finished");
    }
}
