package com.alexander.bot.cmd.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BotCommandCredentials {
    HELLO_CREDENTIALS("hello", "Says hello"),
    FILE_CREDENTIALS("file", "Sends a file"),
    WORD_REPEAT_CREDENTIALS("word_repeat", "Repeats your words"),
    FINDBY_CREDENTIALS("findby", "Selects from the database");

    private final String name;
    private final String description;

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
