package com.alexander.bot.tools;

import java.util.HashMap;
import java.util.Map;

public abstract class SqlInterpeter {
    protected Map<String, String> defaultOptions;
    protected String baseSql;

    public SqlInterpeter(String baseSql) {
        defaultOptions = new HashMap<>();
        this.baseSql = baseSql;
        initDefaultOptions();
    }

    public abstract String createSqlString(Map<String, String> options);

    protected abstract void initDefaultOptions();
}
