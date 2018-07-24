package com.karlofduty.altfinder.commands;

import com.karlofduty.altfinder.AltFinder;
import com.karlofduty.altfinder.ConfigValues;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VPNShieldCommand extends CommandUtilities implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args)
    {
        if(args.length >= 0)
        {
            if(args[0].equals("toggle"))
            {
                if(commandSender.hasPermission("altfinder.vpnshield.toggle"))
                {
                    AltFinder.getInstance().vpnShieldEnabled = !AltFinder.getInstance().vpnShieldEnabled;
                    if(AltFinder.getInstance().vpnShieldEnabled)
                    {
                        commandSender.sendMessage(ConfigValues.getString("commands.vpnshield.enable"));
                    }
                    else
                    {
                        commandSender.sendMessage(ConfigValues.getString("commands.vpnshield.disable"));
                    }
                }
                else
                {
                    commandSender.sendMessage(ConfigValues.getString("commands.noperm"));
                }
            }
            else if(args[0].equals("on"))
            {
                if(commandSender.hasPermission("altfinder.vpnshield.toggle"))
                {
                    AltFinder.getInstance().vpnShieldEnabled = true;
                    commandSender.sendMessage(ConfigValues.getString("commands.vpnshield.enable"));
                }
                else
                {
                    commandSender.sendMessage(ConfigValues.getString("commands.noperm"));
                }
            }
            else if(args[0].equals("off"))
            {
                if(commandSender.hasPermission("altfinder.vpnshield.toggle"))
                {
                    AltFinder.getInstance().vpnShieldEnabled = false;
                    commandSender.sendMessage(ConfigValues.getString("commands.vpnshield.disable"));
                }
                else
                {
                    commandSender.sendMessage(ConfigValues.getString("commands.noperm"));
                }
            }
            else if(args[0].equals("strictmode"))
            {
                if(args.length <= 1)
                {
                    return false;
                }
                else if(args[1].equals("toggle"))
                {
                    if(commandSender.hasPermission("altfinder.vpnshield.strictmode.toggle"))
                    {
                        AltFinder.getInstance().vpnShieldStrictMode = !AltFinder.getInstance().vpnShieldStrictMode;
                        if(AltFinder.getInstance().vpnShieldStrictMode)
                        {
                            commandSender.sendMessage(ConfigValues.getString("commands.vpnshield.strictmode.enable"));
                        }
                        else
                        {
                            commandSender.sendMessage(ConfigValues.getString("commands.vpnshield.strictmode.disable"));
                        }
                    }
                    else
                    {
                        commandSender.sendMessage(ConfigValues.getString("commands.noperm"));
                    }
                }
                else if(args[1].equals("on"))
                {
                    if(commandSender.hasPermission("altfinder.vpnshield.strictmode.toggle"))
                    {
                        AltFinder.getInstance().vpnShieldStrictMode = true;
                        commandSender.sendMessage(ConfigValues.getString("commands.vpnshield.strictmode.enable"));
                    }
                    else
                    {
                        commandSender.sendMessage(ConfigValues.getString("commands.noperm"));
                    }

                }
                else if(args[1].equals("off"))
                {
                    if(commandSender.hasPermission("altfinder.vpnshield.strictmode.toggle"))
                    {
                        AltFinder.getInstance().vpnShieldStrictMode = false;
                        commandSender.sendMessage(ConfigValues.getString("commands.vpnshield.strictmode.disable"));
                    }
                    else
                    {
                        commandSender.sendMessage(ConfigValues.getString("commands.noperm"));
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }
}