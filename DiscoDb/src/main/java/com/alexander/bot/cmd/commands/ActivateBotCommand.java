package com.alexander.bot.cmd.commands;

import com.alexander.bot.model.AddKeyRequestDTO;
import com.alexander.bot.error.exceptions.BotCommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

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
        String url = "http://localhost:6969/acc/apikeys/activate";
        restTemplate.postForEntity(url, addKeyRequestDTO, String.class);
    }

    @Override
    protected void initOptions() {
        options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "api_key", "API key to activate our services for your account", true));
    }

    @Override
    protected void initSubcommandGroups(ApplicationContext applicationContext) {
        subcommandGroups = new HashMap<>();
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
