package com.alexander.bot.error.exceptions;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ApiKeyNotFoundException extends BotCommandException {
    public ApiKeyNotFoundException(SlashCommandInteractionEvent event, String message) {
        super(event, message);
    }
}
