package zemian.javastarter.serviceloader;

import java.util.Iterator;
import java.util.ServiceLoader;

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
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ServiceLoader<MyService> loader = ServiceLoader.load(MyService.class, cl);
        Iterator<MyService> iter = loader.iterator();
        if (!iter.hasNext())
            return;
        MyService provider = iter.next();
        System.out.println(provider);
    }
}
