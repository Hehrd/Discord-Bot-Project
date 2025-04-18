package com.alexander.bot.tools;

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

    private void appendColumns(StringBuffer sql, List<String> columns) {
        sql.append(" ");
        sql.append("(");
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            List<String> columnProperties = Arrays.asList(columns.get(i).split(":"));

        }
    }
}
