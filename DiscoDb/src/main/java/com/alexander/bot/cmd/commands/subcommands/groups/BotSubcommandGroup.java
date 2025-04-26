package com.alexander.bot.cmd.commands.subcommands.groups;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import lombok.Data;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.util.Map;

@Data
public abstract class BotSubcommandGroup {
    protected final String name;
    protected final String description;
    protected Map<String, BotSubcommand> subcommands;

    public BotSubcommandGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SubcommandGroupData toSubcommandGroupData() {
        SubcommandGroupData subcommandGroupData = new SubcommandGroupData(name, description);
        subcommandGroupData.addSubcommands(subcommands.values()
                .stream()
                .map(subcommand -> subcommand.toSubcommandData())
                .toList());
        return subcommandGroupData;
    }

    public BotSubcommand getSubcommand(String name) {
        return subcommands.get(name);
    }
}
