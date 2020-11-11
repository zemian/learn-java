package zemian.mysqljdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

public class PagingResultSet {
    public static void main(String[] args) {
        // How to tell MySQL driver to fetch row-by-row (we can only give hint)
        // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-implementation-notes.html
        Jdbc.withJdbc("/jdbc-employeesdb.properties", jdbc -> {
            jdbc.withConnNoRet(conn -> {
                String sql = "select * from salaries";
                try (PreparedStatement stmt = conn.prepareStatement(sql, TYPE_FORWARD_ONLY, CONCUR_READ_ONLY)) {
                    stmt.setFetchSize(Integer.MIN_VALUE);
                    stmt.executeQuery();
                    long count = 0L;
                    try (ResultSet rs = stmt.getResultSet()) {
                        while (rs.next()) {
                            count++;
                        }
                    }
                    System.out.println("Count = " + count);
                }
            });
        });

        /*
        NOTE:

        Normally, the ResultSet.next() will iterate and fetch all records as first RS. There is no need
        to invoke "stmt.getMoreResults()" even when we set fetch size to small value.

        The "stmt.getMoreResults()" is normally used when "execute()" return multiple ResultSet. You normally
        would check it like this:

        ((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1))

         */
    }
}
