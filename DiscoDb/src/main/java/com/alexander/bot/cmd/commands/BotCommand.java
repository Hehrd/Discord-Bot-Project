package com.alexander.bot.cmd.commands;

import com.alexander.bot.error.exceptions.BotCommandException;
import com.alexander.bot.validation.Validator;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.context.ApplicationContext;

import java.util.*;


public abstract class BotCommand {
    protected final BotCommandCredentials credentials;
    protected Optional<List<OptionData>> options;
    protected List<Validator> validators;

    protected BotCommand(BotCommandCredentials credentials, ApplicationContext applicationContext) {
        this.credentials = credentials;
        initOptions();
        initValidators(applicationContext);
    }


    public abstract void execute(SlashCommandInteractionEvent event) throws BotCommandException;

    protected abstract void initOptions();
    protected abstract void initValidators(ApplicationContext applicationContext);

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

    protected Map<String, String> getOptionsValues(SlashCommandInteractionEvent event) {
        Map<String, String> cmdOptions = new HashMap<>();
        for (OptionData option : options.get()) {
            String name = option.getName();
            String optionValue = event.getOption(name) == null ? "" : event.getOption(name).getAsString();
            cmdOptions.put(name, optionValue);
        }
        return cmdOptions;
    }

    protected void validate(SlashCommandInteractionEvent event) throws BotCommandException {
        for (Validator validator : validators) {
            validator.validate(event);
        }
    }

}
