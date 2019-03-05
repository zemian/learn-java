package com.zemian.hellojava.logback;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.Configurator;
import com.zemian.hellojava.AppException;
import com.zemian.hellojava.support.AppUtils;
import org.springframework.core.io.Resource;

/**
 * Logback will automatically look for a classpath:/META-INF/services/ch.qos.logback.classic.spi.Configurator
 * file and if you add this class name into this file, it will look for "logback.xml" file under our
 * own application namespace in the classpath. If no "logback.xml"  file is found, it will
 * default to "BasicConfigurator".
 *
 * This custom configuration loader is created so we can consolidate all properties and related files under
 * our application namespace in classpath for good practice purpose.
 *
 * @Author Zemian Deng 2017
 */
public class ExtClasspathConfigurator extends JoranConfigurator implements Configurator {
    @Override
    public void configure(LoggerContext loggerContext) {
        // Get logback.xml from the application's namespace in classpath.
        //
        // Example: (default)
        //  classpath:/<appname>/logback.xml Or classpath:/<appname>/<env>/logback-qa.xml.
        Resource resource = AppUtils.getEnvResource("logback.xml");
        try {
            if (resource != null) {
                doConfigure(resource.getURL());
            } else {
                new BasicConfigurator().configure(loggerContext);
            }
        } catch (Exception e) {
            throw new AppException("Failed to load logback config " + resource.getFilename(), e);
        }
    }
}
