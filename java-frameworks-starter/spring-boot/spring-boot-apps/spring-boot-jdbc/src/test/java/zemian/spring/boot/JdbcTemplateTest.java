package zemian.spring.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by xbbj5x6 on 11/1/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTemplateTest {
    private static Logger LOG = LoggerFactory.getLogger(CallFuctionsTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void querySettingsTable() {
        LOG.info("Using {}", jdbcTemplate);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from settings");
        for (Map<String, Object> row : list) {
            LOG.info("Query row: {}", row);
        }
    }
}
