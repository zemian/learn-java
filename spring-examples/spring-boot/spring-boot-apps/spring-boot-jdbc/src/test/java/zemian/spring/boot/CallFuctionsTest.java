package zemian.spring.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xbbj5x6 on 11/1/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CallFuctionsTest {
    private static Logger LOG = LoggerFactory.getLogger(CallFuctionsTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void callFunctionWithNamedParam() {
        LOG.info("Using {}", jdbcTemplate);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("increment");
        LOG.info("Using {}", jdbcCall);
        Map<String, Object> input = new HashMap<>();
        input.put("i", 100);
        Map<String, Object> result = jdbcCall.execute(input);
        LOG.info("Function result: {}", result);
    }

    @Test
    public void callFunctionWithOutputParam() {
        LOG.info("Using {}", jdbcTemplate);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_em");
        LOG.info("Using {}", jdbcCall);
        Map<String, Object> input = new HashMap<>();
        input.put("x", 20);
        input.put("y", 30);
        Map<String, Object> result = jdbcCall.execute(input);
        LOG.info("Function result: {}", result);
    }

    @Test
    public void callFunctionWithOutputParamExplicit() {
        LOG.info("Using {}", jdbcTemplate);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_em")
                .declareParameters(new SqlParameter("x", Types.INTEGER))
                .declareParameters(new SqlParameter("y", Types.INTEGER))
                .declareParameters(new SqlOutParameter("sum", Types.INTEGER));
        LOG.info("Using {}", jdbcCall);
        Map<String, Object> result = jdbcCall.execute(21, 34);
        LOG.info("Function result: {}", result);
    }

    @Test
    public void callFunctionWithParamNames() {
        LOG.info("Using {}", jdbcTemplate);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add");
        LOG.info("Using {}", jdbcCall);
        Map<String, Object> result = jdbcCall.execute(2, 3);
        LOG.info("Function result: {}", result);
    }
}
