package com.zemian.hellojava.jdk;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

public class JavaNewDateTest {
    @Test
    public void parseDateTime() {
        try {
            // NOTE: You can't use LocalDateTime parse without specifying full date and time portion!
            LocalDateTime dt = LocalDateTime.parse("2017", DateTimeFormatter.ofPattern("yyyy"));
            System.out.println(dt);
        } catch (DateTimeParseException e) {
            // Expected.
        }

        try {
            // NOTE: Even this is still wrong
            LocalDateTime dt = LocalDateTime.parse("2017-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println(dt);
        } catch (DateTimeParseException e) {
            // Expected.
        }


        // NOTE: This is okay
        LocalDateTime dt = LocalDateTime.parse("2017-01-01T00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        System.out.println(dt);
    }

    @Test
    public void parseDateOnly() {
        LocalDate dt = LocalDate.parse("2017-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(dt);
    }

    @Test
    public void parseYearOnly() {
        try {
            // NOTE: You can't use LocalDate parse just the year. It's strict and needs the MM-DD part!
            LocalDate dt = LocalDate.parse("2017", DateTimeFormatter.ofPattern("yyyy"));
            System.out.println(dt);
        } catch (DateTimeParseException e) {
            // Expected.
        }

        // Alternatives 1:
        {
            TemporalAccessor year = DateTimeFormatter.ofPattern("yyyy").parse("2017");
            LocalDate dt = LocalDate.ofYearDay(Year.from(year).getValue(), 1);
            System.out.println(dt);
        }

        // Alternatives 2:
        {
            LocalDate dt = LocalDate.parse("2017" + "-01-01");
            System.out.println(dt);
        }

        // Alternatives 3:
        {
            Year year = Year.parse("2017");
            System.out.println(year);
        }

        // Other readings:
        // https://stackoverflow.com/questions/46199611/how-to-parse-only-4-digit-years

    }

    @Test
    public void conversion() {
        LocalDateTime dt1 = LocalDateTime.parse("2017-11-01T00:00");
        System.out.println("dt1=" + dt1);
        System.out.println(dt1.toLocalDate());
        System.out.println(dt1.toLocalTime());

        LocalDate dtB1 = LocalDate.parse("2017-11-01");
        System.out.println("dtB1=" + dtB1);
        LocalDateTime dtB2 = dtB1.atTime(13,30);
        System.out.println(dtB2);

        LocalTime dtC1 = LocalTime.parse("23:05");
        System.out.println("dtC1=" + dtC1);
        LocalDateTime dtC2 = dtC1.atDate(dtB1);
        System.out.println(dtC2);
    }
}
