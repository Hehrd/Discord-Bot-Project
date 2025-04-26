package com.alexander.bot.cmd.commands.subcommands.commands.database;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.model.request.CreateDatabaseRequestDTO;
import com.alexander.bot.service.JWTService;
import com.alexander.bot.tools.interpreter.CreateDatabaseInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class DatabaseCreateSubcommand extends BotSubcommand {
    @Autowired
    public DatabaseCreateSubcommand(RestTemplate restTemplate, CreateDatabaseInterpreter interpreter, JWTService jwtService) {
        super("create", "Create a database", restTemplate, interpreter, jwtService);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String containerName = String.format("%s-%s", event.getOption("container").getAsString(), event.getUser().getId());
        String sql = sqlInterpeter.createSqlString(getOptionsMap(event));
        String cmd = String.format(
                "/usr/local/bin/docker start %1$s && " +
                        "/usr/local/bin/docker exec %1$s " +
                        "psql -h localhost -p 5432 -U postgres " +
                        "-c \"%2$s\"",
                containerName, sql
        );
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        sendRequestToAccManager(event.getUser().getId(), CreateDatabaseRequestDTO.builder()
                                                         .container(containerName)
                                                         .name(event.getOption("name").getAsString())
                                                         .build());
        event.getHook().sendMessage(output.getBody()).setEphemeral(true).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("container", new OptionData(OptionType.STRING, "container", "A container for the database", true));
        options.put("name", new OptionData(OptionType.STRING, "name", "The name of the database", true));
    }

    private void sendRequestToAccManager(String id, CreateDatabaseRequestDTO createDatabaseRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtService.createByDiscordId(id));
        HttpEntity<CreateDatabaseRequestDTO> httpEntity = new HttpEntity<>(createDatabaseRequestDTO, headers);
        restTemplate.exchange("http://localhost:6969/databases", HttpMethod.POST, httpEntity, String.class);
    }
}
