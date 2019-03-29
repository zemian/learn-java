package com.zemian.hellojava.app;

import com.zemian.hellojava.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(classes = Hello.class)
public class HelloTest extends BaseSpringTest {
	@Autowired
	private Hello hello;

	@Test
	public void testGreetingMessage() {
		assertThat(hello.getAppEnv(), is("TEST"));
		assertThat(hello.getGreetingMessage(), is("Hello World"));
	}

	@Value("${app.env}")
	private String envName;

	@Value("${app.hello.message}")
	private String helloMessage;

	@Test
	public void test() {
		assertThat(envName, is("TEST"));
		assertThat(helloMessage, is("Hello World"));
	}
}