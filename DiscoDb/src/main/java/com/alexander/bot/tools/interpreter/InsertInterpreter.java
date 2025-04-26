package com.alexander.bot.tools.interpreter;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class InsertInterpreter extends SqlInterpeter {
    public InsertInterpreter() {
        super("INSERT INTO");
    }

    @Override
    public String createSqlString(Map<String, String> options) {
        StringBuffer sql = new StringBuffer();
        sql.append(baseSql);
        sql.append(" ");
        appendTable(sql, options.get("table"));
        appendColumns(sql, options.get("columns"));
        appendValues(sql, options.get("values"));
        sql.append(";");
        return sql.toString();
    }

    @Override
    protected void initDefaultOptions() {

    }

    private void appendTable(StringBuffer sql, String table) {
        sql.append(" ");
        sql.append(table);
    }

    private void appendColumns(StringBuffer sql, String columns) {
        sql.append(" ");
        sql.append("(");
        List<String> columnList = Arrays.asList(columns.split(";"));
        for (int i = 0; i < columnList.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(columnList.get(i));
        }
        sql.append(")");
    }
    private void appendValues(StringBuffer sql, String values) {
        sql.append(" ");
        sql.append("VALUES(");
        List<String> valuesList = Arrays.asList(values.split(";"));
        for (int i = 0; i < valuesList.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(valuesList.get(i));
        }
        sql.append(")");
    }
}
