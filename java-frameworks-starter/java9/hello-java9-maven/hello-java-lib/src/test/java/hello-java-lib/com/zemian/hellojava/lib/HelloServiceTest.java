package com.zemian.hellojava.lib;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HelloServiceTest {
	@Test
	public void testJavaPrecision() {
		HelloService service = new HelloService();
		assertThat(service.getGreetingMessage(), is("Hello World"));
	}
}