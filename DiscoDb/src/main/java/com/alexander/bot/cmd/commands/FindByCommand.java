package com.alexander.bot.cmd.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Optional;

public class FindByCommand extends SqlCommand {
    public FindByCommand() {
        super(BotCommandCredentials.FINDBY_CREDENTIALS, String.format("SELECT * from"));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }

    @Override
    protected void initOptions() {
        options = Optional.empty();
    }
}
