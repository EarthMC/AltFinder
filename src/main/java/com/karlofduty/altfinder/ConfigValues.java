package com.karlofduty.altfinder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ConfigValues
{
    static boolean getBool(String configPath)
    {
        return AltFinder.config.getBoolean(configPath);
    }
    static String getParsedString(String configPath, CommandSender source, String targetPlayer)
    {
        return getParsedString(configPath,source, getPlayerInfo(targetPlayer));
    }

    static String getParsedString(String configPath, CommandSender source)
    {
        return getParsedString(configPath,source,"null","null","null");
    }

    static String getParsedString(String configPath, CommandSender source, String[] targetPlayer)
    {
        return getParsedString(configPath,source,targetPlayer[0],targetPlayer[1],targetPlayer[2]);
    }

    static String getParsedString(String configPath, CommandSender source, String targetName, String targetUUID, String targetIP)
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

        String result = AltFinder.config.getString(configPath);

        result = result.replaceAll("<sourceplayer>", sourceName);
        result = result.replaceAll("<sourceuuid>", sourceUUID);
        result = result.replaceAll("<sourceip>", sourceIP);

        result = result.replaceAll("<targetplayer>", targetName);
        result = result.replaceAll("<targetuuid>", targetUUID);
        result = result.replaceAll("<targetip>", targetIP);

        result = ChatColor.translateAlternateColorCodes('&', result);
        AltFinder.log("[Server -> " + sourceName + "]: " + ChatColor.stripColor(result));
        return result;
    }

    static String getString(String configPath)
    {
        return AltFinder.config.getString(configPath);
    }

    // Gets the info needed for getParsedString(...) from either an online or offline player
    static String[] getPlayerInfo(String username)
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

    static String[] getOnlinePlayerInfo(String username)
    {
        String[] result = {username, "null", "null"};
        Player player = Bukkit.getPlayer(username);
        if(player != null)
        {
            result = new String[]{player.getName(), player.getUniqueId().toString(), player.getAddress().getHostName()};
        }
        return result;
    }

    static String[] getOfflinePlayerInfo(String username)
    {
        String[] result = {username, "null", "null"};
        OfflinePlayer player = Bukkit.getOfflinePlayer(username);
        if(player != null)
        {
            result = new String[]{player.getName(), player.getUniqueId().toString(), "null"};
        }
        return result;
    }
}
