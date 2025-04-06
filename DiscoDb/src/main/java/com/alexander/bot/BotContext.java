package com.alexander.bot;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BotContext {
    private String currentContainerName;
    private String currentDatabaseName;


}
