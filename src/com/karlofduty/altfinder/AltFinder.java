package com.karlofduty.altfinder;

import org.bukkit.plugin.java.JavaPlugin;
import me.gong.mcleaks.MCLeaksAPI;
import com.karlofduty.altfinder.commands.CommandIP;
import com.karlofduty.altfinder.commands.CommandMCLeaks;
import java.util.concurrent.TimeUnit;

public class AltFinder extends JavaPlugin
{
    final MCLeaksAPI api = MCLeaksAPI.builder()
        .threadCount(2)
        .expireAfter(10, TimeUnit.MINUTES).build();
    @Override
    public void onEnable(){
        //Fired when the server enables the plugin

        this.getCommand("ip").setExecutor(new CommandIP());
        this.getCommand("mcleaks").setExecutor(new CommandMCLeaks());
    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }
}
