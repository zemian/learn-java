package com.zemian.hellojava.app;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = Hello.Config.class)
public class HelloTest extends SpringTestBase {
	@Autowired
	private Hello hello;

	@Test
	public void testGreetingMessage() {
		assertThat(hello.getAppEnv(), is("TEST"));
		assertThat(hello.getGreetingMessage(), is("Hello World"));
	}
}