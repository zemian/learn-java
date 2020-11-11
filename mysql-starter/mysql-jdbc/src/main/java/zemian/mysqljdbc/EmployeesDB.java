package zemian.mysqljdbc;

import java.util.ArrayList;
import java.util.List;

public class EmployeesDB {
    public static void main(String[] args) {
        countSalaries();
    }

    // Note: There are about 2,844,047 records in salaries table.
    private static void countSalaries() {
        Jdbc.withJdbc("/jdbc-employeesdb.properties", jdbc -> {
            // table size is too large to fit into single list
            // We will just count the records.
            List<Long> countWrapper = new ArrayList<>();
            countWrapper.add(0L);
            jdbc.query((rs, i) -> {
                countWrapper.set(0, countWrapper.get(0) + 1);
                return null;
            }, "select * from salaries");
            System.out.println("ResultSet count=" + countWrapper.get(0));


            Long count = jdbc.get("select count(*) from salaries");
            System.out.println("DB salaries count=" + count);
        });
    }

    private static void countEmployees() {
        Jdbc.withJdbc("/jdbc-employeesdb.properties", jdbc -> {
            List<Jdbc.Record> records = jdbc.query("select * from employees");
            for (Jdbc.Record record : records) {
                System.out.println(record);
            }
            System.out.println("Total " + records.size() + " records found.");

            Long count = jdbc.get("select count(*) from employees");
            System.out.println("DB employees count=" + count);
        });
    }
}
