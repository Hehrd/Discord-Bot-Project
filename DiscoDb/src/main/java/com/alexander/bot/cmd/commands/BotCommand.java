package com.alexander.bot.cmd.commands;

import lombok.Data;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
public abstract class BotCommand {
    private final BotCommandCredentials credentials;
    protected Optional<List<OptionData>> options;

    public BotCommand(BotCommandCredentials botCommandCredentials) {
        this.credentials = botCommandCredentials;
        initOptions();
    }


    public abstract void execute(SlashCommandInteractionEvent event);


    protected abstract void initOptions();

    public CommandData toCommandData() {
        System.out.println("test");
//        Collection<? extends OptionData> optionData = new ArrayList<>();
//        if (options.isPresent()) {
//            optionData = options.get();
//        }
        return Commands
                .slash(credentials.getName(), credentials.getDescription())
                .addOptions(options.orElse(new ArrayList<>()));
    }

    public String getName() {
        return credentials.getName();
    }

    public String getDescription() {
        return credentials.getDescription();
    }

}
