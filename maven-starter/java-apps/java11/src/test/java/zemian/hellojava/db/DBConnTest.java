package zemian.hellojava.db;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("db")
class DBConnTest {
    @Test
    void withConn() {
        DBConn.withConn(conn -> assertNotNull(conn));
    }
}