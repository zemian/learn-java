package zemian.javastarter.fileio;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WriteTextFileTest {
    @Test
    public void writeFile() {
        WriteTextFile writer = new WriteTextFile();
        writer.writeFile("target/test.txt", "Hello");

        assertThat(new File("target/test.txt").exists(), is(true));
    }
}
