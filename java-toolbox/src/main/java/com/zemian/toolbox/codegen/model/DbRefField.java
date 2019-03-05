package com.zemian.toolbox.codegen.model;

/**
 * Represents a foreign DB column field

 PKTABLE_CAT String => parent key table catalog (may be null)
 PKTABLE_SCHEM String => parent key table schema (may be null)
 PKTABLE_NAME String => parent key table name
 PKCOLUMN_NAME String => parent key column name
 FKTABLE_CAT String => foreign key table catalog (may be null) being exported (may be null)
 FKTABLE_SCHEM String => foreign key table schema (may be null) being exported (may be null)
 FKTABLE_NAME String => foreign key table name being exported
 FKCOLUMN_NAME String => foreign key column name being exported
 KEY_SEQ short => sequence number within foreign key( a value of 1 represents the first column of the foreign key, a value of 2 would represent the second column within the foreign key).
 UPDATE_RULE short => What happens to foreign key when parent key is updated:
 importedNoAction - do not allow update of parent key if it has been imported
 importedKeyCascade - change imported key to agree with parent key update
 importedKeySetNull - change imported key to NULL if its parent key has been updated
 importedKeySetDefault - change imported key to default values if its parent key has been updated
 importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility)
 DELETE_RULE short => What happens to the foreign key when parent key is deleted.
 importedKeyNoAction - do not allow delete of parent key if it has been imported
 importedKeyCascade - delete rows that import a deleted key
 importedKeySetNull - change imported key to NULL if its primary key has been deleted
 importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility)
 importedKeySetDefault - change imported key to default if its parent key has been deleted
 FK_NAME String => foreign key name (may be null)
 PK_NAME String => parent key name (may be null)
 DEFERRABILITY short => can the evaluation of foreign key constraints be deferred until commit
 importedKeyInitiallyDeferred - see SQL92 for definition
 importedKeyInitiallyImmediate - see SQL92 for definition
 importedKeyNotDeferrable - see SQL92 for definition

 */
public class DbRefField {
    private String fkTableCat;
    private String fkTableSchem;
    private String fkTableName;
    private String fkColumnName;
    private String fkName;
    private int keySeq;
    private int updateRule;
    private int deleteRule;

    public String getFkTableCat() {
        return fkTableCat;
    }

    public void setFkTableCat(String fkTableCat) {
        this.fkTableCat = fkTableCat;
    }

    public String getFkTableSchem() {
        return fkTableSchem;
    }

    public void setFkTableSchem(String fkTableSchem) {
        this.fkTableSchem = fkTableSchem;
    }

    public String getFkTableName() {
        return fkTableName;
    }

    public void setFkTableName(String fkTableName) {
        this.fkTableName = fkTableName;
    }

    public String getFkColumnName() {
        return fkColumnName;
    }

    public void setFkColumnName(String fkColumnName) {
        this.fkColumnName = fkColumnName;
    }

    public String getFkName() {
        return fkName;
    }

    public void setFkName(String fkName) {
        this.fkName = fkName;
    }

    public int getKeySeq() {
        return keySeq;
    }

    public void setKeySeq(int keySeq) {
        this.keySeq = keySeq;
    }

    public int getUpdateRule() {
        return updateRule;
    }

    public void setUpdateRule(int updateRule) {
        this.updateRule = updateRule;
    }

    public int getDeleteRule() {
        return deleteRule;
    }

    public void setDeleteRule(int deleteRule) {
        this.deleteRule = deleteRule;
    }
}
