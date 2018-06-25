package com.karlofduty.altfinder.commands;

import com.karlofduty.altfinder.AltFinder;
import com.karlofduty.altfinder.ConfigValues;
import com.karlofduty.altfinder.MCLeaksChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MCLeaksCommand extends CommandUtilities implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if(args.length == 0)
        {
            commandSender.sendMessage(ConfigValues.getParsedString("commands.invalid-usage",commandSender));
            return false;
        }
        if(!commandSender.hasPermission("altfinder.mcleaks"))
        {
            commandSender.sendMessage(ConfigValues.getParsedString("commands.no-perm",commandSender));
            return true;
        }

        MCLeaksChecker.checkPlayerAsync(args[0],AltFinder.getInstance().mcLeaksAPI, (result) -> {
            if(result == -1)
            {
                //Error occured
                commandSender.sendMessage(ConfigValues.getParsedString("commands.mcleaks.error", commandSender, args[0]));
            }
            else if(result == 0)
            {
                //User was not found on mcleaks
                commandSender.sendMessage(ConfigValues.getParsedString("commands.mcleaks.not-found", commandSender, args[0]));
            }
            else if (result == 1)
            {
                //User was found on mcleaks
                commandSender.sendMessage(ConfigValues.getParsedString("commands.mcleaks.found", commandSender, args[0]));
            }
            else if (result == 2)
            {
                //User has never joined the server
                commandSender.sendMessage(ConfigValues.getParsedString("commands.mcleaks.no-such-player", commandSender, args[0]));
            }
        });
        return true;
    }
}