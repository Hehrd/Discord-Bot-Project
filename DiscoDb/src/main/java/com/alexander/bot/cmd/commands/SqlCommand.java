package com.alexander.bot.cmd.commands;

public abstract class SqlCommand extends BotCommand {
    protected final String baseSql;

    public SqlCommand(BotCommandCredentials botCommand, String baseSql) {
        super(botCommand);
        this.baseSql = baseSql;
    }
}
