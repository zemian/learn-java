// Lesson 2
// Let's jump start with two most common usage of Collection types: List and Map
//
// Further study:
// - See more methods available for List at
//   https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ArrayList.html
// - See more methods available for Map at
//   https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashMap.html
// - Learn different syntax on creating List or Map:
//     List<Integer> list = new ArrayList<>();
//     Map<String, Integer> map = new HashMap<>();
// - Learn about immutable List.of(1, 2, 3)
// - Learn about immutable Map.of("foo", 77, "bar", 88)

// You need to import the collection type classes first
import java.util.ArrayList;
import java.util.HashMap;

public class Lesson02b_CollectionTypes {
    public static void main(String[] args) {
        listDemo();
        mapDemo();
    }

    public static void listDemo() {
        // List - Dynamic Array
        ArrayList<Integer> list = new ArrayList<>();

        // Add data
        list.add(7);
        list.add(8);
        list.add(9);
        System.out.println(list); // Notice that unlike array, list prints content!

        // Get data individually
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));

        // Count size
        System.out.println(list.size());

        // Remove data
        list.remove(0);
        System.out.println(list);

        // Update data
        list.set(0, 99);
        System.out.println(list);

        // Loop data
        for (Integer item : list) {
            System.out.println(item);
        }

        // Append large data
        for (int i = 0; i < 100_000; i++) {
            list.add(99 * i);
        }
        System.out.println(list.size());
        System.out.println(list.get(list.size() - 1)); // Get last item

        // Converting array to List
        Integer[] array = {1, 2, 3}; // Notice that you must use "Integer" and not "int"
        ArrayList<Integer> arrayList = new ArrayList(java.util.Arrays.asList(array));
        System.out.println(array);
        System.out.println(arrayList);

        // You can convert a list back to array
        list.add(0, 4);
        Integer[] arrayAgain = list.toArray(new Integer[0]); // Notice that you need to give empty array with type!
        System.out.println(arrayAgain[0]);

        // You can quickly print an array using List as a wrapper
        System.out.println(arrayAgain); // not readable
        System.out.println(java.util.Arrays.asList(arrayAgain)); // Now it's readable
    }

    public static void mapDemo() {
        // Map - Key Value entry dictionary
        HashMap<String, Integer> map = new HashMap<>();

        // Add data
        map.put("foo", 77);
        map.put("bar", 88);
        map.put("baz", 99);
        System.out.println(map);

        // Get data by key
        System.out.println(map.get("foo"));
        System.out.println(map.get("bar"));
        System.out.println(map.get("baz"));
        System.out.println(map.get("bad_key")); // Output: null

        // Check key exists
        System.out.println(map.containsKey("bar"));
        System.out.println(map.containsKey("bad_key"));

        // Count size
        System.out.println(map.size());

        // Remove data
        map.remove("foo");
        System.out.println(map);

        // Update data
        map.put("bar", 5555); // Just override the existing key value
        System.out.println(map);

        // Loop data
        for(String key : map.keySet()) {
            Integer value = map.get(key);
            System.out.println("Entry " + key + " => " + value);
        }

        // Append large data
        for (int i = 0; i < 100_000; i++) {
            map.put("mykey_" + i, 99 * i);
        }
        System.out.println(map.size());
        System.out.println(map.get("mykey_99999"));
    }
}
