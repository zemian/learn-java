package com.zemian.hellojava.web.controller.api;

import com.zemian.hellojava.data.dao.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Transactional
public class ApiTestController {
    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping(value = "/api/test/hello")
    public @ResponseBody
    ApiResult<List<Map<String, Object>>> latestBlog(Paging paging) {
        String sql = "SELECT * FROM information_schema.tables WHERE table_name like 'pg_%'";
        return ApiResult.result(jdbc.queryForList(sql));
    }
}
