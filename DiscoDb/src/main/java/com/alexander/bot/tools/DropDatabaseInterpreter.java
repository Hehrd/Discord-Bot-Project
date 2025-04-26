package com.alexander.bot.tools;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DropDatabaseInterpreter extends SqlInterpeter{
    public DropDatabaseInterpreter() {
        super("DROP DATABASE");
    }

    @Override
    public String createSqlString(Map<String, String> options) {
        StringBuffer sql = new StringBuffer();
        sql.append(baseSql);
        appendName(sql, options.get("name"));
        sql.append(";");
        return sql.toString();
    }

    @Override
    protected void initDefaultOptions() {

    }

    private void appendName(StringBuffer sql, String name) {
        sql.append(" ");
        sql.append(name);
    }
}
