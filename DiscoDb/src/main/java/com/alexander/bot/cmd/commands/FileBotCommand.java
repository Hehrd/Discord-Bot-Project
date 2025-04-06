package com.alexander.bot.cmd.commands;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.util.Optional;

public class FileBotCommand extends BotCommand {
    public FileBotCommand() {
        super(BotCommandCredentials.FILE_CREDENTIALS);
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
}
