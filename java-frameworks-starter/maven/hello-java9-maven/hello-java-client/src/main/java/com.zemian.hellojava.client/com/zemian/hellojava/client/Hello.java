package com.zemian.hellojava.client;

import com.zemian.hellojava.lib.*;

public class Hello {
	public static void main(String[] args) {
		HelloService service = new HelloService();
		System.out.println(service.getGreetingMessage());
	}
}