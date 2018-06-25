package com.karlofduty.altfinder.commands;

import com.karlofduty.altfinder.ConfigValues;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class IPCommand extends CommandUtilities implements CommandExecutor
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
                    commandSender.sendMessage(ConfigValues.getParsedString("commands.ip.other", commandSender, args[0]));
                }
                else
                {
                    commandSender.sendMessage(ConfigValues.getParsedString("commands.ip.no-such-player", commandSender, args[0]));
                }
            }
            else
            {
                commandSender.sendMessage(ConfigValues.getParsedString("commands.no-perm", commandSender, args[0]));
            }
        }
        else
        {
            if(commandSender.hasPermission("altfinder.ip.self"))
            {
                commandSender.sendMessage(ConfigValues.getParsedString("commands.ip.self", commandSender));
            }
            else
            {
                commandSender.sendMessage(ConfigValues.getParsedString("commands.no-perm", commandSender));
            }
        }
        return true;
    }
}
