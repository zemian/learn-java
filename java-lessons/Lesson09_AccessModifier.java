// Lesson 8
// Java classes can have modifier keywords when defining them. These will affect
// how other classes can access or inherit the members.
//
// Further study:
// - https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html
// - Review how package work again and see how access modifier affects it
// - Learn about "default" access modifier
public class Lesson09_AccessModifier {
    public static void main(String[] args) {
        Truck truck = new Truck("Red", 10_000);
        System.out.println(truck.getDescription());
        System.out.println(truck.getNumOfWheels());
    }

    public static class Car {
        private String color;

        public Car(String color) {
            this.color = color;
        }

        protected String getDescription() {
            return "My car color is " + this.color;
        }

        protected void changeColor(String newColor) {
            this.color = newColor;
        }

        public String getColor() {
            return color;
        }
    }

    public static class Truck extends Car {

        private int loadCapacityInTon;
        private int numOfWheels = 18;

        public Truck(String color, int loadCapacityInTon) {
            // We must call parent constructor since it has argument
            super(color);

            this.loadCapacityInTon = loadCapacityInTon;
        }

        // Override method - widen access modifier!
        public String getDescription() {
            return "My truck color is " + this.getColor() + " with " +
                    this.loadCapacityInTon + " tons capacity.";
        }

        public int getNumOfWheels() {
            return this.numOfWheels;
        }
    }
}
