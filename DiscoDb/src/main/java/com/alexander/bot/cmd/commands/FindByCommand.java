package com.alexander.bot.cmd.commands;

import com.alexander.bot.error.exceptions.BotCommandException;
import com.alexander.bot.tools.interpreter.SelectInterpreter;
import com.alexander.bot.validation.ApiKeyValidator;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

//@Component
public class FindByCommand extends SqlCommand {
//    @Autowired
    public FindByCommand(RestTemplate restTemplate,
                         ApplicationContext applicationContext,
                         SelectInterpreter selectInterpreter) {
        super(restTemplate,
                selectInterpreter,
              BotCommandCredentials.QUERY_REDENTIALS,
              applicationContext);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws BotCommandException {
//        validate(event);
//        String sql = selectInterpreter.createSqlString(getOptionsValues(event));
//        String url = String.format(, event.getUser().getId());
        String testUrl = "http://localhost:15000/ssh/execcmd";
        String test = "echo hello";
        String output = restTemplate.postForEntity(testUrl, test, String.class).getBody();
        event.reply(output).setEphemeral(false).queue();
    }

    @Override
    protected void initOptions() {
        options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "master_table", "The name of the table", true));
        options.add(new OptionData(OptionType.STRING, "joins", "table1:join_column_in_table1:join_column_in_master_table;table2...", true));
        options.add(new OptionData(OptionType.STRING, "fields_and_values", "field1=\"string_value\";field2=5;field3=true", true));
        options.add(new OptionData(OptionType.STRING, "columns_to_select", "Columns to be selected", false));
        options.add(new OptionData(OptionType.INTEGER, "limit", "Limit of selects", false));
//        optionsList.add(new OptionData(OptionType.STRING, "sort", "column1:asc", false));
    }

    @Override
    protected void initSubcommandGroups(ApplicationContext applicationContext) {

    }

    @Override
    protected void initValidators(ApplicationContext applicationContext) {
        validators = new ArrayList<>();
        validators.add(applicationContext.getBean(ApiKeyValidator.class));
    }

}
