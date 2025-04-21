package com.alexander.bot.cmd.commands;

import com.alexander.bot.cmd.commands.subcommands.groups.BotSubcommandGroup;
import com.alexander.bot.cmd.commands.subcommands.groups.TableColumnSubcommandGroup;
import com.alexander.bot.cmd.commands.subcommands.groups.TableOtherSubcommandGroup;
import com.alexander.bot.error.exceptions.BotCommandException;
import com.alexander.bot.tools.CreateTableInterpreter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TableCommand extends SqlCommand{
    @Autowired
    public TableCommand(RestTemplate restTemplate, ApplicationContext applicationContext) {
        super(restTemplate, new CreateTableInterpreter(), BotCommandCredentials.TABLE_CREDENTIALS, applicationContext);
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
        subcommandGroups.put(applicationContext.getBean(TableOtherSubcommandGroup.class).getName(), applicationContext.getBean(TableOtherSubcommandGroup.class));
        subcommandGroups.put(applicationContext.getBean(TableColumnSubcommandGroup.class).getName(), applicationContext.getBean(TableColumnSubcommandGroup.class));
    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
