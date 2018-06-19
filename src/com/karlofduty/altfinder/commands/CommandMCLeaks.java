package com.karlofduty.altfinder.commands;

import com.karlofduty.altfinder.AltFinder;
import com.karlofduty.altfinder.MCLeaksChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMCLeaks implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if(strings.length < 1)
        {
            commandSender.sendMessage("§cInvalid command usage.");
            return false;
        }
        if(!commandSender.hasPermission("altfinder.mcleaks"))
        {
            commandSender.sendMessage("§cYou do not have permission to do that.");
            return true;
        }
        MCLeaksChecker.checkPlayerAsync(strings[0], AltFinder.getInstance().mcLeaksAPI, (result, username) -> {
            if(result == -1)
            {
                commandSender.sendMessage("§6An error occurred fetching player from MCLeaksChecker database, try again later.");
            }
            else if(result == 0)
            {
                commandSender.sendMessage("§ePlayer is §2NOT §eon the list of confirmed MCLeaks accounts although may still be one.");
            }
            else if (result == 1)
            {
                commandSender.sendMessage("§ePlayer §cIS §eon the list of confirmed MCLeaksChecker account.");
            }
            else if (result == 2)
            {
                commandSender.sendMessage("§eThis player has not joined the server before.");
            }
        });
        return true;

    }
}