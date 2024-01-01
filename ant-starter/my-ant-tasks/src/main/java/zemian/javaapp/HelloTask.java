package zemian.javaapp;

import org.apache.tools.ant.Task;

public class HelloTask extends Task {
    public void execute() {
        System.out.println("Hello World");
    }
}
