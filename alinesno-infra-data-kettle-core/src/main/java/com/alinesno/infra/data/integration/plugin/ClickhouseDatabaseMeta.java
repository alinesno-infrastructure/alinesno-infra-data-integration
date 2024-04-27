package com.alinesno.infra.data.integration.plugin;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.SqlScriptParser;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.util.Utils;
import java.sql.ResultSet;

/**
 * 【kettle连接clickhouse数据库插件】
 *
 * @author paul
 * @date 2024年3月10日
 */

@DatabaseMetaPlugin(
        type = "Clickhouse",
        typeDescription = "Clickhouse"
)
public class ClickhouseDatabaseMeta extends BaseDatabaseMeta implements DatabaseInterface {
    private static final String STRICT_BIGNUMBER_INTERPRETATION = "STRICT_NUMBER_38_INTERPRETATION";

    public ClickhouseDatabaseMeta() {
    }

    public int[] getAccessTypeList() {
        return new int[]{0, 4};
    }

    public int getDefaultDatabasePort() {
        return this.getAccessType() == 0 ? 8123 : -1;
    }

    public boolean supportsAutoInc() {
        return false;
    }

    public String getLimitClause(int nrRows) {
        return " WHERE ROWNUM <= " + nrRows;
    }

    public String getSQLQueryFields(String tableName) {
        return "SELECT * FROM " + tableName + " WHERE 1=0";
    }

    public String getSQLTableExists(String tablename) {
        return this.getSQLQueryFields(tablename);
    }

    public String getSQLColumnExists(String columnname, String tablename) {
        return this.getSQLQueryColumnFields(columnname, tablename);
    }

    public String getSQLQueryColumnFields(String columnname, String tableName) {
        return "SELECT " + columnname + " FROM " + tableName + " WHERE 1=0";
    }

    public boolean needsToLockAllTables() {
        return false;
    }

    public String getDriverClass() {
        return this.getAccessType() == 1 ? "sun.jdbc.odbc.JdbcOdbcDriver" : "ru.yandex.clickhouse.ClickHouseDriver";
    }

    public String getURL(String hostname, String port, String databaseName) throws KettleDatabaseException {
        if (this.getAccessType() == 1) {
            return "jdbc:odbc:" + databaseName;
        } else if (this.getAccessType() == 0) {
            String _hostname = hostname;
            String _port = port;
            String _databaseName = databaseName;
            String _SocketTimeOut = "?socket_timeout=600000";
            if (Utils.isEmpty(hostname)) {
                _hostname = "localhost";
            }

            if (Utils.isEmpty(port) || port.equals("-1")) {
                _port = "";
            }

            if (Utils.isEmpty(databaseName)) {
                throw new KettleDatabaseException("必须指定数据库名称");
            } else {
                if (!databaseName.startsWith("/")) {
                    _databaseName = "/" + databaseName;
                }

                return "jdbc:clickhouse://" + _hostname + (Utils.isEmpty(_port) ? "" : ":" + _port) + _databaseName + _SocketTimeOut;
            }
        } else {
            throw new KettleDatabaseException("不支持的数据库连接方式[" + this.getAccessType() + "]");
        }
    }

    public boolean supportsOptionsInURL() {
        return false;
    }

    public boolean supportsSequences() {
        return true;
    }

    public String getSQLSequenceExists(String sequenceName) {
        int dotPos = sequenceName.indexOf(46);
        String sql = "";
        if (dotPos == -1) {
            sql = "SELECT * FROM USER_SEQUENCES WHERE SEQUENCE_NAME = '" + sequenceName.toUpperCase() + "'";
        } else {
            String schemaName = sequenceName.substring(0, dotPos);
            String seqName = sequenceName.substring(dotPos + 1);
            sql = "SELECT * FROM ALL_SEQUENCES WHERE SEQUENCE_NAME = '" + seqName.toUpperCase() + "' AND SEQUENCE_OWNER = '" + schemaName.toUpperCase() + "'";
        }

        return sql;
    }

    public String getSQLCurrentSequenceValue(String sequenceName) {
        return "SELECT " + sequenceName + ".currval FROM DUAL";
    }

    public String getSQLNextSequenceValue(String sequenceName) {
        return "SELECT " + sequenceName + ".nextval FROM dual";
    }

    public boolean supportsSequenceNoMaxValueOption() {
        return true;
    }

    public boolean useSchemaNameForTableList() {
        return true;
    }

    public boolean supportsSynonyms() {
        return true;
    }

