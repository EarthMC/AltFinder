package com.karlofduty.altfinder;

import com.karlofduty.altfinder.commands.CommandIP;
import com.karlofduty.altfinder.commands.CommandMCLeaks;
import com.karlofduty.altfinder.listeners.EventListener;
import me.gong.mcleaks.MCLeaksAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class AltFinder extends JavaPlugin
{
    public FileConfiguration config;
    public final MCLeaksAPI mcLeaksAPI = MCLeaksAPI.builder()
            .threadCount(2)
            .expireAfter(30, TimeUnit.MINUTES).build();
    private static AltFinder instance;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        config = this.getConfig();
        //Fired when the server enables the plugin
        instance = this;
        this.getCommand("ip").setExecutor(new CommandIP());
        this.getCommand("mcleaks").setExecutor(new CommandMCLeaks());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable()
    {
        //Fired when the server stops and disables all plugins

    }

    public static AltFinder getInstance() {
        return instance;
    }

    public static FileConfiguration getLoadedConfig() {return instance.config;}
}
