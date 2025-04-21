package com.alexander.bot.cmd.commands.subcommands.groups;

import com.alexander.bot.cmd.commands.subcommands.commands.table.TableColumnAddSubcommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TableColumnSubcommandGroup extends BotSubcommandGroup {
    @Autowired
    protected TableColumnSubcommandGroup(ApplicationContext context) {
        super("column", "Do something with a column");
        subcommands = new HashMap<>();
        subcommands.put(context.getBean(TableColumnAddSubcommand.class).getName(), context.getBean(TableColumnAddSubcommand.class));
    }
}
