package com.zemian.hellojava;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Application utilities class.
 */
public class AppUtils {
    /*
    Load "application.properties" and "application-test.properties" from classpath if they exists.
     */
    public static Properties loadAppProps() {
        return loadProps("application.properties", "application-test.properties");
    }

    /*
    Create Properties and load content from classpath resources if it exists. The later resource will override
    the ones in front.
     */
    public static Properties loadProps(String ... resourceNames) {
        Properties props = new Properties();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        for (String resourceName : resourceNames) {
            try {
                try (InputStream stream = cl.getResourceAsStream(resourceName)) {
                    if (stream != null) {
                        props.load(stream);
                    }
                }
            } catch (IOException e) {
                throw new AppException("Failed to load: " + resourceName, e);
            }
        }
        return props;
    }
}
