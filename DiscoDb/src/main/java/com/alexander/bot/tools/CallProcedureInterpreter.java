package com.alexander.bot.tools;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class CallProcedureInterpreter extends SqlInterpeter {

    public CallProcedureInterpreter() {
        super("CALL");
    }

    @Override
    public String createSqlString(Map<String, String> options) {
        StringBuffer sql = new StringBuffer();
        sql.append(baseSql);
        appendName(sql, options.get("name"));
        appendParams(sql, options.get("params"));
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
    private void appendParams(StringBuffer sql, String params) {
        List<String> paramsList = Arrays.asList(params.split(";"));
        sql.append(" (");
        for (int i = 0; i < paramsList.size(); i++) {
            if (i > 0) {
                sql.append(",");
            }
            sql.append(paramsList.get(i));
        }
        sql.append(")");
    }
}
