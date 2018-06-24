package com.karlofduty.altfinder.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandIP extends CommandUtilities implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args)
    {
        if(args.length != 0)
        {
            if(commandSender.hasPermission("altfinder.ip.other"))
            {
                if(Bukkit.getPlayer(args[0]) != null)
                {
                    commandSender.sendMessage(parseConfigMessageSync("commands.ip.other", commandSender, args[0]));
                }
                else
                {
                    commandSender.sendMessage(parseConfigMessageSync("commands.ip.nosuchplayer", commandSender, args[0]));
                }
            }
            else
            {
                commandSender.sendMessage(parseConfigMessageSync("commands.noperm", commandSender, args[0]));
            }
        }
        else
        {
            if(commandSender.hasPermission("altfinder.ip.self"))
            {
                commandSender.sendMessage(parseConfigMessageSync("commands.ip.self", commandSender));
            }
            else
            {
                commandSender.sendMessage(parseConfigMessageSync("commands.noperm", commandSender));
            }
        }
        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
