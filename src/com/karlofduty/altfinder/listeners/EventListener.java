package com.karlofduty.altfinder.listeners;

import com.karlofduty.altfinder.AltFinder;
import com.karlofduty.altfinder.MCLeaksChecker;
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
        MCLeaksChecker.checkPlayerAsync(event.getPlayer().getName(), AltFinder.getInstance().mcLeaksAPI, (result, username) -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {
                if(player.hasPermission("altfinder.mcleaks.notify"))
                {
                    if(result == -1)
                        player.sendMessage("§eAn error occurred while automatically checking " + username + " in the MCLeaks database.");
                    //else if(result == 0)
                        //player.sendMessage("§e" + username + " is §2NOT §eon the list of confirmed MCLeaksChecker accounts although may still be one.");
                    else if (result == 1)
                        player.sendMessage("§e" + username + "  §cIS §eon the list of confirmed MCLeaks accounts.");
                }
            }
        });
    }
}
