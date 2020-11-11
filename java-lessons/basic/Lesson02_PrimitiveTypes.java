// Lesson 2
// Different types of data types in Java
//
// Further study:
// - Explore the data range for each type
// - Explore more operators you can use for each data type
// - Explore some basic operators you can use on each type
// - Explore what happen when you mix types when using with operators
// - Learn how to use good name on variable!
// - Learn some of the common String function/methods you can use
// - Learn that you can assign "null" value into Object like String or Integer!
// - Learn more on java.util.Arrays class and it's methods
public class Lesson02_PrimitiveTypes {
    public static void main(String[] args) {
        // # The 8 primitive types
        // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html

        byte b = 0x7F;
        short s = 99;
        int i = 1234567890;
        long n = 1234567890123456789L;
        float f = 3.14f;
        double d = 3.14159265358979323846264338327950288419716939937510;
        boolean t = true;
        char c = '\uffff';

        System.out.println(b);
        System.out.println(s);
        System.out.println(i);
        System.out.println(n);
        System.out.println(f);
        System.out.println(d);
        System.out.println(t);
        System.out.println(c);

        // Array type - a container
        int[] numbers = {1, 2, 3};
        System.out.println(numbers);
        System.out.println(numbers[0]);
        System.out.println(numbers[1]);
        System.out.println(numbers[2]);
        System.out.println(numbers.length);

        // ## Operators
        int sum = 99 + 1;
        double strange = 0.1 + 0.2;
        System.out.println(sum);
        System.out.println(strange); // Output: 0.30000000000000004
        System.out.println(0xFF);
        System.out.println(0xFF >>> 1);

        // ## Operator Precedence
        // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
        System.out.println(n + 9 - i * d / 5);
        System.out.println(n + (9 - i) * d / 5);

        // # Builtin String Object type
        String str = "foo";
        System.out.println(str);
        System.out.println(str + " bar");
        System.out.println(str + 3.14);
        System.out.println(str.length()); // Use of "dot" operator here
        System.out.println(str.substring(0, 2));
        System.out.println(str.indexOf("o"));
        char[] characters = str.toCharArray();
        System.out.println(characters[0]);

        String[] list = {"foo", "bar", "baz"};
        System.out.println(list);
        System.out.println(list[0]);

        // ## Object wrapper for primitive types
        Integer intObj = new Integer(99); // Redundant to use "new"
        Integer intObj2 = 99; // Default to use Autoboxing
        Double doubleObj = 3.14;
        Integer[] intArray = {1, 2, 3};
    }
}
