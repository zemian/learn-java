package com.zemian.hellojava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SettingDAO {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Setting> findAll() {
        String sql = "SELECT id, category, name, value FROM settings ORDER BY category, name";
        return jdbc.query(sql, (rs, rowNum) -> {
            Setting a = new Setting();
            a.setId(rs.getLong(1));
            a.setCategory(rs.getString(2));
            a.setName(rs.getString(3));
            a.setValue(rs.getString(4));
            return a;
        });
    }
}
