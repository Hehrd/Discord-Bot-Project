package com.alexander.bot.tools;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class DropTableInterpreter extends SqlInterpeter {
    public DropTableInterpreter() {
        super("DROP TABLE");
    }

    @Override
    public String createSqlString(Map<String, String> options) {
        StringBuffer sql = new StringBuffer();
        sql.append(baseSql);
        appendTable(sql, options.get("name"));
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


}
