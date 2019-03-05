package com.zemian.hellojava.spring.app;

import com.zemian.hellojava.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A spring server, or a runnable bean invoker.
 *
 * Server Mode Usage:
 *   AppMain [SpringJavaConfig]
 *
 * Runnable Bean Invoker Usage:
 *   AppMain [SpringJavaConfig] [RunnableBeanName]
 *
 * When running as server mode, the main thread will be block and wait for user to shutdown with CTRL+C. The
 * [SpringJavaConfig] argument is a Spring Java Config class that contains all the application beans. It default
 * to `{@link AppMainConfig}` if not given, which will do nothing. It will not auto scan component as default!
 * User should write their own Java Config that provide a meaningful set of application beans.
 *
 * If [RunnableBeanName] is given, it will execute the given bean as {@link Runnable} and then exit the program
 * immediately. This will make the program act as a command line utility instead of a server.
 */
public class AppMain {

	public static void main(String[] args) throws Exception {
		new AppMain().run(args);
	}

	private static Logger LOG = LoggerFactory.getLogger(AppMain.class);
	private AtomicBoolean running = new AtomicBoolean(true);
	private Object waitLock = new Object();
	private ConfigurableApplicationContext spring;

	public void run(String[] args) {
		try {
			Class<?> springConfig = AppMainConfig.class;
			String runnableBeanName = null;

			if (args.length >= 1)
				springConfig = Class.forName(args[0]);
			if (args.length >= 2)
				runnableBeanName = args[1];

			LOG.info("Starting Spring with config: {}", springConfig);
			spring = new AnnotationConfigApplicationContext(springConfig);

			// Check for runnable bean
			if (runnableBeanName != null) {
				LOG.info("Searching for RunnableBean: {}", runnableBeanName);
				Runnable bean = spring.getBean(runnableBeanName, Runnable.class);
				LOG.info("Found and invoking RunnableBean: {}", bean);
				bean.run();
				LOG.info("Runnable Bean {} Completed.", bean);

				stopMain();
			} else {
				// Register shutdown hook to stop server
				Runtime.getRuntime().addShutdownHook(new Thread(() -> stopMain()));

				// Place server in wait mode
				while (running.get()) {
					LOG.info("Spring is running, placing main thread into wait mode.");
					synchronized (waitLock) {
						waitLock.wait();
					}
				}
			}

			// Done
			LOG.info("AppMain is done.");
		} catch (Exception e) {
			throw new AppException("Failed to run Spring.", e);
		}
	}

	public void stopMain() {
		LOG.info("Stopping Spring.");
		try {
			spring.close();
			LOG.info("Spring stopped.");
		} catch (Exception e) {
			LOG.error("Failed to stop Spring.", e);
		}
		running.set(false);
		synchronized (waitLock) {
			waitLock.notify();
		}
	}

	public boolean isRunning() {
		return running.get();
	}
}
