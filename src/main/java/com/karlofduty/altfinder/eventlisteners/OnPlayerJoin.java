package com.karlofduty.altfinder.eventlisteners;

import com.karlofduty.altfinder.AltFinder;
import com.karlofduty.altfinder.ConfigValues;
import com.karlofduty.altfinder.VPNChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

        if(ConfigValues.getBool("mcleaks.join-check.enable-join-check"))
        {
            checkVPN(event);
        }

    }
    private void checkVPN(PlayerJoinEvent event)
    {
        VPNChecker.checkPlayerAsync(event.getPlayer().getAddress().getHostName(), (result) ->
        {
            // -1: Error, 0: Not vpn, 1: Vpn
            int resultCode = 0;
            if(result.equals("ERROR"))
            {
                AltFinder.log("VPN check error.");
                resultCode = -1;
            }
            else
            {
                try
                {
                    final JSONObject obj = (JSONObject) new JSONParser().parse(result);
                    long severity = (long)obj.get("block");

                    if(severity == 1 || ConfigValues.getBool("vpnshield.increased-default-sensitivity") && severity == 2)
                    {
                        resultCode = 1;
                    }

                }
                catch (ParseException e)
                {
                    AltFinder.log("VPN check error. Invalid JSON string.");
                    resultCode = -1;
                    e.printStackTrace();
                }
            }

            String username = event.getPlayer().getName();
            // Send notifications
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {
                if (player.hasPermission("altfinder.vpnshield.notify"))
                {
                    if (resultCode == -1 && ConfigValues.getBool("vpnshield.error.notify"))
                    {
                        player.sendMessage(ConfigValues.getParsedString("vpnshield.error.notification", AltFinder.getConsole(), username));
                    }
                    else if (resultCode == 0 && ConfigValues.getBool("vpnshield.not-vpn.notify"))
                    {
                        player.sendMessage(ConfigValues.getParsedString("vpnshield.not-vpn.notification", AltFinder.getConsole(), username));
                    }
                    else if (resultCode == 1 && ConfigValues.getBool("vpnshield.using-vpn.notify"))
                    {
                        player.sendMessage(ConfigValues.getParsedString("vpnshield.using-vpn.notification", AltFinder.getConsole(), username));
                    }
                }
            }

            //Execute commands
            if (resultCode == -1 && ConfigValues.getBool("vpnshield.error.execute-command"))
            {
                AltFinder.executeCommand(ConfigValues.getParsedString("vpnshield.error.command", AltFinder.getConsole(), username));
            }
            else if (resultCode == 0 && ConfigValues.getBool("vpnshield.not-vpn.execute-command"))
            {
                AltFinder.executeCommand(ConfigValues.getParsedString("vpnshield.not-vpn.command", AltFinder.getConsole(), username));
            }
            else if (resultCode == 1 && ConfigValues.getBool("vpnshield.using-vpn.execute-command"))
            {
                if(event.getPlayer().hasPermission("altfinder.vpnshield.bypass"))
                {
                    if(ConfigValues.getBool("vpnshield.bypass.notify"))
                    {
                        event.getPlayer().sendMessage(ConfigValues.getParsedString("vpnshield.bypass.notification", AltFinder.getConsole(), username));
                    }
                }
                else
                {
                    AltFinder.executeCommand(ConfigValues.getParsedString("vpnshield.using-vpn.command", AltFinder.getConsole(), username));
                }

            }

        });
    }
}
