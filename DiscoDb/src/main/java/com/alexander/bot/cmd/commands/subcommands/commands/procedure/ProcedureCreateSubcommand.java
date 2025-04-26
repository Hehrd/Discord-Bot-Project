package com.alexander.bot.cmd.commands.subcommands.commands.procedure;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.tools.CreateProcedureInterpreter;
import com.alexander.bot.tools.SqlInterpeter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class ProcedureCreateSubcommand extends BotSubcommand {

    @Autowired
    public ProcedureCreateSubcommand(RestTemplate restTemplate, CreateProcedureInterpreter interpreter) {
        super("create", "Create a procedure", restTemplate, interpreter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String containerName = String.format("%s-%s", event.getOption("container").getAsString(), event.getUser().getId());
        String database = event.getOption("database").getAsString();
        String sql = sqlInterpeter.createSqlString(getOptionsMap(event));
        String cmd = String.format(
                "/usr/local/bin/docker start %1$s && " +
                        "/usr/local/bin/docker exec %1$s " +
                        "psql -h localhost -p 5432 -U postgres " +
                        "-d %2$s -c \"%3$s\"",
                containerName, database, sql
        );
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        event.reply(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("name", new OptionData(OptionType.STRING, "name", "The name of the procedure", true));
        options.put("database", new OptionData(OptionType.STRING, "database", "The name of the database", true));
        options.put("container", new OptionData(OptionType.STRING, "container", "The name of the container", true));
        options.put("param_names", new OptionData(OptionType.STRING, "param_names", "The names of the params", true));
        options.put("param_types", new OptionData(OptionType.STRING, "param_types", "The types of the params", true));
        options.put("body", new OptionData(OptionType.STRING, "body", "The body of the procedure", true));
        options.put("language", new OptionData(OptionType.STRING, "language", "The language of the procedure", true));
    }
}
