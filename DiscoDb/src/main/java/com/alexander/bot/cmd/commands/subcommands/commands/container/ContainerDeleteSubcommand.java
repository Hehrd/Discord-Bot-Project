package com.alexander.bot.cmd.commands.subcommands.commands.container;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.service.JWTService;
import com.alexander.bot.tools.interpreter.CreateTableInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class ContainerDeleteSubcommand extends BotSubcommand {
    @Autowired
    public ContainerDeleteSubcommand(RestTemplate restTemplate, CreateTableInterpreter interpreter, JWTService jwtService) {
        super("delete", "Delete a container", restTemplate, interpreter, jwtService);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String containerName = String.format("%s-%s", event.getOption("name").getAsString(), event.getUser().getId());
        StringBuffer cmd = new StringBuffer();
        cmd.append(String.format("/usr/local/bin/docker stop %s", containerName));
        cmd.append(" && ");
        cmd.append(String.format("/usr/local/bin/docker rm %s", containerName));
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd.toString(), String.class);
        sendRequestToAccManager(event.getUser().getId(), containerName);
        event.getHook().sendMessage(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("name", new OptionData(OptionType.STRING, "name", "The name of the table"));
    }

    private void sendRequestToAccManager(String id, String containerName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtService.createByDiscordId(id));
        HttpEntity<String> httpEntity = new HttpEntity<>(containerName, headers);
        restTemplate.exchange("http://localhost:6969/containers", HttpMethod.POST, httpEntity, String.class);
    }
}
