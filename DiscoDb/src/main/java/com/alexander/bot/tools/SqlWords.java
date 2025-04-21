package com.alexander.bot.tools;

public enum SqlWords {
    AND("AND"),
    OR("OR"),
    NOT("NOT");

    private final String sqlValue;

    SqlWords(String sqlValue) {
        this.sqlValue = sqlValue;
    }
    public String getSqlValue() {
        return sqlValue;
    }
}
