package zemian.javastarter.serviceloader;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/*
A demo to see how ServiceLoader works.

Setup: META-INF/services/<ServiceInterfaceName> => single line of impl class

NOTE: Service implementation must have a default no-arg constructor.

See https://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html
 */
public class MyServiceDemo {
    public static void main(String[] args) {
        main2(args);
    }

    public static void main1(String[] args) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ServiceLoader<MyService> loader = ServiceLoader.load(MyService.class, cl);
        for (MyService myService : loader) {
            System.out.println(myService);
        }
    }

    public static void main2(String[] args) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            ServiceLoader<MyService> loader = ServiceLoader.load(MyService.class, cl);
            Iterator<MyService> iter = loader.iterator();
            if (!iter.hasNext())
                return;
            MyService provider = iter.next();
            System.out.println(provider);
        } catch (ServiceConfigurationError ignore) {
            // How do we debug "ignore" if third party lib choose to do this?
            // Answer: In IDEA, place breakpoint before exception, then open
            // breakpoint view, and enable "Any Exception", then step through.
        }
    }
}
