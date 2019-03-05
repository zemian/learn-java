package com.zemian.hellojava.jdbc;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MoneySample {
    public static void main(String[] args) {
//        compareDoubleLostValues();

        String url = "jdbc:postgresql://localhost:5432/zemian";
        String user = "zemian";
        String password = "";
        Jdbc.runJdbc(url, user, password, jdbc-> {
            //testMoneyNumeric2(jdbc);
            //testMoneyFloat2(jdbc);

//            compareMoneyFloat(jdbc);
//            testMoneyNumeric3(jdbc);

            myMoney(jdbc);
        });
    }

    private static void myMoney(Jdbc jdbc) {
        String name = "test" + System.currentTimeMillis();
        for (double i = 10_000_000.00; i < 10_000_010.00; i+= 0.10) {
            System.out.println(name + " inserting " + i);
            jdbc.execute("insert into my_money(name, amount) values(?, ?)",
                    name, i);
        }
        System.out.println("name=" + name);
    }

    private static void testMoneyNumeric3(Jdbc jdbc) {
        String name = "test3" + System.currentTimeMillis();
        jdbc.execute("insert into test_money_numeric(name, amount) values(?, ?)",
                name, 0.10 + 0.20 + 0.01 + 0.02);

//        for (int i = 0; i < 100; i++) {
//            jdbc.execute("update test_money_numeric set amount = amount + ? where name = ?",
//                    0.00999776460230350494384765625, name);
//        }

        //0.00999776460230350494384765625

        System.out.println("name = " + name);

//        double amountDouble = jdbc.get("select amount from test_money_numeric where name = ? ", name);
//        System.out.println("amount (double) = " + amountDouble);

        BigDecimal amount = jdbc.get("select amount from test_money_numeric where name = ? ", name);
        System.out.println("amount (BigDecimal) = " + amount);
    }

    private static void compareDoubleLostValues() {
        double sum = 10_000_000.00;
        double inc = 0.01;
        double max = sum + 100.00;
        System.out.println("Testing double " + inc + " increment from " + sum +
                " up to " + new DecimalFormat("#,###.##").format(max));

        while (sum < max)
            sum += inc;
        System.out.println("Sum stopped at " + sum);

        double diff = Math.abs(max - sum);
        // Use BigDec to print plain dec number, not scientif notation
        System.out.println("Double precision lost: " + new BigDecimal(diff).toPlainString());

        String diffStr = new BigDecimal(diff).toPlainString();
        for (int i = 0; i < diffStr.length(); i++) {
            if (diffStr.charAt(i + 2) != '0') {
                System.out.println("  that's " + (i) + " zeros decimal places");
                break;
            }
        }
    }

    private static void compareMoneyFloat(Jdbc jdbc) {
        double amount = jdbc.get("select amount from test_money_float where id = ?", 146324);
        double compare = 999.99;
        System.out.println(amount + " cmp " + compare + " " + cmp(amount, compare));
        compare = 999.98;
        System.out.println(amount + " cmp " + compare + " " + cmp(amount, compare));
        compare = 1000.00;
        System.out.println(amount + " cmp " + compare + " " + cmp(amount, compare));
    }

    private static int cmp(double d1, double d2) {
        double precision = 0.00001;
        double diff = Math.abs(d1 - d2);
        if (diff <= precision) {
            return 0;
        } else {
            return d1 > d2 ? 1 : -1;
        }
    }

    static int INSERT_SIZE = 1_000_00;
    static int PRINT_SIZE = 1_00_00;

    private static void testMoneyNumeric2(Jdbc jdbc) {
        String name = "num_" + System.currentTimeMillis();
        BigDecimal sum = new BigDecimal("0.00");
        for (int i = 0; i < INSERT_SIZE; i++) {
            Jdbc.Record rec = new Jdbc.Record("name", name, "amount", sum);
            jdbc.insert("test_money_numeric", rec);

            if (i % PRINT_SIZE == 0)
                System.out.println("inserted numeric " + rec);

            sum = sum.add(BigDecimal.valueOf(0.01));
        }
    }

    private static void testMoneyFloat2(Jdbc jdbc) {
        String name = "num_" + System.currentTimeMillis();
        double sum = 0.00;
        for (int i = 0; i < INSERT_SIZE; i++) {
            Jdbc.Record rec = new Jdbc.Record("name", name, "amount", sum);
            jdbc.insert("test_money_float", rec);

            if (i % PRINT_SIZE == 0)
                System.out.println("inserted float " + rec);

            sum = sum + 0.01;
        }
    }

    private static void testMoneyNumeric(Jdbc jdbc) {
        String name = "num_" + System.currentTimeMillis();
        Jdbc.Record rec = new Jdbc.Record("name", name, "amount", BigDecimal.valueOf(0.00));
        jdbc.insert("test_money_numeric", rec);
        System.out.println("inserted numeric " + rec);

        for (int i = 0; i < INSERT_SIZE; i++) {
            BigDecimal amount = (BigDecimal) rec.get("amount");
            amount = amount.add(new BigDecimal("0.01"));
            rec = jdbc.query("select name, amount from test_money_numeric where id = ?", rec.get("id")).get(0);
            rec.put("amount", amount);
            jdbc.insert("test_money_numeric", rec);

            if (i % PRINT_SIZE == 0)
                System.out.println("inserted numeric " + rec);

        }
    }
    
    private static void testMoneyFloat(Jdbc jdbc) {
        String name = "float_" + System.currentTimeMillis();
        Jdbc.Record rec = new Jdbc.Record("name", name, "amount", 0.00);
        jdbc.insert("test_money_float", rec);
        System.out.println("inserted float " + rec);

        for (int i = 0; i < INSERT_SIZE; i++) {
            rec = jdbc.query("select name, amount from test_money_float where id = ?", rec.get("id")).get(0);
            rec.put("amount", (Double) rec.get("amount") + 0.01);
            jdbc.insert("test_money_float", rec);

            if (i % PRINT_SIZE == 0)
                System.out.println("inserted float " + rec);
        }
    }
}
