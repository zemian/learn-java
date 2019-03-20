package zemian.javastarter.fileio;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileLineProcessorTest {
    @Test
    public void eachLine() throws Exception {
        WriteTextFile writer = new WriteTextFile();
        writer.writeFile("target/test.txt", "one\ntwo\nthree");

        List<String> lines = new ArrayList<>();
        FileLineProcessor flp = new FileLineProcessor();
        flp.eachLine("target/test.txt", line -> lines.add(line));
        assertThat(lines.size(), is(3));
        assertThat(lines, hasItems("one", "two", "three"));

        // Now checkout Files.lines()
    }

}