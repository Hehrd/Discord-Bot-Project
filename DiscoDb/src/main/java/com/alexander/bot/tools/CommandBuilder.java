package com.alexander.bot.tools;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
    private List<String> parts;

    public CommandBuilder() {
        parts = new ArrayList<>();
    }

    public CommandBuilder addPart(String part) {
        parts.add(part);
        return this;
    }

    public CommandBuilder addPartInQuotes(String part) {
        String partInQuotes = String.format("'%s'", part);
        parts.add(partInQuotes);
        return this;
    }

    public String build() {
        StringBuffer cmd = new StringBuffer();
        for (String part : parts) {
            cmd.append(part);
            cmd.append(" ");
        }
        return cmd.toString();
    }
}
