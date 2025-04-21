package com.alexander.bot.cmd.commands;

import com.alexander.bot.error.exceptions.BotCommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class QueryCommand extends BotCommand {

    @Autowired
    protected QueryCommand(ApplicationContext applicationContext) {
        super(BotCommandCredentials.FINDBY_CREDENTIALS, applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws BotCommandException {
        String containerName = String.format("%s-%s", event.getOption("container").getAsString(), event.getUser().getId());
        String database = event.getOption("database").getAsString();
        String query = event.getOption("sql").getAsString();
        String cmd = String.format(
                "/usr/local/bin/docker start %1$s && " +
                        "/usr/local/bin/docker exec %1$s " +
                        "psql -h localhost -p 5432 -U postgres -d %2$s " +
                        "-c \"\\c db\" -c \"%3$s;\"",
                containerName,
                database,
                query);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        event.reply(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "sql", "SQL query to be executed", true));
        options.add(new OptionData(OptionType.STRING, "container", "The name of the container", true));
        options.add(new OptionData(OptionType.STRING, "database", "The name of the container", true));
    }

    @Override
    protected void initSubcommandGroups(ApplicationContext applicationContext) {
        subcommandGroups = new HashMap<>();
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
