package com.zemian.hellojava.app;

import com.zemian.hellojava.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A spring server to load any Spring Java Config class configuration. Once started the server's main thread will be
 * blocked and wait for stop() signal. User may use `CTRL+C` keys to shutdown the server gracefully.
 *
 * This server will only bootstrap the Spring container. In order to provide any real work for the server, users
 * need to provide their own application beans in the Java config classes as argument to be loaded. It's up to
 * user to configure and provide actual beans for the server functionality.
 *
 * Usage:
 *   ServerMain [SpringJavaConfig ...]
 *
 * The one or more [SpringJavaConfig] arguments is the class name for any Spring Java Config classes.
 *
 * @Author Zemian Deng 2017
 */
public class SpringServer {

	public static void main(String[] args) {
		if (args.length < 1) {
			throw new AppException("Missing args: [SpringJavaConfig ...]");
		}

		Class<?>[] springConfigs = new Class<?>[args.length];
		try {
			for (int i = 0; i < args.length; i++) {
				springConfigs[i] = Class.forName(args[i]);
			}
		} catch (ClassNotFoundException e) {
			throw new AppException("Failed to convert args into Spring Java Config class.", e);
		}

		new SpringServer().start(springConfigs);
	}

	private static Logger LOG = LoggerFactory.getLogger(SpringServer.class);
	private AtomicBoolean started = new AtomicBoolean(false);
	private Object waitLock = new Object();
	private ConfigurableApplicationContext spring;

	public ConfigurableApplicationContext getSpring() {
		return spring;
	}

	public void start(Class<?> ... springConfigs) {
		if (started.get()) {
			throw new AppException("SpringServer is already running: " + spring);
		}

		try {
			LOG.info("Starting SpringServer with config: {}", Arrays.asList(springConfigs));
			started.set(true);

			spring = new AnnotationConfigApplicationContext(springConfigs);

			// Register shutdown hook to stop server
			Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));

			// Place server in wait mode
			while (started.get()) {
				LOG.info("SpringServer is running, placing main thread into wait mode.");
				synchronized (waitLock) {
					waitLock.wait();
				}
			}

			// Done
			LOG.info("SpringServer is ready.", this);
		} catch (Exception e) {
			throw new AppException("Failed to run SpringServer.", e);
		}
	}

	public void stop() {
		if (started.get()) {
			LOG.info("Stopping SpringServer.");
			try {
				spring.close();
				spring = null;
				LOG.info("Spring stopped.");
			} catch (Exception e) {
				LOG.error("Failed to stop SpringServer.", e);
			}
			started.set(false);
			synchronized (waitLock) {
				waitLock.notify();
			}
		}
	}

	public boolean isStarted() {
		return started.get();
	}
}
