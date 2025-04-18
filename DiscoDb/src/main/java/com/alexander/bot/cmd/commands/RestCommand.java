package com.alexander.bot.cmd.commands;

import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

public abstract class RestCommand extends BotCommand {
    protected RestTemplate restTemplate;
    protected RestCommand(RestTemplate restTemplate, BotCommandCredentials credentials, ApplicationContext applicationContext) {
        super(credentials, applicationContext);
        this.restTemplate = restTemplate;
    }
}
