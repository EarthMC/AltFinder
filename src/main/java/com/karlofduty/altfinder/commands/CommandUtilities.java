package com.karlofduty.altfinder.commands;

import com.karlofduty.altfinder.AltFinder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandUtilities
{
    public String parseConfigMessageSync(String configPath, CommandSender source, String targetPlayer)
    {
        return parseConfigMessageSync(configPath,source, getPlayerInfo(targetPlayer));
    }

    public String parseConfigMessageSync(String configPath, CommandSender source)
    {
        return parseConfigMessageSync(configPath,source,"null","null","null");
    }

    public String parseConfigMessageSync(String configPath, CommandSender source, String[] targetPlayer)
    {
        return parseConfigMessageSync(configPath,source,targetPlayer[0],targetPlayer[1],targetPlayer[2]);
    }

    public String parseConfigMessageSync(String configPath, CommandSender source, String targetName, String targetUUID, String targetIP)
    {
        String sourceName = "Console";
        String sourceUUID = "null";
        String sourceIP = "127.0.0.1";

        if(source instanceof Player)
        {
            Player player = (Player)source;
            sourceName = player.getName();
            sourceUUID = player.getUniqueId().toString();
            sourceIP = player.getAddress().getHostName();
        }
        else
        {
            //Sets the console name as some plugins change it.
            sourceName = source.getName();
        }

        String result = AltFinder.getLoadedConfig().getString(configPath);

        result = result.replaceAll("<sourceplayer>", sourceName);
        result = result.replaceAll("<sourceuuid>", sourceUUID);
        result = result.replaceAll("<sourceip>", sourceIP);

        result = result.replaceAll("<targetplayer>", targetName);
        result = result.replaceAll("<targetuuid>", targetUUID);
        result = result.replaceAll("<targetip>", targetIP);

        AltFinder.getInstance().getLogger().info(result);
        return result;
    }
    public String[] getPlayerInfo(String username)
    {
        if(Bukkit.getPlayer(username) != null)
        {
            return getOnlinePlayerInfo(username);
        }
        else
        {
            return getOfflinePlayerInfo(username);
        }
    }
    public String[] getOnlinePlayerInfo(String username)
    {
        String[] result = {username, "null", "null"};
        Player player = Bukkit.getPlayer(username);
        if(player != null)
        {
            result = new String[]{player.getName(), player.getUniqueId().toString(), player.getAddress().getHostName()};
        }
        return result;
    }
    public String[] getOfflinePlayerInfo(String username)
    {
        String[] result = {username, "null", "null"};
        OfflinePlayer player = Bukkit.getOfflinePlayer(username);
        if(player != null)
        {
            result = new String[]{player.getName(), player.getUniqueId().toString(), "null"};
        }
        return result;
    }
    public boolean isConfigSettingOn(String configPath)
    {
        return AltFinder.getLoadedConfig().getBoolean(configPath);
    }
    public boolean hasAllPermissions(Player player, String[] strings)
    {
        for(String s: strings)
        {
            if(!player.hasPermission(s))
            {
                return false;
            }
        }
        return true;
    }
    public boolean hasAnyPermissions(Player player, String[] strings)
    {
        for(String s: strings)
        {
            if(player.hasPermission(s))
            {
                return true;
            }
        }
        return false;
    }
    public ConsoleCommandSender getConsole()
    {
        return AltFinder.getInstance().getServer().getConsoleSender();
    }
}
