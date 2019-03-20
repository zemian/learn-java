package com.zemian.hellojava.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class MapTest {
    @Test
    public void hashMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 111);
        map.put("two", 222);
        map.put("three", 333);
        assertThat(map.size(), is(3));
        assertThat(map, hasEntry("one", 111));
        assertThat(map, hasEntry("two", 222));
        assertThat(map, hasEntry("three", 333));
        assertThat(map, not(hasEntry("google", 10E100)));
    }
}
