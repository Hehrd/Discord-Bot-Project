package com.alexander.bot.error;

import com.alexander.bot.error.exceptions.ApiKeyNotFoundException;
import com.alexander.bot.error.exceptions.BotCommandException;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GlobalExceptionHandler {
    public void handle(BotCommandException botCommandException) {
        SlashCommandInteractionEvent event = botCommandException.getEvent();
        if (botCommandException instanceof BotCommandException) {
            handleApiKeyNotFoundException(event);
        } else if (botCommandException instanceof ApiKeyNotFoundException) {}
    }

    private void handleApiKeyNotFoundException(SlashCommandInteractionEvent event) {
        event.reply("Api key not found!").queue();
    }
}
