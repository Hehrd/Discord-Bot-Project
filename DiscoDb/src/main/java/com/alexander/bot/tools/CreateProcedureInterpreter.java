package com.alexander.bot.tools;

import com.alexander.bot.cmd.commands.subcommands.commands.BotSubcommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class CreateProcedureInterpreter extends SqlInterpeter {

    public CreateProcedureInterpreter() {
        super("CREATE PROCEDURE");
    }

    @Override
    public String createSqlString(Map<String, String> options) {
        StringBuffer sql = new StringBuffer();
        sql.append(baseSql);
        appendName(sql, options.get("name"));
        appendParams(sql, options.get("param_names"), options.get("param_types"));
        appendLanguage(sql, options.get("language"));
        appendBody(sql, options.get("body"));
        return sql.toString();
    }

    @Override
    protected void initDefaultOptions() {

    }

    private void appendName(StringBuffer sql, String name) {
        sql.append(" ");
        sql.append(name);
    }
    private void appendParams(StringBuffer sql, String paramNames, String paramTypes) {
        List<String> paramNamesList = Arrays.asList(paramNames.split(";"));
        List<String> paramTypesList = Arrays.asList(paramTypes.split(";"));
        StringBuffer params = new StringBuffer();
        params.append("(");
        for (int i = 0; i < paramNamesList.size(); i++) {
            if (i > 0) {
                params.append(", ");
            }
            params.append(paramNamesList.get(i));
            params.append(" ");
            params.append(paramTypesList.get(i));
        }
        params.append(")\n");
        sql.append(params);
    }
    private void appendLanguage(StringBuffer sql, String language) {
        sql.append(" ");
        sql.append("LANGUAGE ");
        sql.append(language);
        sql.append("\n");
    }

    private void appendBody(StringBuffer sql, String body) {
        sql.append("AS \\$\\$\n");
        sql.append("BEGIN\n");
        sql.append(body);
        sql.append("END;\n");
        sql.append("\\$\\$;");

    }
}
