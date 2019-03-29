package com.zemian.hellojava.data;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@ContextConfiguration(classes = CommonDataConfig.class)
public class TimestampTest extends SpringTestBase {
    @Autowired
    private JdbcTemplate jdbc;

    @Test
    public void dbTimezone() {
        // NOTES:
        //
        // JDBC has separation with DATE, TIME and DATE_TIME types: java.sql.Date, java.sql.Time and java.sql.Timestamp.
        // All of these does not track timezone information!
        // The java.sql.Timestamp is similar to java.util.Date with extra more nano seconds information field.
        //
        // Note the java.util.Date only holds up to milliseconds precision! So if your domain object is typed
        // with java.util.Date, and you use to map to your dabase column which typed a java.sql.Timestamp, then
        // you may lose the nano seconds precision. If you are using java8, you may consider using LocalDateTime
        // as domain type instead.
        //
        // Postgres calls DATETIME objects TIMESTAMP.
        //
        // Postgres can store TIMESTAMP without (default) timezone or with it.
        //
        // TIMESTAP WITHOUT TIMEZONE (TIMEsTAMP) / default
        //   Postgres store datetime objects in DB system os local timezone. If client sends datetime other than
        //   this timezone it will auto convert and then store. When client request datatime, it will always return
        //   as local timezone of the DB system.
        //
        // TIMESTAP WITH TIMEZONE (TIMEsTAMPTZ)
        //   Postgres stores datetime objects in UTC timezone. If client sends datetime other than UTC, it will auto
        //   convert and then store. When client request datatime, it will auto convert to what client requests
        //   the timezone to be (client's timezone senstive).
        //

//        // Insert a current local timezone of Epoch datetime into DB
//        Date dt = new Date(0);
//        String sql = "INSERT INTO test_timestamps(name, created_dt, created_dt_tz) VALUES(?, ?, ?)";
//        int ret = jdbc.update(sql, "Date(0)", dt, dt);
//        System.out.println("Result = " + ret);

//        // Insert a specific timezone of datetime into DB
//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"));
//        cal.set(1970, 0, 1, 0, 0, 0);
//        Date pstDt = cal.getTime();
//        String sql = "INSERT INTO test_timestamps(name, created_dt, created_dt_tz) VALUES(?, ?, ?)";
//        int ret = jdbc.update(sql, "Date(0) with PST", pstDt, pstDt);
//        System.out.println("Result = " + ret);

        // Get date and display
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String sql = "SELECT created_dt, created_dt_tz FROM test_timestamps WHERE name = ?";
        Map<String, Object> map = jdbc.queryForMap(sql, "Date(0) with PST");
        Date createdDt = (Date)map.get("created_dt");
        // Display INCORRECT value
        System.out.println("Result createdDt with UTC timezone formatter=" + df.format(createdDt));
        // Display CORRECT value
        System.out.println("Result createdDt with local timezone formatter=" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz").format(createdDt));

        Date createdDtTz = (Date)map.get("created_dt_tz");
        // Display CORRECT value
        System.out.println("Result createdDtTz with UTC timezone formatter=" + df.format(createdDtTz));
        // Display CORRECT value
        System.out.println("Result createdDtTz with local timezone formatter=" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz").format(createdDtTz));
    }
}