package com.alexander.bot.cmd.commands.subcommands.commands;

import com.alexander.bot.tools.SqlInterpeter;
import lombok.Data;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class BotSubcommand {
    protected String name;
    protected String description;
    protected Map<String, OptionData> options;
    protected RestTemplate restTemplate;
    protected SqlInterpeter sqlInterpeter;

    public BotSubcommand(String name, String description, RestTemplate restTemplate, SqlInterpeter sqlInterpeter) {
        this.name = name;
        this.description = description;
        this.restTemplate = restTemplate;
        this.sqlInterpeter = sqlInterpeter;
        initOptions();
    }

    public abstract void execute(SlashCommandInteractionEvent event);

    protected abstract void initOptions();

    public SubcommandData toSubcommandData() {
        SubcommandData subcommandData = new SubcommandData(name, description);
        if (options == null) {
            System.out.println(this.toString());
        }
        List<OptionData> optionDataList = new ArrayList<>();
        List<OptionData> requiredOptions = new ArrayList<>();
        List<OptionData> optionalOptions = new ArrayList<>();
        for (OptionData optionData : options.values()) {
            if (optionData.isRequired()) {
                requiredOptions.add(optionData);
                continue;
            }
            optionalOptions.add(optionData);
        }
        optionDataList.addAll(requiredOptions);
        optionDataList.addAll(optionalOptions);
        subcommandData.addOptions(optionDataList);
        return subcommandData;
    }

    protected Map<String, String> getOptionsMap(SlashCommandInteractionEvent event) {
        Map<String, String> optionsMap = new HashMap<>();
        for (OptionMapping option : event.getOptions()) {
            optionsMap.put(option.getName(), option.getAsString());
        }
        return optionsMap;
    }


}
