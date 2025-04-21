package com.alexander.bot.cmd.commands.subcommands.commands.table;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import com.alexander.bot.tools.SqlInterpeter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class TableInsertSubcommand extends BotSubcommand {
    public TableInsertSubcommand(String name, String description, RestTemplate restTemplate, SqlInterpeter sqlInterpeter) {
        super("insert","Insert a row", restTemplate, sqlInterpeter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }

    @Override
    protected void initOptions() {
        options = new HashMap<>();
        options.put("container", new OptionData(OptionType.STRING, "container", "The container of the table", true));
        options.put("database", new OptionData(OptionType.STRING, "database", "The database of the table", true));
        options.put("column_and_values", new OptionData(OptionType.STRING, "columns_and_values", "column1:value1;column2:value2", true));
    }
}
