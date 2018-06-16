package com.karlofduty.altfinder;

import org.bukkit.plugin.java.JavaPlugin;
import me.gong.mcleaks.MCLeaksAPI;
import com.karlofduty.altfinder.commands.CommandIP;
import com.karlofduty.altfinder.commands.CommandMCLeaks;
import java.util.concurrent.TimeUnit;

public class AltFinder extends JavaPlugin
{
    private static AltFinder instance;

    @Override
    public void onEnable(){
        //Fired when the server enables the plugin
        instance = this;
        CommandMCLeaks mcleaksCommand = new CommandMCLeaks();

        this.getCommand("ip").setExecutor(new CommandIP());
        this.getCommand("mcleaks").setExecutor(mcleaksCommand);
    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }

    public static AltFinder getInstance() {
        return instance;
    }
}
