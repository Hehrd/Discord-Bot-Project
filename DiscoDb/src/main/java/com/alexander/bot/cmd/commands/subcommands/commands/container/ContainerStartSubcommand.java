package com.alexander.bot.cmd.commands.subcommands.commands.container;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.service.JWTService;
import com.alexander.bot.tools.interpreter.CreateTableInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class ContainerStartSubcommand extends BotSubcommand {
    @Autowired
    public ContainerStartSubcommand(RestTemplate restTemplate, CreateTableInterpreter interpreter, JWTService jwtService) {
        super("start", "Start a container", restTemplate, interpreter, jwtService);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        StringBuffer containerName = new StringBuffer();
        containerName.append(event.getOption("name").getAsString())
                     .append("-")
                     .append(event.getUser().getId());
        String cmd = String.format("/usr/local/bin/docker start %s", containerName);
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        event.reply(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("name", new OptionData(OptionType.STRING, "name", "The name of the container.", true));
    }
}
