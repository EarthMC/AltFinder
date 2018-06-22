package com.karlofduty.altfinder.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandIP implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args)
    {
        if(args.length != 0)
        {
            if(commandSender.hasPermission("altfinder.ip.other"))
            {
                commandSender.sendMessage("§eThe player's current IP address is §6" + Bukkit.getPlayer(args[0]).getAddress().getHostName() + "§e.");
            }
            else
            {
                commandSender.sendMessage("§cYou do not have permission to do that.");
            }
        }
        else
        {
            if(commandSender.hasPermission("altfinder.ip.self"))
            {
                Player player = Bukkit.getPlayer(commandSender.getName());
                if(player != null)
                {
                    commandSender.sendMessage("§eYour current IP address is §6" + player.getAddress().getHostName() + "§e.");
                }
                else
                {
                    commandSender.sendMessage("§eThat player is not currently online.");
                }
            }
            else
            {
                commandSender.sendMessage("§cYou do not have permission to do that.");
            }
        }
        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
