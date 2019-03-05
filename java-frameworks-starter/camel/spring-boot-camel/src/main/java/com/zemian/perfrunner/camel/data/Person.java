package com.zemian.perfrunner.camel.data;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = -4076625802514267885L;

    private String firstName;
    private String lastName;
    private int age;
    private Person spouse;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public String toRecord() {
        String spouseStr = (spouse == null ? "" :
                spouse.getFirstName() + "," + spouse.getLastName() + "," + spouse.getAge());
        return firstName + "," + lastName + "," + age + "|" + spouseStr;
    }

    public static Person fromRecord(String line) {
        String[] parts = StringUtils.split(line, "|");
        String[] pParts = StringUtils.split(parts[0], ",");
        Person p = new Person(pParts[0], pParts[1]);
        if (pParts.length == 3)
            p.setAge(Integer.parseInt(pParts[2]));

        if (parts.length == 2 && StringUtils.isNotEmpty(parts[1])) {
            String[] sParts = StringUtils.split(parts[1], ",");
            Person spouse = new Person(sParts[0], sParts[1]);
            if (sParts.length == 3)
                spouse.setAge(Integer.parseInt(sParts[2]));

            p.setSpouse(spouse);
        }
        return p;
    }
}
