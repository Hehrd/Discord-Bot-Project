package com.alexander.bot.validation;

import com.alexander.bot.error.exceptions.ApiKeyNotFoundException;
import com.alexander.bot.error.exceptions.BotCommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.web.client.RestTemplate;

public abstract class Validator {
    protected RestTemplate restTemplate;

    public Validator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public abstract void validate(SlashCommandInteractionEvent event) throws BotCommandException;
}
