package zemian.mysqljdbc;

import java.util.List;

public class WorldDB {
    public static void main(String[] args) {
        Jdbc.withJdbc("/jdbc-worlddb.properties", jdbc -> {
            List<Jdbc.Record> records = jdbc.query("select * from country");
            for (Jdbc.Record record : records) {
                System.out.println(record);
            }
            System.out.println("Total " + records.size() + " records found.");
        });
    }
}
