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
    final MCLeaksAPI api = MCLeaksAPI.builder()
            .threadCount(2)
            .expireAfter(10, TimeUnit.MINUTES).build();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if(strings.length > 0)
        {
            OfflinePlayer op = Bukkit.getOfflinePlayer(strings[0]);
            if (op.hasPlayedBefore())
            {
                checkPlayerAsync(op.getUniqueId(), api, new FetchMCLeaksCallback()
                {
                    @Override
                    public void onCheckDone(int result)
                    {
                        if(result == -1)
                        {
                            commandSender.sendMessage("§6An error occurred fetching player from MCLeaks database, try again later.");
                        }
                        else if(result == 0)
                        {
                            commandSender.sendMessage("§2Player is NOT on the list of confirmed MCLeaks accounts");
                        }
                        else
                        {
                            commandSender.sendMessage("§cPlayer IS on the list of confirmed MCLeaks account.");
                        }
                    }
                });
            }
            else
            {
                commandSender.sendMessage("This player has not joined the server before.");
            }
            return true;
        }
        commandSender.sendMessage("Invalid command usage.");
        return false;
    }
    private static void checkPlayerAsync(final UUID uuid, final MCLeaksAPI api, final FetchMCLeaksCallback callback)
    {
        // Run outside of the tick loop
        Bukkit.getScheduler().runTaskAsynchronously(AltFinder.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                int result = -1;
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

                final int finalResult = result;

                // go back to the tick loop
                Bukkit.getScheduler().runTask(AltFinder.getInstance(), new Runnable()
                {
                    @Override
                    public void run()
                    {
                        // call the callback with the result
                        callback.onCheckDone(finalResult);
                    }
                });
            }
        });
    }
}