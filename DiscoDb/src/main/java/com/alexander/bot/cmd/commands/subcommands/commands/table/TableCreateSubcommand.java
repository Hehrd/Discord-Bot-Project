package com.alexander.bot.cmd.commands.subcommands.commands.table;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.tools.CreateTableInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class TableCreateSubcommand extends BotSubcommand {

    @Autowired
    public TableCreateSubcommand(RestTemplate restTemplate, CreateTableInterpreter interpreter) {
        super("create", "Creates a table", restTemplate, interpreter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        List<String> commands = new ArrayList<>();
        commands.add(String.format("docker exec -it %s-%s sh",
                event.getOption("container").getAsString(),
                event.getUser().getId()));
        commands.add(String.format("\\c %s", event.getOption("database").getAsString()));
        commands.add(sqlInterpeter.createSqlString(getOptionsMap(event)));
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/execcmd", commands, String.class);
        event.reply(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("name", new OptionData(OptionType.STRING, "name", "The name of the table", true));
        options.put("database", new OptionData(OptionType.STRING, "database", "The name of the database", true));
        options.put("container", new OptionData(OptionType.STRING, "container", "The name of the container", true));
    }
}
