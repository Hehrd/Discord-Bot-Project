package com.alexander.bot.cmd.commands;

import com.alexander.bot.cmd.commands.subcommands.commands.procedure.ProcedureCreateSubcommand;
import com.alexander.bot.cmd.commands.subcommands.groups.BotSubcommandGroup;
import com.alexander.bot.cmd.commands.subcommands.groups.DatabaseOtherSubcommandGroup;
import com.alexander.bot.cmd.commands.subcommands.groups.ProcedureOtherSubcommandGroup;
import com.alexander.bot.error.exceptions.BotCommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ProcedureCommand extends BotCommand {

    @Autowired
    protected ProcedureCommand(ApplicationContext applicationContext) {
        super(BotCommandCredentials.PROCEDURE_CREDENTIALS, applicationContext);
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
        subcommandGroups.put(applicationContext.getBean(ProcedureOtherSubcommandGroup.class).getName(), applicationContext.getBean(ProcedureOtherSubcommandGroup.class));
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
