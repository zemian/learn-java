package zemian.myava.jdbc.postgres;

import java.sql.Connection;

/**
 * Created by xbbj5x6 on 11/1/2016.
 */
public class CallFunctions {
    public static void main(String[] args) throws Exception {
        Database.withConnection((Connection conn) ->
                System.out.println(conn)
        );
    }
}
