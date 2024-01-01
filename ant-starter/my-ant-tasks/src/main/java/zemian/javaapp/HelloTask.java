package zemian.javaapp;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.condition.Condition;

import java.util.ArrayList;
import java.util.List;

public class HelloTask extends Task {
    private List<Condition> conditions = new ArrayList<>();
    public void add(Condition c) {
        conditions.add(c);
    }
    public void execute() {
        // iterator over the conditions
        System.out.println("Hello World");
    }
}
