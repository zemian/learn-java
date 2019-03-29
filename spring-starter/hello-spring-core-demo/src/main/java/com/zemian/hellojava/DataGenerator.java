package com.zemian.hellojava;

public class DataGenerator {
    private String name;

    public DataGenerator() {
    }

    public DataGenerator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hello createHello(String name) {
        Hello ret = new Hello();
        ret.setName(name);
        return ret;
    }

    public AuditLog createAuditLog(String name, String value) {
        AuditLog ret = new AuditLog();
        ret.setName(name);
        ret.setValue(value);
        return ret;
    }

    @Override
    public String toString() {
        return "DataGenerator{" +
                "name='" + name + '\'' +
                '}';
    }
}
