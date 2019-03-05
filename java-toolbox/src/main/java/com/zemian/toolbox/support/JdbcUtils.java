package com.zemian.toolbox.support;

import com.zemian.toolbox.AppException;
import com.zemian.toolbox.codegen.model.DbField;
import com.zemian.toolbox.codegen.model.DbRefField;
import com.zemian.toolbox.codegen.model.DbTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUtils {
    private static Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);

    public static String tableToClass(String tableName, boolean dropPlural) {
        String param = tableName;
        // Remove plurals "s" if there is any
        if (dropPlural && tableName.endsWith("s")) {
            param = tableName.substring(0, tableName.length() - 1);
        }
        return JavaUtils.upCase(JavaUtils.underscoreToCamel(param)).trim();
    }

    /** Get list of all column names. This method will not close the ResultSet! */
    public static List<String> getColumnNames(ResultSet rs) {
        List<String> ret = new ArrayList<>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int colCount = md.getColumnCount();
            for (int i = 0; i < colCount; i++) {
                int colIdx = i + 1;
                ret.add(md.getColumnName(colIdx));
            }
            return ret;
        } catch (SQLException e) {
            throw new AppException("Failed to inspect result set column names.", e);
        }
    }

    public static List<DbTable> getDbTable(String tablePattern, String url, String username, String password) throws Exception {
        List<DbTable> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tableRS = meta.getTables(null, null, tablePattern, null)) {
                while (tableRS.next()) {
                    String tableName = tableRS.getString("TABLE_NAME");
                    String tableType = tableRS.getString("TABLE_TYPE");
                    String tableCat = tableRS.getString("TABLE_CAT");
                    String tableSchem = tableRS.getString("TABLE_SCHEM");
                    DbTable dbTable = new DbTable(tableName, tableType);
                    dbTable.setTableCat(tableCat);
                    dbTable.setTableSchem(tableSchem);
                    result.add(dbTable);

                    // == Find columns
                    try (ResultSet rs = meta.getColumns(tableCat, tableSchem, tableName, null)) {
                        ResultSetMetaData md = rs.getMetaData();
                        int colCount = md.getColumnCount();
                        while (rs.next()) {
                            DbField field = new DbField(
                                    rs.getInt("DATA_TYPE"),
                                    rs.getString("COLUMN_NAME"),
                                    rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable
                            );

                            dbTable.getFields().add(field);

                            // Extra column fields
                            try {
                                field.setAutoincrement(rs.getBoolean("IS_AUTOINCREMENT"));
                                field.setColumnDef(rs.getString("COLUMN_DEF"));
                                field.setTypeName(rs.getString("TYPE_NAME"));
                                field.setColumnSize(rs.getInt("COLUMN_SIZE"));
                                field.setBufferLength(rs.getInt("BUFFER_LENGTH"));
                                field.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
                                field.setNumPrecRadix(rs.getInt("NUM_PREC_RADIX"));
                                field.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));

                                if (colCount >= 24) {
                                    // PostgresDB 9.6 does not support this column yet!
                                    field.setGeneratedcolumn(rs.getBoolean("IS_GENERATEDCOLUMN"));
                                }
                            } catch (Exception e) {
                                // Ignore.
                                LOG.debug("Failed to inspect table: " + tableName + " with columns.", e);
                            }

                            // == Find foreign reference fields
                            try (ResultSet rs2 = meta.getCrossReference(tableCat, tableSchem, tableName,
                                    null, null, null)) {
                                if (rs2.next()) {
                                    try {
                                        DbRefField refField = new DbRefField();
                                        field.getRefFields().add(refField);

                                        refField.setFkTableCat(rs2.getString("FKTABLE_CAT"));
                                        refField.setFkTableSchem(rs2.getString("FKTABLE_SCHEN"));
                                        refField.setFkTableName(rs2.getString("FKTABLE_NAME"));
                                        refField.setFkColumnName(rs2.getString("FKCOLUMN_NAME"));
                                        refField.setFkName(rs2.getString("FK_NAME"));
                                        refField.setKeySeq(rs2.getShort("KEY_SEQ"));
                                        refField.setDeleteRule(rs2.getShort("DELETE_RULE"));
                                        refField.setUpdateRule(rs2.getShort("UPDATE_RULE"));
                                    } catch (Exception e) {
                                        // Ignore.
                                        LOG.debug("Failed to inspect table: " + tableName + " with cross refs.", e);
                                    }
                                }
                            }
                        }
                    }

                    // == Find primary keys
                    try (ResultSet rs = meta.getPrimaryKeys(null, null, tableName)) {
                        // We only support one key columsn for now.
                        if (rs.next()) {
                            String key = rs.getString("COLUMN_NAME");
                            Optional<DbField> dbField = dbTable.getFields().stream().filter(f -> f.getColumnName().equals(key)).findFirst();
                            if (dbField.isPresent()) {
                                dbField.get().setKey(true);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static String sqlTypeToJava(int sqlType) {
        String ret = "Object";

        if (sqlType == Types.VARCHAR || sqlType == Types.CHAR || sqlType == Types.LONGVARCHAR) {
            ret = "String";
        } else if (sqlType == Types.NUMERIC || sqlType == Types.DECIMAL) {
            ret = "java.math.BigDecimal";
        } else if (sqlType == Types.NUMERIC || sqlType == Types.DECIMAL ||
                sqlType == Types.DOUBLE || sqlType == Types.FLOAT || sqlType == Types.REAL) {
            ret = "Double";
        } else if (sqlType == Types.INTEGER || sqlType == Types.SMALLINT || sqlType == Types.BIGINT) {
            ret = "Integer";
        } else if (sqlType == Types.BIGINT) {
            ret = "Long";
        } else if (sqlType == Types.BIT) {
            ret = "boolean";
        } else if (sqlType == Types.TIMESTAMP) {
            ret = "java.time.LocalDateTime";
        } else if (sqlType == Types.DATE) {
            ret = "java.time.LocalDate";
        } else if (sqlType == Types.TIME) {
            ret = "java.time.LocalTime";
        }

        return ret;
    }
}
