package com.alexander.bot.cmd.commands.subcommands.groups;

import com.alexander.bot.cmd.commands.subcommands.commands.database.DatabaseCreateSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.database.DatabaseDeleteSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.database.DatabaseDownloadSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.table.TableColumnAddSubcommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DatabaseOtherSubcommandGroup extends BotSubcommandGroup {
    @Autowired
    public DatabaseOtherSubcommandGroup(ApplicationContext context) {
        super("other", "Other commands");
        subcommands = new HashMap<>();
        subcommands.put(context.getBean(DatabaseCreateSubcommand.class).getName(), context.getBean(DatabaseCreateSubcommand.class));
        subcommands.put(context.getBean(DatabaseDeleteSubcommand.class).getName(), context.getBean(DatabaseDeleteSubcommand.class));
        subcommands.put(context.getBean(DatabaseDownloadSubcommand.class).getName(), context.getBean(DatabaseDownloadSubcommand.class));
    }
}
