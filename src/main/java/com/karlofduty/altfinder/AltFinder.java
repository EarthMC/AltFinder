package com.karlofduty.altfinder;

import com.karlofduty.altfinder.commands.IPCommand;
import com.karlofduty.altfinder.commands.MCLeaksCommand;
import com.karlofduty.altfinder.commands.VPNShieldCommand;
import com.karlofduty.altfinder.eventlisteners.OnPlayerJoin;
import me.gong.mcleaks.MCLeaksAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class AltFinder extends JavaPlugin
{
    public static FileConfiguration config;
    private static String configVersion = "00002";

    public final MCLeaksAPI mcLeaksAPI = MCLeaksAPI.builder()
            .threadCount(2)
            .expireAfter(30, TimeUnit.MINUTES).build();

    private static AltFinder instance;

    public boolean vpnShieldEnabled;

    public boolean vpnShieldStrictMode;

    @Override
    public void onEnable()
    {
        instance = this;

        //TODO: Config validation
        saveDefaultConfig();
        config = this.getConfig();
        if(!config.getString("config-version").equalsIgnoreCase(configVersion))
        {
            logColoured(ChatColor.YELLOW + "Your config was made for an older version of AltFinder, you can check the GitHub page for new config options or remove 'config-version' from config.yml\n" +
                    "\n" +
                    "Current config version: " + config.getString("config-version") + "\n"+
                    "Compatible config version: " + configVersion + "\n");
        }

        // Set VPNShield options
        vpnShieldEnabled = config.getBoolean("vpnshield.enable-by-default");
        vpnShieldStrictMode = config.getBoolean("increased-default-sensitivity");

        // Set command executors
        this.getCommand("ip").setExecutor(new IPCommand());
        this.getCommand("mcleaks").setExecutor(new MCLeaksCommand());
        this.getCommand("vpnshield").setExecutor(new VPNShieldCommand());
        //TODO: Add reload command

        // Register events
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
    }

    @Override
    public void onDisable()
    {
        mcLeaksAPI.shutdown();
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
        instance.getServer().dispatchCommand(getConsole(), command);
    }

    public static void log(String message)
    {
        instance.getServer().getLogger().info(message);
    }
    public static void logColoured(String message)
    {
        getConsole().sendMessage(message);
    }
    public static void logWarning(String message)
    {
        instance.getServer().getLogger().warning(ChatColor.GOLD + message);
    }
}
