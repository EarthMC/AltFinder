package com.karlofduty.altfinder;

import org.bukkit.entity.Player;

public class Utilities
{
    public static boolean hasAllParmissions(Player player, String[] strings)
    {
        for(String s: strings)
        {
            if(!player.hasPermission(s))
            {
                return false;
            }
        }
        return true;
    }
    public static boolean hasAnyParmissions(Player player, String[] strings)
    {
        for(String s: strings)
        {
            if(player.hasPermission(s))
            {
                return true;
            }
        }
        return false;
    }
}
