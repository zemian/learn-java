package com.zemian.hellojava;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/*
This base test class provide a Spring runner ready junit test. Note it loads the generic empty
SpringTestConfig, and it should not perform auto component scan. Each test should
add their own Config on their own test.

NOTE: The `application-test.properties` must be loaded here in order to override props in
the main `application.properties` file!
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
@TestPropertySource("classpath:/application-test.properties")
public abstract class BaseSpringTest {
}
