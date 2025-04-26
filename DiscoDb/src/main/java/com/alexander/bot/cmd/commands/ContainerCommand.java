package com.alexander.bot.cmd.commands;

import com.alexander.bot.cmd.commands.subcommands.groups.BotSubcommandGroup;
import com.alexander.bot.cmd.commands.subcommands.groups.ContainerOtherSubcommandGroup;
import com.alexander.bot.error.exceptions.BotCommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ContainerCommand extends RestCommand{

    @Autowired
    protected ContainerCommand(RestTemplate restTemplate, ApplicationContext applicationContext) {
        super(restTemplate, BotCommandCredentials.CONTAINER_CREDENTIALS, applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws BotCommandException {
        String groupName = event.getSubcommandGroup() == null ? "other" : event.getSubcommandGroup();
        BotSubcommandGroup group = subcommandGroups.get(groupName);
        group.getSubcommand(event.getSubcommandName()).execute(event);
    }

    @Override
    protected void initOptions() {
        options = new ArrayList<>();
    }

    @Override
    protected void initSubcommandGroups(ApplicationContext applicationContext) {
        subcommandGroups = new HashMap<>();
        subcommandGroups.put(applicationContext.getBean(ContainerOtherSubcommandGroup.class).getName(), applicationContext.getBean(ContainerOtherSubcommandGroup.class));
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
