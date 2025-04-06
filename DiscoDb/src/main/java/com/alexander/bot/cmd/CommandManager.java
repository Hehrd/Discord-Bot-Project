package com.alexander.bot.cmd;

import com.alexander.bot.cmd.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandManager extends ListenerAdapter {
    private final Map<String, BotCommand> globalBotCommands;
    private final Map<String, BotCommand> serverExclusiveBotCommands;

    public CommandManager() {
        globalBotCommands = new HashMap<>();
        serverExclusiveBotCommands = new HashMap<>();
        initCommands();
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Ready");
        JDA jda = event.getJDA();
        updateGlobalCommands(jda);

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
        globalBotCommands.get(event.getName()).execute(event);
    }

//    private void initCommandsInGuild(Guild guild) {
//        for (BotCommand botCommand : serverExclusiveBotCommands) {
//            CommandCreateAction commandCreateAction = guild.upsertCommand(botCommand.getName(), botCommand.getDescription());
//            commandCreateAction.addOptions(botCommand.getOptions().orElse(new ArrayList<>())).queue();
//        }
//    }

    private void initCommands() {
        initGlobalCommands();
        initServerExclusiveCommands();
    }

    private void initGlobalCommands() {
        globalBotCommands.put(BotCommandCredentials.HELLO_CREDENTIALS.getName(), new HelloBotCommand());
        globalBotCommands.put(BotCommandCredentials.FILE_CREDENTIALS.getName(),new FileBotCommand());
//        globalCommands.add(new WordRepeatCommand());
        globalBotCommands.put(BotCommandCredentials.FINDBY_CREDENTIALS.getName(), new FindByCommand());
    }

    private void initServerExclusiveCommands() {
        serverExclusiveBotCommands.put(BotCommandCredentials.WORD_REPEAT_CREDENTIALS.getName(), new WordRepeatBotCommand());
    }

    private void updateGlobalCommands(JDA jda) {
        CommandListUpdateAction commandListUpdateAction = jda.updateCommands();
        commandListUpdateAction.addCommands(globalBotCommands.values().stream()
                .map(botCommand -> botCommand.toCommandData()).toList());
    }
}
