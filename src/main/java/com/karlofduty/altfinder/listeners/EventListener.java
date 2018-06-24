package com.karlofduty.altfinder.listeners;

import com.karlofduty.altfinder.AltFinder;
import com.karlofduty.altfinder.MCLeaksChecker;
import com.karlofduty.altfinder.commands.CommandUtilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class EventListener extends CommandUtilities implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        final String username = event.getPlayer().getName();
        MCLeaksChecker.checkPlayerAsync(username, AltFinder.getInstance().mcLeaksAPI, (result) -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {
                if(player.hasPermission("altfinder.mcleaks.notify"))
                {
                    if(result == -1 && isConfigSettingOn("mcleaks.onPlayerJoin.notifyIfError"))
                        player.sendMessage(parseConfigMessageSync("mcleaks.onPlayerJoin.ifErrorNotification",getConsole(),username));
                    else if(result == 0 && isConfigSettingOn("mcleaks.onPlayerJoin.notifyIfNotMcleaks"))
                        player.sendMessage(parseConfigMessageSync("mcleaks.onPlayerJoin.ifNotMcleaksNotification",getConsole(),username));
                    else if (result == 1 && isConfigSettingOn("mcleaks.onPlayerJoin.notifyIfMcleaks"))
                        player.sendMessage(parseConfigMessageSync("mcleaks.onPlayerJoin.ifMcleaksNotification",getConsole(),username));
                }
            }
            if (result == 1 && isConfigSettingOn("mcleaks.onPlayerJoin.executeCommandIfMcleaksAccount"))
            {
                AltFinder.getInstance().getServer().dispatchCommand(getConsole(),parseConfigMessageSync("mcleaks.onPlayerJoin.ifMcleaksNotification",getConsole(),username));
            }
        });
    }
}
