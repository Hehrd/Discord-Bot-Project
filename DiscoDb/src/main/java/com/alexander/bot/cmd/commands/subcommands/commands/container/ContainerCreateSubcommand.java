package com.alexander.bot.cmd.commands.subcommands.commands.container;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.model.request.CreateContainerRequestDTO;
import com.alexander.bot.service.JWTService;
import com.alexander.bot.tools.interpreter.SelectInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class ContainerCreateSubcommand extends BotSubcommand {
    @Autowired
    public ContainerCreateSubcommand(RestTemplate restTemplate, SelectInterpreter interpreter, JWTService jwtService) {
        super("create", "Create a container", restTemplate, interpreter, jwtService);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String name = String.format("%s-%s", event.getOption("name").getAsString(), event.getUser().getId());
        String cmd = String.format("/usr/local/bin/docker create --name %s -e POSTGRES_PASSWORD=pass -p 5432:5432 postgres", name);
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        CreateContainerRequestDTO createContainerRequestDTO = new CreateContainerRequestDTO();
        createContainerRequestDTO.setName(name.toString());
        createContainerRequestDTO.setDiscordId(event.getUser().getId());
        sendRequestToAccManager(event.getUser().getId(), name.toString());
        event.getHook().sendMessage(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("name", new OptionData(OptionType.STRING, "name", "The name of the table"));
    }

    private void sendRequestToAccManager(String id, String containerName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtService.createByDiscordId(id));
        HttpEntity<String> httpEntity = new HttpEntity<>(containerName, headers);
        restTemplate.exchange("http://localhost:6969/containers", HttpMethod.POST, httpEntity, String.class);
    }
}
