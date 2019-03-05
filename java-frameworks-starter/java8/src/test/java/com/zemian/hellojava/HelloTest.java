package com.zemian.hellojava;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HelloTest {
	@Test
	public void testHello() {
		Hello a = new Hello();
		assertThat(a.getAppEnv(), is("TEST"));
		assertThat(a.getGreetingMessage(), is("Hello World"));
	}
}