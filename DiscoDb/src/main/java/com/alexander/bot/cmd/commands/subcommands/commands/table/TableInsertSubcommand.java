package com.alexander.bot.cmd.commands.subcommands.commands.table;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.service.JWTService;
import com.alexander.bot.tools.interpreter.InsertInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class TableInsertSubcommand extends BotSubcommand {
    @Autowired
    public TableInsertSubcommand(RestTemplate restTemplate, InsertInterpreter interpreter, JWTService jwtService) {
        super("insert","Insert a row", restTemplate, interpreter, jwtService);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String containerName = String.format("%s-%s", event.getOption("container").getAsString(), event.getUser().getId());
        String sql = sqlInterpeter.createSqlString(getOptionsMap(event));
        String cmd = String.format(
                "/usr/local/bin/docker start %1$s && " +
                        "/usr/local/bin/docker exec %1$s " +
                        "psql -h localhost -p 5432 -U postgres -d %2$s " +
                        "-c \"%3$s\"",
                containerName, event.getOption("database").getAsString(), sql
        );
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        event.reply(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("container", new OptionData(OptionType.STRING, "container", "The container of the table", true));
        options.put("database", new OptionData(OptionType.STRING, "database", "The database of the table", true));
        options.put("table", new OptionData(OptionType.STRING, "table", "The name of the table", true));
        options.put("columns", new OptionData(OptionType.STRING, "columns", "column1;column2", true));
        options.put("values", new OptionData(OptionType.STRING, "values", "value1;value2", true));
    }
}
