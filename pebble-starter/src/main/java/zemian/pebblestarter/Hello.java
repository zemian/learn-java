package zemian.pebblestarter;

import com.mitchellbosecke.pebble.*;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mitchellbosecke.pebble.template.*;
import java.util.*;
import java.io.*;

public class Hello {
	public static void main(String[] args) throws Exception {
		FileLoader loader = new FileLoader();
		loader.setPrefix("src/main/webapp/WEB-INF/pebble");
		loader.setSuffix(".html");
        PebbleEngine engine = new PebbleEngine.Builder().loader(loader).build();
		PebbleTemplate compiledTemplate = engine.getTemplate("home");

		Map<String, Object> context = new HashMap<>();
		context.put("name", "Zemian");

		Writer writer = new StringWriter();
		compiledTemplate.evaluate(writer, context);

		String output = writer.toString();
		System.out.println(output);
	}
}