package com.alexander.bot;

import com.alexander.bot.cmd.CommandManager;
import com.alexander.bot.cmd.commands.BotCommand;
import com.alexander.bot.tools.SelectInterpreter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
    @Bean
    public CommandManager commandManager(@Autowired ApplicationContext applicationContext) {
        return new CommandManager(applicationContext.getBeansOfType(BotCommand.class),
                applicationContext.getBeansOfType(BotCommand.class));
    }

    @Bean
    public ShardManager shardManager(@Autowired CommandManager commandManager,
                                     @Value("${jda.api.token}") String token) {
        DefaultShardManagerBuilder shardManagerBuilder = DefaultShardManagerBuilder.createDefault(token);
        ShardManager shardManager = shardManagerBuilder
                .setStatus(OnlineStatus.IDLE)
                .setActivity(Activity.customStatus("Doing something"))
                .addEventListeners(commandManager)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        return shardManager;
    }

    @Bean
    public SelectInterpreter sqlInterpreter() {
        return new SelectInterpreter();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
