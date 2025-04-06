package com.alexander.bot;

import com.alexander.bot.cmd.CommandManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bot {
    @Autowired
    private ShardManager shardManager;
    @Autowired
    private CommandManager commandManager;

    public Bot() {
        start();
    }

    public void start() {
//        commandManager = new CommandManager();
//
////        shutdown();
    }

    public void shutdown() {
        shardManager.shutdown();
    }
}
