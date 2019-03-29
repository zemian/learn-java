package com.zemian.hellojava.data.support;

import com.zemian.hellojava.AppException;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Work with direct JDBC java.sql.Connection object obtained from the DriverManager using
 * url, username and password given. This allow you to have full control of Connection and
 * no middle layer of any obstruction in between to work with vendor DB connection.
 *
 * @Author Zemian Deng 2017
 */
public class DirectConnService {
    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static interface ConnCallback<T> {
        T withConn(Connection conn) throws Exception;
    }

    public <T> T execute(ConnCallback<T> callback) {
        T result = null;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            result = callback.withConn(conn);
        } catch (Exception e) {
            throw new AppException("Failed to execute DB connection callback.", e);
        }
        return result;
    }
}
