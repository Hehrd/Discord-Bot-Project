package com.alexander.bot.cmd.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BotCommandCredentials {
    QUERY_REDENTIALS("query", "Write a custom query"),
    TABLE_CREDENTIALS("table", "Creates a table"),
    CONTAINER_CREDENTIALS("container", "Creates a docker container"),
    ACTIVATE_BOT("activate_bot", "Activate the services of the bot"),
    DATABASE_CREDENTIALS("database", "Creates a database"),
    PROCEDURE_CREDENTIALS("procedure", "Creates a procedure"),;

    private final String name;
    private final String description;

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
