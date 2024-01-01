package zemian.javaapp;

public class Hello {
    public static void main(String[] args) {
        System.out.println(new Hello().greet("World"));
    }

    public String greet(String name) {
        return "Hello " + name;
    }
}
