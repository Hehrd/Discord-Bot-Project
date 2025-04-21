package com.alexander.bot.cmd.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BotCommandCredentials {
    FINDBY_CREDENTIALS("findby", "Selects from the database"),
    TABLE_CREDENTIALS("table", "Creates a table"),
    CONTAINER_CREDENTIALS("container", "Creates a docker container"),
    ACTIVATE_BOT("activate_bot", "Activate the services of the bot"),
    DATABASE_CREDENTIALS("database", "Creates a database"),;

    private final String name;
    private final String description;

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
