package com.alexander.bot.cmd.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Optional;

public class HelloBotCommand extends BotCommand {
    public HelloBotCommand() {
        super(BotCommandCredentials.HELLO_CREDENTIALS);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        System.out.println("hello");
        event.reply("Hello World! ~ " + event.getChannel().getName()).setEphemeral(false).queue();
    }

    @Override
    protected void initOptions() {
        options = Optional.empty();
    }
}
