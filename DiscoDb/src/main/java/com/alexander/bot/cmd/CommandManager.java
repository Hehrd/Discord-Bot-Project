package com.alexander.bot.cmd;

import com.alexander.bot.cmd.commands.*;
import com.alexander.bot.error.GlobalExceptionHandler;
import com.alexander.bot.error.exceptions.BotCommandException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class CommandManager extends ListenerAdapter {
    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    private Map<String, BotCommand> globalBotCommands;
    private Map<String, BotCommand> serverExclusiveBotCommands;

    public CommandManager(Map<String, BotCommand> globalBotCommands,
                          Map<String, BotCommand> serverExclusiveBotCommands) {
        this.globalBotCommands = globalBotCommands;
        this.serverExclusiveBotCommands = serverExclusiveBotCommands;
        this.exceptionHandler = exceptionHandler;
        initCommands();
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Ready");
        updateCommands(event.getJDA());

//        for (Guild guild : event.getJDA().getGuilds()) {
//            initCommandsInGuild(guild);
//        }
//        event.getJDA().retrieveCommands().queue(commands -> {
//            for (Command command : commands) {
//                command.delete().queue();
//            }
//        });
    }



    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        System.out.println("Slash CommandInteraction");
        try {
            globalBotCommands.get(event.getName()).execute(event);
        } catch (BotCommandException e) {
            exceptionHandler.handle(e);
        }

    }

//    private void initCommandsInGuild(Guild guild) {
//        for (BotCommand botCommand : serverExclusiveBotCommands) {
//            CommandCreateAction commandCreateAction = guild.upsertCommand(botCommand.getName(), botCommand.getDescription());
//            commandCreateAction.addOptions(botCommand.getOptions().orElse(new ArrayList<>())).queue();
//        }
//    }
    private void initCommands() {
        initGlobalBotCommands();
        initServerExclusiveBotCommands();
    }

    private void initGlobalBotCommands() {
        Collection<BotCommand> botCommands = globalBotCommands.values();
        globalBotCommands = new HashMap<>();
        for (BotCommand botCommand : botCommands) {
            globalBotCommands.put(botCommand.getName(), botCommand);
        }
    }

    private void initServerExclusiveBotCommands() {
        Collection<BotCommand> botCommands = serverExclusiveBotCommands.values();
        serverExclusiveBotCommands = new HashMap<>();
        for (BotCommand botCommand : botCommands) {
            serverExclusiveBotCommands.put(botCommand.getName(), botCommand);
        }
    }

    private void updateCommands(JDA jda) {
        updateGlobalCommands(jda);
        updateServerExclusiveCommands(jda.getGuilds());
    }

    private void updateServerExclusiveCommands(List<Guild> guilds) {
        for (Guild guild : guilds) {
            CommandListUpdateAction commandListUpdateAction = guild.updateCommands();
            commandListUpdateAction.addCommands(serverExclusiveBotCommands.values()
                    .stream()
                    .map(botCommand -> botCommand.toCommandData()).toList()).queue();
        }
    }

    private void updateGlobalCommands(JDA jda) {
        CommandListUpdateAction commandListUpdateAction = jda.updateCommands();
        commandListUpdateAction.addCommands(globalBotCommands.values().stream()
                .map(botCommand -> botCommand.toCommandData()).toList()).queue();
    }
}
