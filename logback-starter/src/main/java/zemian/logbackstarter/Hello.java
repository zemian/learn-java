package zemian.logbackstarter;

import org.slf4j.*;

public class Hello {
	private static Logger log = LoggerFactory.getLogger(Hello.class);

	public static void main(String[] args) {
		log.info(new Hello().getMessage());
	}

	public String getMessage() {
		return "Hello World";
	}
}
