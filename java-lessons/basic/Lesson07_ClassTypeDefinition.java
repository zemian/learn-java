// Lesson 6 - A real usage of Class to create custom user type and better organizing of your code.
//            A custom class allow us to not just group functions together (also called methods)
//            but we group them in such way that they are used to manipulate some member
//            variables (data) inside the class. When you create a new instance (called object in
//            general) from a Class, the instance will have their own copy of these member variables
//            and hence hold "states" and values specific to a instance. Organizing your code this
//            way is called Object Oriented design.
//
// Further study:
// - Compare OO to functional programming design
// - Learn more about primitive wrapper classes and their functions/methods you can use
// - Learn more about static keyword usage
// - Learn about method override vs overloading
// - Learn about java.lang.Object and it's methods
// - Learn about member default value
// - Learn what getter and setter methods are
public class Lesson07_ClassTypeDefinition {
    public static void main(String[] args) {
        // Explore an built-in Class: Integer (or java.lang.Integer)

        // Create a new instance of a class
        Integer num = new Integer(127);
        System.out.println(num.toString());

        // Use their method specific to this instance
        byte numByte = num.byteValue();
        System.out.println(numByte);

        // Create multiple instances
        Integer[] numArray = {new Integer(7), new Integer(8), new Integer(9)};
        System.out.println(numArray[0].byteValue());
        System.out.println(numArray[1].byteValue());
        System.out.println(numArray[2].byteValue());

        // Or you use their utility methods related to Integer
        String binaryNum = Integer.toBinaryString(0xAB07);
        System.out.println(binaryNum);

        // Now try custom class: Car
        Car car = new Car("Red");
        System.out.println(car.getDescription());

        Car[] cars = {new Car("Yellow"), new Car("Blue"), new Car("Green")};
        System.out.println(cars[0].getDescription());
        System.out.println(cars[1].getDescription());
        System.out.println(cars[2].getDescription());

        // Now modify one instance of the car
        cars[0].changeColor("Orange");
        System.out.println(cars[0].getDescription()); // Changed!
        System.out.println(cars[1].getDescription()); // Unchanged
        System.out.println(cars[2].getDescription()); // Unchanged
    }

    // Class definition - keep in mind that you are writing the type declaration
    //                    (template for each instance object)
    public static class Car {
        // Notice that we no longer use "static" keyword for the rest of the class definition!
        public String color;

        // Using a Constructor to accept argument to the instance
        public Car(String color) {
            // Notice the usage of "this", which means the "instance" that you are working on.
            this.color = color;
        }

        public String getDescription() {
            return "My car color is " + this.color;
        }

        public void changeColor(String newColor) {
            this.color = newColor;
        }
    }
}
