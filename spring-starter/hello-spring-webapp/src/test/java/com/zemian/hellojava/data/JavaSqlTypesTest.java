package com.zemian.hellojava.data;

import com.zemian.hellojava.AppException;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Map;
import java.util.TreeMap;

/*
== JDBC Type and their int constant value
-16=LONGNVARCHAR
-15=NCHAR
-9=NVARCHAR
-8=ROWID
-7=BIT
-6=TINYINT
-5=BIGINT
-4=LONGVARBINARY
-3=VARBINARY
-2=BINARY
-1=LONGVARCHAR
0=NULL
1=CHAR
2=NUMERIC
3=DECIMAL
4=INTEGER
5=SMALLINT
6=FLOAT
7=REAL
8=DOUBLE
12=VARCHAR
16=BOOLEAN
70=DATALINK
91=DATE
92=TIME
93=TIMESTAMP
1111=OTHER
2000=JAVA_OBJECT
2001=DISTINCT
2002=STRUCT
2003=ARRAY
2004=BLOB
2005=CLOB
2006=REF
2009=SQLXML
2011=NCLOB
2012=REF_CURSOR
2013=TIME_WITH_TIMEZONE
2014=TIMESTAMP_WITH_TIMEZONE

More about JDBC data type can be found here: https://docs.oracle.com/javase/tutorial/jdbc/basics/sqltypes.html

A quick table map of JDBC types are listed here:

Source: https://www.cis.upenn.edu/~bcpierce/courses/629/jdkdocs/guide/jdbc/getstart/mapping.doc.html

== JDBC Types Mapped to Java Types
JDBC type 	Java type
CHAR 	String
VARCHAR 	String
LONGVARCHAR 	String
NUMERIC 	java.math.BigDecimal
DECIMAL 	java.math.BigDecimal
BIT 	boolean
TINYINT 	byte
SMALLINT 	short
INTEGER 	int
BIGINT 	long
REAL 	float
FLOAT 	double
DOUBLE 	double
BINARY 	byte[]
VARBINARY 	byte[]
LONGVARBINARY 	byte[]
DATE 	java.sql.Date
TIME 	java.sql.Time
TIMESTAMP 	java.sql.Timestamp

== 8.6.2     Java Types Mapped to JDBC Types
Java Type 	JDBC type
String 	VARCHAR or LONGVARCHAR
java.math.BigDecimal 	NUMERIC
boolean 	BIT
byte 	TINYINT
short 	SMALLINT
int 	INTEGER
long 	BIGINT
float 	REAL
double 	DOUBLE
byte[] 	VARBINARY or LONGVARBINARY
java.sql.Date 	DATE
java.sql.Time 	TIME
java.sql.Timestamp 	TIMESTAMP

== JDBC Types Mapped to Java Object Types

JDBC Type 	Java Object Type
CHAR 	String
VARCHAR 	String
LONGVARCHAR 	String
NUMERIC 	java.math.BigDecimal
DECIMAL 	java.math.BigDecimal
BIT 	Boolean
TINYINT 	Integer
SMALLINT 	Integer
INTEGER 	Integer
BIGINT 	Long
REAL 	Float
FLOAT 	Double
DOUBLE 	Double
BINARY 	byte[]
VARBINARY 	byte[]
LONGVARBINARY 	byte[]
DATE 	java.sql.Date
TIME 	java.sql.Time
TIMESTAMP 	java.sql.Timestamp

 */
public class JavaSqlTypesTest {
    public Map<Integer, String> getSqlTypesNames() {
        try {
            Map<Integer, String> result = new TreeMap<>();
            for (Field field : Types.class.getFields()) {
                result.put((Integer) field.get(null), field.getName());
            }
            return result;
        } catch (Exception e) {
            throw new AppException("Failed to get all java.sql.Types names.", e);
        }
    }

    public Map<String, Integer> getSqlTypesValues() {
        try {
            Map<String, Integer> result = new TreeMap<>();
            for (Field field : Types.class.getFields()) {
                result.put(field.getName(), (Integer) field.get(null));
            }
            return result;
        } catch (Exception e) {
            throw new AppException("Failed to get all java.sql.Types values.", e);
        }
    }

    @Test
    public void listSqlTypesNames() {
        for (Map.Entry<Integer, String> entry : getSqlTypesNames().entrySet()) {
            System.out.println(entry);
        }
    }

    @Test
    public void listSqlTypesValues() {
        for (Map.Entry<String, Integer> entry : getSqlTypesValues().entrySet()) {
            System.out.println(entry);
        }
    }
}
