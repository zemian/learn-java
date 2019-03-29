package com.zemian.hellojava.spring.hello;

import com.zemian.hellojava.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = HelloConfig.class)
public class HelloServiceTest extends BaseSpringTest {
	@Autowired
	private HelloService helloService;

	@Test
	public void testGreetingMessage() {
		assertThat(helloService.getAppEnv(), is("TEST"));
		assertThat(helloService.getGreetingMessage(), is("Hello World"));
	}
}