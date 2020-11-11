package zemian.weldstarter;

import static org.junit.jupiter.api.Assertions.*;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

// See https://github.com/weld/weld-junit/tree/master/junit5
@ExtendWith(WeldJunit5Extension.class)
class HelloTest {
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.of(Hello.class);

    @Inject
    Hello hello;

    @Test
    public void getMessage() {
        assertEquals("Hello World", hello.getMessage());
    }
}