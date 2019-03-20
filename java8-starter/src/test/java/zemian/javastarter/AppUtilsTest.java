package zemian.javastarter;

import org.junit.Test;
import zemian.javastarter.AppUtils;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AppUtilsTest {
    @Test
    public void testProps() {
        Properties p = AppUtils.loadAppProps();
        assertThat(p.getProperty("app.env"), is("TEST"));
        assertThat(p.getProperty("app.hello.message"), is("Hello World"));
    }
}
