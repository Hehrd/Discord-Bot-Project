package com.alexander.bot.tools;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SelectInterpreter extends SqlInterpeter{

    public SelectInterpreter() {
        super("SELECT");
    }

    public String createSqlString(Map<String, String> options) {
        StringBuffer sql = new StringBuffer();
        sql.append(baseSql);
        appendColumnsToSelect(options.get("columns_to_select"), sql);
        appendTable(options.get("master_table"), sql);
//        appendJoins(options.get("master_table"), options.get("joins"),  sql);
        appendWhereClause(options.get("fields_and_values"), sql);
        appendLimit(options.get("limit"), sql);
        sql.append(";");
        return sql.toString();
    }

    private void appendColumnsToSelect(String columns, StringBuffer sql) {
        sql.append(" ");
        if (columns.equals("")) {
            sql.append(defaultOptions.get("columns_to_select"));
            return;
        }
        sql.append(" ");
        List<String> columnsList = Arrays.asList(columns.split(";"));
        for (int i = 0; i < columnsList.size(); i++) {
            sql.append(columnsList.get(i));
            if (i != columnsList.size() - 1) {
                sql.append(", ");
            }
        }
    }

    private void appendTable(String table, StringBuffer sql) {
        sql.append(" ");
        sql.append(String.format("FROM %s", table));
    }

    private void appendJoins(String masterTable, String joins, StringBuffer sql) {
        sql.append(" ");
        List<String> joinsList = Arrays.asList(joins.split(";"));
        for (String join : joinsList) {
            List<String> joinComponents = Arrays.asList(join.split(":"));
            String joinTable = joinComponents.get(0);
            String columnInJoinTable = joinComponents.get(1);
            String columnInMasterTable = joinComponents.get(2);
            sql.append(" ");
            sql.append(String.format("JOIN %s ON %s.%s = %s.%s",
                    joinTable,
                    joinTable,
                    columnInJoinTable,
                    masterTable,
                    columnInMasterTable));
        }
    }

    private void appendWhereClause(String fieldsAndValues, StringBuffer sql) {
        sql.append(" ");
        sql.append("WHERE");
        List<String> fieldsAndValuesList = Arrays.asList(fieldsAndValues.split(";"));
        for (int i = 0; i < fieldsAndValuesList.size(); i++) {
            sql.append(" ");
            if (i > 0) {
                sql.append(" ");
                sql.append("AND");
                sql.append(" ");
            }
            sql.append(String.format("%s", fieldsAndValuesList.get(i)));
        }
    }

    private void appendLimit(String limit, StringBuffer sql) {
        sql.append(" ");
        sql.append(String.format("LIMIT %s", limit));
    }

    protected void initDefaultOptions() {
        defaultOptions.put("columns_to_select", "*");
    }

}
