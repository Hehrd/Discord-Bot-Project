package com.alexander.bot.cmd.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HelloBotCommand extends BotCommand {
    @Autowired
    public HelloBotCommand(ApplicationContext applicationContext) {
        super(BotCommandCredentials.HELLO_CREDENTIALS, applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        System.out.println("hello");
        event.reply("Hello World! ~ " + event.getChannel().getName()).setEphemeral(false).queue();
    }

    @Override
    protected void initOptions() {
        options = Optional.empty();
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
