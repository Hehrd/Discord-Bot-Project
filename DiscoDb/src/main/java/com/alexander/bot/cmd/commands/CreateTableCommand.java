package com.alexander.bot.cmd.commands;

import com.alexander.bot.error.exceptions.BotCommandException;
import com.alexander.bot.tools.CreateTableInterpreter;
import com.alexander.bot.tools.SqlInterpeter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CreateTableCommand extends SqlCommand{
    @Autowired
    public CreateTableCommand(RestTemplate restTemplate,
                              CreateTableInterpreter createTableInterpreter,
                              ApplicationContext applicationContext) {
        super(restTemplate,
                createTableInterpreter,
                BotCommandCredentials.CREATE_TABLE_CREDENTIALS,
                applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws BotCommandException {

    }

    @Override
    protected void initOptions() {

    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {

    }
}
