package zemian.mysqljdbc;

import java.util.List;

public class SakilaDB {
    public static void main(String[] args) {
        Jdbc.withJdbc("/jdbc-sakiladb.properties", jdbc -> {
            List<Jdbc.Record> records = jdbc.query("select * from film");
            for (Jdbc.Record record : records) {
                System.out.println(record);
            }
            System.out.println("Total " + records.size() + " records found.");

            Long count = jdbc.get("select count(*) from film");
            System.out.println("DB film count=" + count);
        });
    }
}
