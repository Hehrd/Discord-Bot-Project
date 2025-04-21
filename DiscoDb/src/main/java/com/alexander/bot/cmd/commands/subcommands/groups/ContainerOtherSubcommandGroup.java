package com.alexander.bot.cmd.commands.subcommands.groups;

import com.alexander.bot.cmd.commands.subcommands.commands.container.ContainerCreateSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.container.ContainerDeleteSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.container.ContainerStartSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.container.ContainerStopSubcommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ContainerOtherSubcommandGroup extends BotSubcommandGroup{
    @Autowired
    public ContainerOtherSubcommandGroup(ApplicationContext applicationContext) {
        super("other", "Other commands");
        subcommands = new HashMap<>();
        subcommands.put(applicationContext.getBean(ContainerCreateSubcommand.class).getName(), applicationContext.getBean(ContainerCreateSubcommand.class));
        subcommands.put(applicationContext.getBean(ContainerStartSubcommand.class).getName(), applicationContext.getBean(ContainerStartSubcommand.class));
        subcommands.put(applicationContext.getBean(ContainerDeleteSubcommand.class).getName(), applicationContext.getBean(ContainerDeleteSubcommand.class));
        subcommands.put(applicationContext.getBean(ContainerStopSubcommand.class).getName(), applicationContext.getBean(ContainerStopSubcommand.class));
    }
}
