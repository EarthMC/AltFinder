package com.karlofduty.altfinder.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
public class CommandIP implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player player;
        if(args.length != 0)
        {
            player = Bukkit.getPlayer(args[0]);
        }
        else
        {
            player = Bukkit.getPlayer(sender.getName());
        }

        String ip = player.getAddress().getHostName();
        sender.sendMessage(ip);
        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
