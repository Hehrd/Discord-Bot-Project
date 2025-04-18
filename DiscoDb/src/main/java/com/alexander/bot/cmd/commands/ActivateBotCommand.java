package com.alexander.bot.cmd.commands;

import com.alexander.bot.dto.AddKeyRequestDTO;
import com.alexander.bot.error.exceptions.BotCommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ActivateBotCommand extends RestCommand {

    @Autowired
    protected ActivateBotCommand(RestTemplate restTemplate, ApplicationContext applicationContext) {
        super(restTemplate, BotCommandCredentials.ACTIVATE_BOT, applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws BotCommandException {
        validate(event);
        String discordId = event.getUser().getId();
        String apiKey = event.getOption("api_key").getAsString();
        AddKeyRequestDTO addKeyRequestDTO = new AddKeyRequestDTO();
        addKeyRequestDTO.setApiKey(apiKey);
        addKeyRequestDTO.setDiscordId(discordId);
        String url = "http://localhost:6969/discodb/addKey";
        restTemplate.postForEntity(url, addKeyRequestDTO, String.class);
    }

    @Override
    protected void initOptions() {
        List<OptionData> optionsList = new ArrayList<>();
        optionsList.add(new OptionData(OptionType.STRING, "api_key", "API key to activate our services for your account", true));
        options = Optional.of(optionsList);
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
