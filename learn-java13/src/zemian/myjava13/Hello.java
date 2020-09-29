package zemian.myjava13;

import java.util.Arrays;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello");

        var a = "abcdefghijklmnopqrstuvwxyz".split("");
        System.out.println(Arrays.asList(a));
        Arrays.stream(a).forEach(System.out::println);
    }
}
