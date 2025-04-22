package com.alexander.bot.cmd.commands.subcommands.groups;

import com.alexander.bot.cmd.commands.subcommands.commands.table.TableCreateSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.table.TableInsertSubcommand;
import com.alexander.bot.cmd.commands.subcommands.commands.table.TableSelectSubcommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TableOtherSubcommandGroup extends BotSubcommandGroup {
    @Autowired
    protected TableOtherSubcommandGroup(ApplicationContext context) {
        super("other", "Other commands");
        subcommands = new HashMap<>();
        subcommands.put(context.getBean(TableCreateSubcommand.class).getName(), context.getBean(TableCreateSubcommand.class));
        subcommands.put(context.getBean(TableSelectSubcommand.class).getName(), context.getBean(TableSelectSubcommand.class));
        subcommands.put(context.getBean(TableInsertSubcommand.class).getName(), context.getBean(TableInsertSubcommand.class));
    }
}
