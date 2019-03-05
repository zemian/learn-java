package com.zemian.hellojava;

import java.time.LocalDateTime;

public class AuditLog {
    private LocalDateTime createdDt = LocalDateTime.now();
    private String name;
    private String value;

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "createdDt=" + createdDt +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
