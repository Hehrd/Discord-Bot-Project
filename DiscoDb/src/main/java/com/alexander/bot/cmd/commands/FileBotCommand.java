package com.alexander.bot.cmd.commands;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@Component
public class FileBotCommand extends BotCommand {
    @Autowired
    public FileBotCommand(ApplicationContext applicationContext) {
        super(BotCommandCredentials.FILE_CREDENTIALS, applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        MessageChannel messageChannel = event.getChannel();
        FileUpload fileUpload = FileUpload.fromData(new File("/Users/alexander/cat-girl.png"));
        messageChannel.sendFiles(fileUpload).queue();
    }

    @Override
    protected void initOptions() {
        options = Optional.empty();
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }

}
