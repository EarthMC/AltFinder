package com.karlofduty.altfinder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class EventListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(ConfigValues.getBool("mcleaks.join-check.enable-join-check"))
        {
            final String username = event.getPlayer().getName();
            MCLeaksChecker.checkPlayerAsync(username, AltFinder.getInstance().mcLeaksAPI, (result) ->
            {
                for (Player player : Bukkit.getServer().getOnlinePlayers())
                {
                    if (player.hasPermission("altfinder.mcleaks.notify"))
                    {
                        if (result == -1 && ConfigValues.getBool("mcleaks.join-check.error.notify"))
                        {
                            player.sendMessage(ConfigValues.getParsedString("mcleaks.join-check.error.notification", AltFinder.getConsole(), username));
                        }
                        else if (result == 0 && ConfigValues.getBool("mcleaks.join-check.not-mcleaks.notify"))
                        {
                            player.sendMessage(ConfigValues.getParsedString("mcleaks.join-check.not-mcleaks.notification", AltFinder.getConsole(), username));
                        }
                        else if (result == 1 && ConfigValues.getBool("mcleaks.join-check.is-mcleaks.notify"))
                        {
                            player.sendMessage(ConfigValues.getParsedString("mcleaks.join-check.is-mcleaks.notification", AltFinder.getConsole(), username));
                        }
                    }
                }
                if (result == -1 && ConfigValues.getBool("mcleaks.join-check.error.execute-command"))
                {
                    AltFinder.executeCommand(ConfigValues.getParsedString("mcleaks.join-check.error.command", AltFinder.getConsole(), username));
                }
                else if (result == 0 && ConfigValues.getBool("mcleaks.join-check.not-mcleaks.execute-command"))
                {
                    AltFinder.executeCommand(ConfigValues.getParsedString("mcleaks.join-check.not-mcleaks.command", AltFinder.getConsole(), username));
                }
                else if (result == 1 && ConfigValues.getBool("mcleaks.join-check.is-mcleaks.execute-command"))
                {
                    AltFinder.executeCommand(ConfigValues.getParsedString("mcleaks.join-check.is-mcleaks.command", AltFinder.getConsole(), username));
                }
            });
        }
    }
}
