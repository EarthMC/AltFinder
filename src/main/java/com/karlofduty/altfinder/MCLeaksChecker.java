package com.karlofduty.altfinder;

import me.gong.mcleaks.MCLeaksAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class MCLeaksChecker
{
    public static void checkPlayerAsync(final String username, final MCLeaksAPI api, final FetchMCLeaksCallback callback)
    {
        // Run outside of the tick loop
        Bukkit.getScheduler().runTaskAsynchronously(AltFinder.getInstance(), () -> {
            UUID uuid = null;
            int result = -1;
            if(Bukkit.getPlayerExact(username) != null)
            {
                uuid = Bukkit.getPlayerExact(username).getUniqueId();
            }
            else
            {
                OfflinePlayer op = Bukkit.getOfflinePlayer(username);
                if (op.hasPlayedBefore())
                {
                    uuid = op.getUniqueId();
                }
            }

            if(uuid != null)
            {
                final MCLeaksAPI.Result uuidResult = api.checkAccount(uuid);
                if(!uuidResult.hasError())
                {
                    if(uuidResult.isMCLeaks())
                    {
                        result = 1;
                    }
                    else
                    {
                        result = 0;
                    }
                }
                else
                {
                    uuidResult.getError().printStackTrace();
                }
            }
            else
            {
                result = 2;
            }
            final int finalResult = result;

            // go back to the tick loop
            Bukkit.getScheduler().runTask(AltFinder.getInstance(), () -> {
                // call the callback with the result
                callback.onCheckDone(finalResult);
            });
        });
    }
}