    public String getAddColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon) {
        return "ALTER TABLE " + tablename + " ADD " + this.getFieldDefinition(v, tk, pk, use_autoinc, true, false);
    }

    public String getDropColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon) {
        return "ALTER TABLE " + tablename + " DROP COLUMN " + v.getName() + Const.CR;
    }

    public String getModifyColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon) {
        ValueMetaInterface tmpColumn = v.clone();
        String tmpName = v.getName();
        boolean isQuoted = tmpName.startsWith("\"") && tmpName.endsWith("\"");
        if (isQuoted) {
            tmpName = tmpName.substring(1, tmpName.length() - 1);
        }

        int threeoh = tmpName.length() >= 30 ? 30 : tmpName.length();
        tmpName = tmpName.substring(0, threeoh);
        tmpName = tmpName + "_KTL";
        if (isQuoted) {
            tmpName = "\"" + tmpName + "\"";
        }

        tmpColumn.setName(tmpName);
        String sql = "";
        sql = sql + this.getAddColumnStatement(tablename, tmpColumn, tk, use_autoinc, pk, semicolon) + ";" + Const.CR;
        sql = sql + "UPDATE " + tablename + " SET " + tmpColumn.getName() + "=" + v.getName() + ";" + Const.CR;
        sql = sql + this.getDropColumnStatement(tablename, v, tk, use_autoinc, pk, semicolon) + ";" + Const.CR;
        sql = sql + this.getAddColumnStatement(tablename, v, tk, use_autoinc, pk, semicolon) + ";" + Const.CR;
        sql = sql + "UPDATE " + tablename + " SET " + v.getName() + "=" + tmpColumn.getName() + ";" + Const.CR;
        sql = sql + this.getDropColumnStatement(tablename, tmpColumn, tk, use_autoinc, pk, semicolon);
        return sql;
    }

    public String getFieldDefinition(ValueMetaInterface v, String tk, String pk, boolean use_autoinc, boolean add_fieldname, boolean add_cr) {
        StringBuilder retval = new StringBuilder(128);
        String fieldname = v.getName();
        int length = v.getLength();
        int precision = v.getPrecision();
        if (add_fieldname) {
            retval.append(fieldname).append(" ");
        }

        int type = v.getType();
        switch(type) {
            case 1:
            case 5:
            case 6:
                if (!fieldname.equalsIgnoreCase(tk) && !fieldname.equalsIgnoreCase(pk)) {
                    if (length > 0) {
                        if (precision <= 0 && length <= 18) {
                            if (precision == 0) {
                                if (length > 9) {
                                    retval.append("BIGINT");
                                } else if (length < 5) {
                                    retval.append("SMALLINT");
                                } else {
                                    retval.append("INT");
                                }
                            } else {
                                retval.append("FLOAT(53)");
                            }
                        } else {
                            retval.append("NUMERIC(").append(length + precision).append(", ").append(precision).append(")");
                        }
                    } else {
                        retval.append("DOUBLE PRECISION");
                    }
                } else {
                    retval.append("BIGSERIAL");
                }
                break;
            case 2:
                if (length >= 1 && length < 9999999) {
                    retval.append("VARCHAR(").append(length).append(")");
                } else {
                    retval.append("TEXT");
                }
                break;
            case 3:
            case 9:
                retval.append("TIMESTAMP");
                break;
            case 4:
                if (this.supportsBooleanDataType()) {
                    retval.append("BOOLEAN");
                } else {
                    retval.append("CHAR(1)");
                }
                break;
            case 7:
            default:
                retval.append(" UNKNOWN");
                break;
            case 8:
                retval.append("BLOB");
        }

        if (add_cr) {
            retval.append(Const.CR);
        }

        return retval.toString();
    }

    public String[] getReservedWords() {
        return new String[]{"ALIAS", "AND", "AS", "AT", "BEGIN", "BETWEEN", "BIGINT", "BIT", "BY", "BOOLEAN", "BOTH", "CALL", "CASE", "CAST", "CHAR", "CHARACTER", "COMMIT", "CONSTANT", "CURSOR", "COALESCE", "CONTINUE", "CONVERT", "CURRENT_DATE", "CURRENT_TIMESTAMP", "CURRENT_USER", "DATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DECODE", "DELETE", "ELSE", "ELSIF", "END", "EXCEPTION", "EXECUTE", "EXIT", "EXTRACT", "FALSE", "FETCH", "FLOAT", "FOR", "FROM", "FUNCTION", "GOTO", "IF", "IN", "INT", "INTO", "IS", "INTEGER", "IMMEDIATE", "INDEX", "INOUT", "INSERT", "LEADING", "LIKE", "LIMIT", "LOCALTIME", "LOCALTIMESTAMP", "LOOP", "NCHAR", "NEXT", "NOCOPY", "NOT", "NULLIF", "NULL", "NUMBER", "NUMERIC", "OPTION", "OF", "OR", "OUT", "OVERLAY", "PERFORM", "POSITION", "PRAGMA", "PROCEDURE", "QUERY", "RAISE", "RECORD", "RENAME", "RETURN", "REVERSE", "ROLLBACK", "REAL", "SELECT", "SAVEPOINT", "SETOF", "SMALLINT", "SUBSTRING", "SQL", "SYSDATE", "SESSION_USER", "THEN", "TO", "TYPE", "TABLE", "TIME", "TIMESTAMP", "TINYINT", "TRAILING", "TREAT", "TRIM", "TRUE", "TYPE", "UID", "UPDATE", "USER", "USING", "VARCHAR", "VARCHAR2", "VALUES", "WITH", "WHEN", "WHILE", "LEVEL"};
    }

    public String getSQLListOfProcedures() {
        return "show tables";
    }

    public String getSQLLockTables(String[] tableNames) {
        StringBuilder sql = new StringBuilder(128);

        for(int i = 0; i < tableNames.length; ++i) {
            sql.append("LOCK TABLE ").append(tableNames[i]).append(" IN EXCLUSIVE MODE;").append(Const.CR);
        }

        return sql.toString();
    }

    public String getSQLUnlockTables(String[] tableNames) {
        return null;
    }

    public String getExtraOptionsHelpText() {
        return "http://www.shentongdata.com/?bid=3&eid=249";
    }

    public String[] getUsedLibraries() {
        return new String[]{"clickhouseJDBC.jar", "clickhouseJDBC14.jar", "clickhouseJDBC16.jar"};
    }

    public boolean checkIndexExists(Database database, String schemaName, String tableName, String[] idx_fields) throws KettleDatabaseException {
        String tablename = database.getDatabaseMeta().getQuotedSchemaTableCombination(schemaName, tableName);
        boolean[] exists = new boolean[idx_fields.length];

        for(int i = 0; i < exists.length; ++i) {
            exists[i] = false;
        }

        try {
            String sql = "SELECT * FROM USER_IND_COLUMNS WHERE TABLE_NAME = '" + tableName + "'";
            ResultSet res = null;

            boolean all;
            try {
                res = database.openQuery(sql);
                if (res == null) {
                    all = false;
                    return all;
                }

                for(Object[] row = database.getRow(res); row != null; row = database.getRow(res)) {
                    String column = database.getReturnRowMeta().getString(row, "COLUMN_NAME", "");
                    int idx = Const.indexOfString(column, idx_fields);
                    if (idx >= 0) {
                        exists[idx] = true;
                    }
                }
            } finally {
                if (res != null) {
                    database.closeQuery(res);
                }

            }

            all = true;

            for(int i = 0; i < exists.length && all; ++i) {
                if (!exists[i]) {
                    all = false;
                }
            }

            return all;
        } catch (Exception var16) {
            throw new KettleDatabaseException("Unable to determine if indexes exists on table [" + tablename + "]", var16);
        }
    }

    public boolean requiresCreateTablePrimaryKeyAppend() {
        return true;
    }

    public boolean supportsPreparedStatementMetadataRetrieval() {
        return false;
    }

    public int getMaxColumnsInIndex() {
        return 32;
    }

    public String getSQLListOfSequences() {
        return "SELECT SEQUENCE_NAME FROM all_sequences";
    }

    public String quoteSQLString(String string) {
        string = string.replaceAll("'", "''");
        string = string.replaceAll("\\n", "'||chr(13)||'");
        string = string.replaceAll("\\r", "'||chr(10)||'");
        return "'" + string + "'";
    }

    public boolean releaseSavepoint() {
        return false;
    }

    public boolean supportsErrorHandlingOnBatchUpdates() {
        return false;
    }

    public boolean supportsRepository() {
        return true;
    }

    public int getMaxVARCHARLength() {
        return 2000;
    }

    public String getDropTableIfExistsStatement(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    public SqlScriptParser createSqlScriptParser() {
        return new SqlScriptParser(false);
    }

    public boolean strictBigNumberInterpretation() {
        return "Y".equalsIgnoreCase(this.getAttributes().getProperty("STRICT_NUMBER_38_INTERPRETATION", "N"));
    }

    public void setStrictBigNumberInterpretation(boolean strictBigNumberInterpretation) {
        this.getAttributes().setProperty("STRICT_NUMBER_38_INTERPRETATION", strictBigNumberInterpretation ? "Y" : "N");
    }
}
