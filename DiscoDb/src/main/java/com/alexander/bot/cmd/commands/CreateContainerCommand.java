package com.alexander.bot.cmd.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class CreateContainerCommand extends RestCommand {
    @Autowired
    protected CreateContainerCommand(RestTemplate restTemplate, ApplicationContext applicationContext) {
        super(restTemplate,
                BotCommandCredentials.CREATE_CONTAINER_CREDENTIALS,
                applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String name = event.getOption("name").getAsString();
        String password = event.getOption("password").getAsString();
        String user = event.getOption("user").getAsString();
        String cmd = String.format("/usr/local/bin/docker run --name %s -e POSTGRES_PASSWORD=%s -p 5432:5432 -d %s", name, password, user);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        event.reply(responseEntity.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        List<OptionData> optionsList = new ArrayList<>();
        optionsList.add(new OptionData(OptionType.STRING, "name", "The name of the container", true));
        optionsList.add(new OptionData(OptionType.STRING, "user", "User for the container", true));
        optionsList.add(new OptionData(OptionType.STRING, "password", "Password for the user", true));
        Map<String, String> defaultsMap = new HashMap<>();
        defaultsMap.put("user", "postgres");
        options = Optional.of(optionsList);
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
