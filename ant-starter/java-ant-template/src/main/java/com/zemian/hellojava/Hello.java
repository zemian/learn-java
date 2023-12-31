package com.zemian.hellojava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * A hello world demo.
 */
public class Hello {
	private static final Logger LOG = LoggerFactory.getLogger(Hello.class);
	private Properties props = AppUtils.loadAppProps();
	public String getAppEnv() {
		return props.getProperty("app.env");
	}
	public String getGreetingMessage() {
		return props.getProperty("app.hello.message");
	}

	public static void main(String[] args) {
		Hello a = new Hello();
		LOG.info(a.getAppEnv() + ": " + a.getGreetingMessage());
	}
}