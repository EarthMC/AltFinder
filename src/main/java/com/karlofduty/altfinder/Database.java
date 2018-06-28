package com.karlofduty.altfinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database
{
    private static Connection connection;
    boolean connected = false;
    private String host, database, username, password;
    private int port;

    public static final String uuidTable = "uuids";
    public static final String nameTable = "names";
    public static final String ipTable = "ips";
    public Database()
    {

    }

    public void connect(final String hostname, final int port, final String database,final String username, final String password) throws SQLException
    {
        this.host = hostname;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.err.println("Java Database Connectivity driver missing!");
            return;
        }

        if (connection != null && !connection.isClosed())
        {
            return;
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database, this.username, this.password);
        validateTablesExist();
        connected = true;
        AltFinder.log("Connected to database.");
    }

    public void reconnect() throws SQLException
    {
        AltFinder.log("Attempting database reconnect...");
        if (connection != null && !connection.isClosed())
        {
            disconnect();
        }
        validateTablesExist();
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database, this.username, this.password);
        connected = true;
        AltFinder.log("Connected to database.");
    }

    public void disconnect()
    {
        try
        {
            if (connection!=null && !connection.isClosed())
            {
                connection.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        AltFinder.log("Disconnected from database.");
        connected = false;
    }

    private void validateTablesExist()
    {
        String uuidSQL = "CREATE TABLE IF NOT EXISTS " + uuidTable +
                "(uuid varchar(64), latestusername varchar(64), latestip varchar(64), lastseen datetime);";
        try
        {
            PreparedStatement prep = connection.prepareStatement(uuidSQL);
            prep.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        String nameSQL = "CREATE TABLE IF NOT EXISTS " + nameTable +
                "(username varchar(64), uuid varchar(64), lastseen datetime);";
        try
        {
            PreparedStatement prep = connection.prepareStatement(nameSQL);
            prep.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        String ipSQL = "CREATE TABLE IF NOT EXISTS " + ipTable +
                "(ip varchar(64), uuid varchar(64), lastseen datetime);";
        try
        {
            PreparedStatement prep = connection.prepareStatement(ipSQL);
            prep.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getDB()
    {
        return connection;
    }

    public boolean isConnected()
    {
        return connected;
    }
}
