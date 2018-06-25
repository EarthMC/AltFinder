package com.karlofduty.altfinder;

import com.karlofduty.altfinder.commands.IPCommand;
import com.karlofduty.altfinder.commands.MCLeaksCommand;
import me.gong.mcleaks.MCLeaksAPI;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class AltFinder extends JavaPlugin
{
    public static FileConfiguration config;
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
        this.getCommand("ip").setExecutor(new IPCommand());
        this.getCommand("mcleaks").setExecutor(new MCLeaksCommand());
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

    public static ConsoleCommandSender getConsole()
    {
        return instance.getServer().getConsoleSender();
    }

    public static void executeCommand(String command)
    {
        AltFinder.getInstance().getServer().dispatchCommand(getConsole(), command);
    }

    public static void log(String message)
    {
        AltFinder.getInstance().getServer().getLogger().info(message);
    }
    public static void logWarning(String message)
    {
        AltFinder.getInstance().getServer().getLogger().warning(message);
    }
}
