package com.zemian.hellojava.jdbc;

import java.util.List;

/**
 * DataAccess for Setting entity.
 */
public class SettingDAO {
    private Jdbc jdbc;

    public SettingDAO(Jdbc jdbc) {
        this.jdbc = jdbc;
    }

    public List<Setting> findAll() {
        String sql = "SELECT id, category, name, value FROM settings ORDER BY category, name";
        return jdbc.query((rs, i) -> {
            Setting a = new Setting();
            a.setId(rs.getLong(1));
            a.setCategory(rs.getString(2));
            a.setName(rs.getString(3));
            a.setValue(rs.getString(4));
            return a;
        }, sql);
    }
}
