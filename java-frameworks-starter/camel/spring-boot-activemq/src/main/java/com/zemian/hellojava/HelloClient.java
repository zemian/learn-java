package com.zemian.hellojava;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
public class HelloClient {
	private static final Logger LOG = getLogger(HelloClient.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext spring = SpringApplication.run(HelloClient.class, args);
		HelloClient bean = spring.getBean(HelloClient.class);
		bean.run();
		spring.close();
	}

	@Autowired
	private JmsTemplate jms;

	public String getGreetingMessage() {
		return "Hello World";
	}

	public void run() {
		LOG.debug("Sending msg using jmsTemplate={}", jms);
		String queueName = HelloListener.TEST_QUEUE;
		jms.convertAndSend(queueName, getGreetingMessage());
		LOG.info("Msg sent to {}", queueName);
	}
}