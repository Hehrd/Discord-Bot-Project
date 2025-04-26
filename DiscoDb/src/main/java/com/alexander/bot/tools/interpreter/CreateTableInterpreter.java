package com.alexander.bot.tools.interpreter;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class CreateTableInterpreter extends SqlInterpeter {
    public CreateTableInterpreter() {
        super("CREATE TABLE");
    }

    @Override
    public String createSqlString(Map<String, String> options) {
        StringBuffer sql = new StringBuffer();
        sql.append(baseSql);
        appendTable(sql, options.get("name"));
        sql.append(" ()");
//        appendColumns(sql, options.get("columns"));
        sql.append(";");
        return sql.toString();
    }

    @Override
    protected void initDefaultOptions() {
        defaultOptions.put("columns_to_select", "*");
    }




    private void appendTable(StringBuffer sql, String table) {
        sql.append(" ");
        sql.append(table);
    }

    private void appendColumns(StringBuffer sql, String columns) {
        List<String> columnsList = Arrays.asList(columns.split(";"));
        sql.append(" ");
        sql.append("(");
        for (int i = 0; i < columnsList.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            List<String> columnProperties = Arrays.asList(columnsList.get(i).split(":"));

        }
    }
}
