package com.karlofduty.altfinder.eventlisteners;

import com.karlofduty.altfinder.ConfigValues;
import com.karlofduty.altfinder.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

//TODO: Restructure class after temporary database functionality has been added
public class OnPlayerJoin implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        logPlayerJoin(event);
        if(ConfigValues.getBool("mcleaks.join-check.enable-join-check"))
        {
            MCLeaksAutoCheck.checkPlayer(event);
        }
    }
    private void logPlayerJoin(PlayerJoinEvent event)
    {
        boolean exists = false;
        try
        {
            String sql = "SELECT * FROM " + Database.uuidTable + " WHERE uuid=?";
            PreparedStatement stmt = Database.getDB().prepareStatement(sql);
            stmt.setString(1, event.getPlayer().getUniqueId().toString());
            ResultSet results = stmt.executeQuery();

            if (!results.next())
            {
                System.out.println("Failed");
            }
            else
            {
                exists = true;
                System.out.println("Success");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if(!exists)
        {
            String sql = "INSERT INTO " + Database.uuidTable + "(`uuid`, `latestusername`, `latestip`, `lastseen`) VALUES (?,?,?,?);";
            try
            {
                PreparedStatement stmt = Database.getDB().prepareStatement(sql);
                stmt.setString(1, event.getPlayer().getUniqueId().toString());
                stmt.setString(2, event.getPlayer().getName());
                stmt.setString(3, event.getPlayer().getAddress().getHostName());
                stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                stmt.executeUpdate();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
