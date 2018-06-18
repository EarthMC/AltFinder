package com.karlofduty.altfinder;

import org.bukkit.plugin.java.JavaPlugin;
import com.karlofduty.altfinder.commands.CommandIP;
import com.karlofduty.altfinder.commands.CommandMCLeaks;

public class AltFinder extends JavaPlugin
{
    private static AltFinder instance;

    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        //Fired when the server enables the plugin
        instance = this;
        this.getCommand("ip").setExecutor(new CommandIP());
        this.getCommand("mcleaks").setExecutor(new CommandMCLeaks());
    }

    @Override
    public void onDisable()
    {
        //Fired when the server stops and disables all plugins

    }

    public static AltFinder getInstance() {
        return instance;
    }
}
