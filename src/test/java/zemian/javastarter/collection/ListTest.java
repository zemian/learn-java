package zemian.javastarter.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ListTest {
    @Test
    public void arrayList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        assertThat(list.size(), is(3));
        assertThat(list, hasItems("one", "two", "three"));
    }
}
