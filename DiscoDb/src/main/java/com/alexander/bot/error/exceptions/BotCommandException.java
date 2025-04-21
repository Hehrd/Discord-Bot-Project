package com.alexander.bot.error.exceptions;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class BotCommandException extends Exception {
    private SlashCommandInteractionEvent event;

    public BotCommandException(SlashCommandInteractionEvent event, String message) {
        super(message);
        this.event = event;
    }

    public SlashCommandInteractionEvent getEvent() {
        return event;
    }
}
