package com.zemian.toolbox.codegen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a DB table from DatabaseMetaData.getTables()
 *
 TABLE_CAT String => table catalog (may be null)
 TABLE_SCHEM String => table schema (may be null)
 TABLE_NAME String => table name
 TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
 REMARKS String => explanatory comment on the table
 TYPE_CAT String => the types catalog (may be null)
 TYPE_SCHEM String => the types schema (may be null)
 TYPE_NAME String => type name (may be null)
 SELF_REFERENCING_COL_NAME String => name of the designated "identifier" column of a typed table (may be null)
 REF_GENERATION String => specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null)

 */
public class DbTable {
    private String tableCat;
    private String tableSchem;
    private String tableName;
    private String tableType;
    private List<DbField> fields = new ArrayList<>();

    public DbTable() {
    }

    public DbTable(String tableName, String tableType) {
        this.tableName = tableName;
        this.tableType = tableType;
    }

    public String getTableName() {
        return tableName;
    }

    public List<DbField> getFields() {
        return fields;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public void setFields(List<DbField> fields) {
        this.fields = fields;
    }

    public String getTableCat() {
        return tableCat;
    }

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }
}
