package com.zemian.hellojava.data.domain;

/**
 * A domain POJO mapped to DB table users.
 *
 * This class is generated by Zemian's CodeGen Toolbox on Jan 3, 2018.
 */
public class User {

    private String username; // key
    private String password;
    private String fullName;
    private boolean admin;
    private boolean deleted;
    private java.time.LocalDateTime createdDt;

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFullName() {
        return this.fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public boolean isAdmin() {
        return this.admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public boolean isDeleted() {
        return this.deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public java.time.LocalDateTime getCreatedDt() {
        return this.createdDt;
    }
    public void setCreatedDt(java.time.LocalDateTime createdDt) {
        this.createdDt = createdDt;
    }

    @Override
    public String toString() {
        StringBuilder fields = new StringBuilder();
        fields.append("username=" + getUsername());
        return "User{" + fields.toString() + "}";
    }

    public User() {
    }

    // == Customize

    public User(String username, String password) {
        this(username, password, false, "User " + username);
    }

    public User(String username, String password, boolean admin, String fullName) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.fullName = fullName;
    }
}