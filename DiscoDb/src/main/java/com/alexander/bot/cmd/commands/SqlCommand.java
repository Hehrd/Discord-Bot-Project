package com.alexander.bot.cmd.commands;

import com.alexander.bot.tools.interpreter.SqlInterpeter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;


public abstract class SqlCommand extends RestCommand {
    protected final SqlInterpeter selectInterpreter;
    public SqlCommand(RestTemplate restTemplate,
                      SqlInterpeter sqlInterpeter,
                      BotCommandCredentials credentials,
                      ApplicationContext applicationContext) {
        super(restTemplate, credentials, applicationContext);
        this.selectInterpreter = sqlInterpeter;
    }
}
