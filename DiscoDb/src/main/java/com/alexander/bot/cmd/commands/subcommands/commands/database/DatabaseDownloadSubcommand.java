package com.alexander.bot.cmd.commands.subcommands.commands.database;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.service.JWTService;
import com.alexander.bot.tools.CommandBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;

@Component
public class DatabaseDownloadSubcommand extends BotSubcommand {
    @Autowired
    public DatabaseDownloadSubcommand(RestTemplate restTemplate, JWTService jwtService) {
        super("download", "Download a database", restTemplate, null, jwtService);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String dbName = event.getOption("name").getAsString();
        String containerName = String.format("%s-%s", event.getOption("container").getAsString(), event.getUser().getId());
        String dumpName = String.format("/tmp/dump-%s-%s.sql", dbName, event.getUser().getId());
        CommandBuilder commandBuilder = new CommandBuilder();
        commandBuilder.addPart(String.format("/usr/local/bin/docker start %1$s", containerName))
                .addPart(String.format("/usr/local/bin/docker exec %1$s", containerName))
                .addPart("bash -c")
                .addPartInQuotes(String.format("pg_dump -h localhost -p 5432 -U postgres %s > %s", dbName, dumpName));
        restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", commandBuilder.build(), String.class);
        File dumpFile = new File(dumpName);
        event.replyFiles(FileUpload.fromData(dumpFile)).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("name", new OptionData(OptionType.STRING, "name", "The name of the database to download"));
        options.put("container", new OptionData(OptionType.STRING, "container", "The name of the container"));
    }
}
