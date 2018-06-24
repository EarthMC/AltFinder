package com.karlofduty.altfinder.commands;

import com.karlofduty.altfinder.AltFinder;
import com.karlofduty.altfinder.MCLeaksChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMCLeaks extends CommandUtilities implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if(args.length == 0)
        {
            commandSender.sendMessage(parseConfigMessageSync("commands.invalidusage",commandSender));
            return false;
        }
        if(!commandSender.hasPermission("altfinder.mcleaks"))
        {
            commandSender.sendMessage(parseConfigMessageSync("commands.noperm",commandSender));
            return true;
        }

        MCLeaksChecker.checkPlayerAsync(args[0],AltFinder.getInstance().mcLeaksAPI, (result) -> {
            if(result == -1)
            {
                //Error occured
                commandSender.sendMessage(parseConfigMessageSync("commands.mcleaks.error", commandSender, args[0]));
            }
            else if(result == 0)
            {
                //User was not found on mcleaks
                commandSender.sendMessage(parseConfigMessageSync("commands.mcleaks.notfound", commandSender, args[0]));
            }
            else if (result == 1)
            {
                //User was found on mcleaks
                commandSender.sendMessage(parseConfigMessageSync("commands.mcleaks.found", commandSender, args[0]));
            }
            else if (result == 2)
            {
                //User has never joined the server
                commandSender.sendMessage(parseConfigMessageSync("commands.mcleaks.nosuchplayer", commandSender, args[0]));
            }
        });
        return true;

    }
}