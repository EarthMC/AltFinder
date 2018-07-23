package com.karlofduty.altfinder.eventlisteners;

import com.karlofduty.altfinder.ConfigValues;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

//TODO: Restructure class after temporary database functionality has been added
public class OnPlayerJoin implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(ConfigValues.getBool("mcleaks.join-check.enable-join-check"))
        {
            MCLeaksAutoCheck.checkPlayer(event);
        }
    }
}
