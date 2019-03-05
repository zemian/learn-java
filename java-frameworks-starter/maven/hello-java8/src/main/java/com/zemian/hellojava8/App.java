package com.zemian.hellojava8;

import java.util.*;
import java.util.stream.*;

public class App {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("Hello");
		list.add("World");
		String msg = list.stream()
			.map(Object::toString)
            .collect(Collectors.joining(" "));
		System.out.println(msg + "!");
	}
}
