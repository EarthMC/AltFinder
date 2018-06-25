package com.karlofduty.altfinder.commands;

import com.karlofduty.altfinder.AltFinder;
import com.karlofduty.altfinder.ConfigValues;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandUtilities
{
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
    public void executeCommand(String command)
    {
        AltFinder.getInstance().getServer().dispatchCommand(getConsole(), command);
    }
}
