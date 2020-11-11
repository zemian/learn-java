// Lesson 7
// Class definition can extend other class so that we can reuse code more effectively
//
// Further study:
// - A Class can only extend ONE parent class
// - Explore Interface and "abstract class"
// - Class can implements many Interface (empty class definition.)
// - What is polymorphism: https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html
// - Learn what class encapsulation mean
public class Lesson08_ClassInheritance {
    public static void main(String[] args) {
        Truck truck = new Truck("Red", 10_000);
        System.out.println(truck.getDescription());
        System.out.println(truck.getNumOfWheels());

        // We can also use parent class to store sub-class instance
        Car car = new Truck("Red", 10_000);
        System.out.println(car.getDescription());
        // Note can no longer can call this since Car class does not have this method!
        //System.out.println(car.getNumOfWheels()); // Error
    }

    public static class Car {
        public String color;

        public Car(String color) {
            this.color = color;
        }

        public String getDescription() {
            return "My car color is " + this.color;
        }

        public void changeColor(String newColor) {
            this.color = newColor;
        }
    }

    public static class Truck extends Car {

        public int loadCapacityInTon;
        public int numOfWheels = 18;

        public Truck(String color, int loadCapacityInTon) {
            // We must call parent constructor since it has argument
            super(color);

            this.loadCapacityInTon = loadCapacityInTon;
        }

        // Override method - notice we use a special "Annotation" to clarify code.
        @Override
        public String getDescription() {
            return "My truck color is " + this.color + " with " +
                    this.loadCapacityInTon + " tons capacity.";
        }

        public int getNumOfWheels() {
            return this.numOfWheels;
        }
    }
}
