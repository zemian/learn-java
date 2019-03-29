package com.zemian.hellojava;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/*
A base class for running SpringBoot Tests. We will load CommonConfig and override with `application-test.properties`
for testing purpose.

Subclass can add additional Spring config by using `@ContextConfiguration`
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public abstract class BaseSpringTest {
}
