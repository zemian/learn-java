package zemian.javastarter.datetime;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OldDateTest {
    @Test
    public void date() {
        Date now = new Date();
        assertThat(now.getTime(), greaterThan(0L));

        Date epoch = new Date(0);
        assertThat(epoch.getTime(), is(0L));
    }

    @Test
    public void parsingDate() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date d1 = df.parse("2017/11/14");
        assertThat(d1.getTime(), is(1510635600000L));
    }
}
