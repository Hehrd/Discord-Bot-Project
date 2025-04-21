package com.alexander.bot.validation;

import com.alexander.bot.error.exceptions.ApiKeyNotFoundException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiKeyValidator extends Validator {

    @Autowired
    public ApiKeyValidator(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public void validate(SlashCommandInteractionEvent event) throws ApiKeyNotFoundException {
        String url = String.format("http://localhost:6969/discodb/hasKey/%s", event.getUser().getId());
        boolean hasKey = restTemplate.getForEntity(url, Boolean.class).getBody();
        if (!hasKey) {
            throw new ApiKeyNotFoundException(event, "Api key not found!");
        }
    }
}
