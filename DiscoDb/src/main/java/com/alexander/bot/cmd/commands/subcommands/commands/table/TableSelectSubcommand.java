package com.alexander.bot.cmd.commands.subcommands.commands.table;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.tools.SelectInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TableSelectSubcommand extends BotSubcommand {

    @Autowired
    public TableSelectSubcommand(RestTemplate restTemplate, SelectInterpreter interpreter) {
        super("select", "Select from a table", restTemplate, interpreter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String containerName = String.format("%s-%s", event.getOption("container").getAsString(), event.getUser().getId()) ;
        String cmd = String.format(
                "/usr/local/bin/docker start %1$s && " +
                        "/usr/local/bin/docker exec %1$s " +
                        "psql -h localhost -p 5432 -U postgres " +
                        "-d %2$s -c \"%3$s\"",
                containerName, event.getOption("database"), sqlInterpeter.createSqlString(getOptionsMap(event))
        );
        ResponseEntity<String> output = restTemplate.postForEntity("http://localhost:15000/ssh/execcmd", cmd, String.class);
        event.reply(output.getBody()).queue();
    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("master_table", new OptionData(OptionType.STRING, "master_table", "The name of the table", true));
        options.put("joins", new OptionData(OptionType.STRING, "joins", "table1:join_column_in_table1:join_column_in_master_table;table2...", true));
        options.put("fields_and_values", new OptionData(OptionType.STRING, "fields_and_values", "field1=\"string_value\";field2=5;field3=true", true));
        options.put("columns_to_select", new OptionData(OptionType.STRING, "columns_to_select", "Columns to be selected", false));
        options.put("limit", new OptionData(OptionType.INTEGER, "limit", "Limit of selects", false));
    }
}
