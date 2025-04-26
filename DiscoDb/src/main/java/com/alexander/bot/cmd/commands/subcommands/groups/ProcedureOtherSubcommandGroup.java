package com.alexander.bot.cmd.commands.subcommands.groups;

import com.alexander.bot.cmd.commands.subcommands.commands.database.DatabaseCreateSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.procedure.ProcedureCallSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.procedure.ProcedureCreateSubcommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ProcedureOtherSubcommandGroup extends BotSubcommandGroup {
    @Autowired
    public ProcedureOtherSubcommandGroup(ApplicationContext context) {
        super("other", "Other commands");
        subcommands = new HashMap<>();
        subcommands.put(context.getBean(ProcedureCreateSubcommand.class).getName(), context.getBean(ProcedureCreateSubcommand.class));
        subcommands.put(context.getBean(ProcedureCallSubcommand.class).getName(), context.getBean(ProcedureCallSubcommand.class));
    }
}
