package com.zemian.hellojava.jdk;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamTest {
    @Test
    public void increaseList() {
        List<String> list = Arrays.asList("a b c", "d e f");
        List<String> list2 = list.stream().flatMap(e -> Arrays.stream(e.split(" "))).collect(Collectors.toList());
        System.out.println(list2);
    }

    @Test
    public void increaseSet() {
        List<String> list = Arrays.asList("a b c", "d e f a");
        Set<String> set = list.stream().flatMap(e -> Arrays.stream(e.split(" "))).collect(Collectors.toSet());
        System.out.println(set);
    }
}
