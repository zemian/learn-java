package zemian.weldstarter;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** This style seems to be more simplified! */
@EnableAutoWeld
@AddBeanClasses(Hello.class)
class Hello2Test {
    @Inject
    Hello hello;

    @Test
    public void getMessage() {
        assertEquals("Hello World", hello.getMessage());
    }
}