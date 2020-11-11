package zemian.mysqlstarter;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class JdbcBenchmark {
    static String tableName = "JdbcBenchmark_test";
    static String sqlCreateTable = "create table if not exists " + tableName + "(\n" +
            "  id       serial primary key,\n" +
            "  ts       timestamp default current_timestamp,\n" +
            "  cat      varchar(10),\n" +
            "\n" +
            "  price    numeric(19,4),\n" +
            "  qty      int,\n" +
            "\n" +
            "  txtdata  text,\n" +
            "  bindata  blob,\n" +
            "\n" +
            "  distx    real,\n" +
            "  disty    double precision\n" +
            ")";
    static String sqlDropTable = "drop table " + tableName;

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    @State(Scope.Thread)
    public static class SelectVersionTest {
        Jdbc jdbc;

        @Setup
        public void setupDB() throws Exception {
            Properties jdbcProps = Jdbc.loadProperties("/jdbc.properties");
            Connection conn = DriverManager.getConnection(jdbcProps.getProperty("url"), jdbcProps);
            jdbc = new Jdbc(conn);
        }

        @TearDown
        public void cleanupDB() throws Exception {
            jdbc.getConn().close();
        }

        @Benchmark
        public void selectVersion() {
            jdbc.query("select version()");
        }
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    @State(Scope.Thread)
    public static class InsertTest {
        Jdbc jdbc;

        @Setup
        public void setupDB() throws Exception {
            Properties jdbcProps = Jdbc.loadProperties("/jdbc.properties");
            Connection conn = DriverManager.getConnection(jdbcProps.getProperty("url"), jdbcProps);
            jdbc = new Jdbc(conn);

            // Setup test table.
            jdbc.execute(sqlCreateTable);
        }

        @TearDown
        public void cleanupDB() throws Exception {
            jdbc.execute(sqlDropTable);
            jdbc.getConn().close();
        }

        @Benchmark
        public void insert() {
            jdbc.execute("insert into test(cat, price, qty) values ('test', 100000.10, 50000)");
        }
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    @State(Scope.Thread)
    public static class UpdateTest {
        Jdbc jdbc;

        @Setup
        public void setupDB() throws Exception {
            Properties jdbcProps = Jdbc.loadProperties("/jdbc.properties");
            Connection conn = DriverManager.getConnection(jdbcProps.getProperty("url"), jdbcProps);
            jdbc = new Jdbc(conn);

            // Setup test table.
            jdbc.execute(sqlCreateTable);

            // Insert data
            jdbc.getConn().setAutoCommit(false);
            for (int i = 0; i < 100_000; i++) {
                jdbc.execute("insert into test(cat, price, qty) values ('test', 100000.10, ?)", i);
                if (i % 1000 == 0)
                    jdbc.getConn().commit();
            }
            jdbc.getConn().setAutoCommit(true);
        }

        @TearDown
        public void cleanupDB() throws Exception {
            jdbc.execute(sqlDropTable);
            jdbc.getConn().close();
        }

        @Benchmark
        public void updateByPrimaryKey() {
            jdbc.execute("update test set price = ? where id = ?", 12345.12345, 500);
        }
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    @State(Scope.Thread)
    public static class DeleteTest {
        Jdbc jdbc;
        int indexCount = 0;

        @Setup
        public void setupDB() throws Exception {
            Properties jdbcProps = Jdbc.loadProperties("/jdbc.properties");
            Connection conn = DriverManager.getConnection(jdbcProps.getProperty("url"), jdbcProps);
            jdbc = new Jdbc(conn);

            // Setup test table.
            jdbc.execute(sqlCreateTable);

            // Insert data
            jdbc.getConn().setAutoCommit(false);
            for (int i = 0; i < 100_000; i++) {
                jdbc.execute("insert into test(cat, price, qty) values ('test', 100000.10, ?)", i);
                if (i % 1000 == 0)
                    jdbc.getConn().commit();
            }
            jdbc.getConn().setAutoCommit(true);
        }

        @TearDown
        public void cleanupDB() throws Exception {
            jdbc.execute(sqlDropTable);
            jdbc.getConn().close();
        }

        @Benchmark
        public void deleteByPrimaryKey() {
            jdbc.execute("delete from test where id = ?", (++indexCount));
        }
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    @State(Scope.Thread)
    public static class QueryTest {
        Jdbc jdbc;
        int indexCount = 0;

        @Setup
        public void setupDB() throws Exception {
            Properties jdbcProps = Jdbc.loadProperties("/jdbc.properties");
            Connection conn = DriverManager.getConnection(jdbcProps.getProperty("url"), jdbcProps);
            jdbc = new Jdbc(conn);

            // Setup test table.
            jdbc.execute(sqlCreateTable);

            // Insert data
            jdbc.getConn().setAutoCommit(false);
            for (int i = 0; i < 100_000; i++) {
                jdbc.execute("insert into test(cat, price, qty) values ('test', 100000.10, ?)", i);
                if (i % 1000 == 0)
                    jdbc.getConn().commit();
            }
            jdbc.getConn().setAutoCommit(true);
        }

        @TearDown
        public void cleanupDB() throws Exception {
            jdbc.execute(sqlDropTable);
            jdbc.getConn().close();
        }

        @Benchmark
        public void queryCount() {
            jdbc.query("select count(*) from test");
        }

        @Benchmark
        public void queryCountWithCondition() {
            jdbc.query("select count(*) from test where qty > ?", 50_000);
        }

        @Benchmark
        public void queryRecordByPrimaryKey() {
            jdbc.query("select * from test where id = ?", (++indexCount));
        }
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(JdbcBenchmark.class.getName())
                .build();
        new Runner(opt).run();
    }
}
