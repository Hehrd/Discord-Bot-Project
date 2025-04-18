package com.alexander.bot.cmd.commands;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class WordRepeatBotCommand extends BotCommand {
    @Autowired
    public WordRepeatBotCommand(ApplicationContext applicationContext) {
        super(BotCommandCredentials.WORD_REPEAT_CREDENTIALS, applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        MessageChannel messageChannel = event.getChannel();
        messageChannel.sendMessage(event.getOption("word").getAsString()).queue();
    }

    @Override
    protected void initOptions() {
        List<OptionData> optionsList = new ArrayList<>();
        optionsList.add(new OptionData(OptionType.STRING, "word", "Word to repeat", true));
        Optional<List<OptionData>> optionsWrapper = Optional.of(optionsList);
        options = optionsWrapper;
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }


}
