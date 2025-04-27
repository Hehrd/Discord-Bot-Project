package com.alexander.bot.cmd.commands.subcommands.commands.table;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.service.JWTService;
import com.alexander.bot.tools.interpreter.AddColumnInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class TableColumnAddSubcommand extends BotSubcommand {

    @Autowired
    public TableColumnAddSubcommand(RestTemplate restTemplate, AddColumnInterpreter interpreter, JWTService jwtService) {
        super("add", "Add a column", restTemplate, interpreter, jwtService);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        StringBuffer container = new StringBuffer();
        container
                .append(event.getOption("container").getAsString())
                .append("-")
                .append(event.getUser().getId());
        String database = event.getOption("database").getAsString();
        String sql = sqlInterpeter.createSqlString(getOptionsMap(event));
        String cmd = String.format(
                "/usr/local/bin/docker start %1$s && " +
                        "/usr/local/bin/docker exec %1$s " +
                        "psql -h localhost -p 5432 -U postgres " +
                        "-d %2$s -c \"%3$s\"",
                container.toString(), database, sql
        );
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        event.getHook().sendMessage(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("container", new OptionData(OptionType.STRING, "container", "The name of the container", true));
        options.put("database", new OptionData(OptionType.STRING, "database", "The name of the database", true));
        options.put("table", new OptionData(OptionType.STRING, "table", "The the name of the table", true));
        options.put("name", new OptionData(OptionType.STRING, "name", "The name of the column", true));
        options.put("type", new OptionData(OptionType.STRING, "type", "The type of the column", true).addChoices(
                new Command.Choice("INTEGER", "INTEGER"),
                new Command.Choice("TEXT", "TEXT"),
                new Command.Choice("BOOLEAN", "BOOLEAN")));
        options.put("default", new OptionData(OptionType.STRING, "default", "Default value", false));
        options.put("isNullable", new OptionData(OptionType.BOOLEAN, "is_nullable", "Can the column be null", false));
    }
}
