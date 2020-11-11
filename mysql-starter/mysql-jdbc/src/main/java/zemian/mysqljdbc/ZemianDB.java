package zemian.mysqljdbc;

import java.util.List;

public class ZemianDB {
    public static void main(String[] args) {
        Jdbc.withJdbc("/jdbc-zemiandb.properties", jdbc -> {
            List<Jdbc.Record> records = jdbc.query("select * from test");
            for (Jdbc.Record record : records) {
                System.out.println(record);
            }
            System.out.println("Total " + records.size() + " records found.");
        });
    }
}
