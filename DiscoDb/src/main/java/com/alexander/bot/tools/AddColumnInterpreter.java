package com.alexander.bot.tools;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class AddColumnInterpreter extends SqlInterpeter{

    public AddColumnInterpreter() {
        super("ALTER TABLE");
    }

    @Override
    public String createSqlString(Map<String, String> options) {
        StringBuffer sql = new StringBuffer();
        sql.append(baseSql);
        appendTable(sql, options.get("table"));
        appendColumn(sql,
                options.get("name"),
                options.get("type"),
                Boolean.parseBoolean(options.get("isNullable")),
                options.get("default"));
        sql.append(";");
        return sql.toString();
    }

    @Override
    protected void initDefaultOptions() {

    }

    private void appendTable(StringBuffer sql, String tableName) {
        sql.append(" ");
        sql.append(tableName);
    }
    private void appendColumn(StringBuffer sql, String name,
                              String type,
                              boolean isNullable,
                              String defaultValue) {
        sql.append(" ");
        sql.append("ADD COLUMN");
        sql.append(" ");
        appendColumnName(sql, name);
        appendColumnType(sql, type);
        appendColumnDefault(sql, defaultValue);
        appendColumnIsNullable(sql, isNullable);
    }
    private void appendColumnName(StringBuffer sql, String name) {
        sql.append(" ");
        sql.append(name);
    }
    private void appendColumnType(StringBuffer sql, String type) {
        sql.append(" ");
        sql.append(type);
    }
    private void appendColumnDefault(StringBuffer sql, String defaultValue) {
        sql.append(" ");
        sql.append("DEFAULT");
        sql.append(" ");
        sql.append(defaultValue);
    }
    private void appendColumnIsNullable(StringBuffer sql, boolean isNullable) {
        if (!isNullable) {
            sql.append(" ");
            sql.append("NOT NULL");
        }
    }
}
