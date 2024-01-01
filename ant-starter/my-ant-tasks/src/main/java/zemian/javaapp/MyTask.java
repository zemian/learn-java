package zemian.javaapp;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.condition.Condition;

import java.util.ArrayList;
import java.util.List;

public class MyTask extends Task {
    private List<Condition> conditions = new ArrayList<>();
    public void add(Condition c) {
        conditions.add(c);
    }
    public void execute() {
        // iterator over the conditions
        for (Condition condition : conditions) {
            System.out.println("Found condition " + condition);
        }
    }
}
