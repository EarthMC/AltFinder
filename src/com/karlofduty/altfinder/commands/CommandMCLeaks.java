package com.karlofduty.altfinder.commands;

import com.karlofduty.altfinder.AltFinder;
import me.gong.mcleaks.MCLeaksAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CommandMCLeaks implements CommandExecutor
{
    public final MCLeaksAPI api = MCLeaksAPI.builder()
            .threadCount(2)
            .expireAfter(10, TimeUnit.MINUTES).build();
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
        checkPlayerAsync(strings[0], api, result -> {
            if(result == -1)
            {
                commandSender.sendMessage("§6An error occurred fetching player from MCLeaks database, try again later.");
            }
            else if(result == 0)
            {
                commandSender.sendMessage("§ePlayer is §2NOT §eon the list of confirmed MCLeaks accounts. This does not mean that they are not an MCLeaks account, only that they have not been found yet.");
            }
            else if (result == 1)
            {
                commandSender.sendMessage("§ePlayer §cIS §eon the list of confirmed MCLeaks account.");
            }
            else if (result == 4)
            {
                commandSender.sendMessage("This player has not joined the server before.");
            }
        });
        return true;

    }
    private static void checkPlayerAsync(final String username, final MCLeaksAPI api, final FetchMCLeaksCallback callback)
    {
        // Run outside of the tick loop
        Bukkit.getScheduler().runTaskAsynchronously(AltFinder.getInstance(), () -> {
            int result = -1;
            OfflinePlayer op = Bukkit.getOfflinePlayer(username);
            if (op.hasPlayedBefore())
            {

                final MCLeaksAPI.Result uuidResult = api.checkAccount(op.getUniqueId());
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