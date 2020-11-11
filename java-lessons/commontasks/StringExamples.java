import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class StringExamples {
    public static void main(String[] args) {
        // How to do some common string operation

        // Split string
        String input = "foo bar baz";
        String[] words = input.split(" ");
        System.out.println(Arrays.asList(words));

        // Join strings
        String[] words2 = {"foo", "bar", "baz"};
        String wordString = String.join(", ", Arrays.asList(words2));
        System.out.println(wordString);

        // File name verification
        String file = "test.txt";
        System.out.println(file.endsWith(".txt")); // check ext
        System.out.println(file.startsWith("test")); // check basename
        System.out.println(file.substring(0, file.lastIndexOf("."))); // basename
        System.out.println(file.substring(5)); // ext

        // Print all letters
        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print(c);
            System.out.print(' ');
        }
        System.out.println();

        // Generate random string that's 8 chars long
        for (int i = 0; i < 3; i++) {
            String rand = UUID.randomUUID().toString();
            rand = rand.replaceAll("-", "");
            rand = rand.substring(0, 8);
            System.out.println(rand);
        }

        // Capitalize first letter
        String name = "hello";
        String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
        System.out.println(capName);

        // Title phrase
        String phrase = "let's learn java together";
        System.out.println(title(phrase));
    }

    public static String title(String input) {
        ArrayList<String> result = new ArrayList<>();
        String[] words = input.split(" ");
        for (String word : words) {
            String capWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            result.add(capWord);
        }
        return String.join(" ", result);
    }
}
