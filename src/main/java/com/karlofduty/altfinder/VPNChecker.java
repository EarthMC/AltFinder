package com.karlofduty.altfinder;

import com.karlofduty.altfinder.callbacks.VPNCallback;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VPNChecker
{
    public static void checkPlayerAsync(String ip, VPNCallback callback)
    {
        // Run outside of the tick loop
        Bukkit.getScheduler().runTaskAsynchronously(AltFinder.getInstance(), () ->
        {
            String result = "ERROR";
            try
            {
                URL myURL = new URL("http://v2.api.iphub.info/ip/" + ip);
                HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-Key", ConfigValues.getString("vpnshield.api-key"));
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.connect();

                BufferedReader br;
                if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
                    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                }
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null)
                {
                    sb.append(output);
                }
                result = sb.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            final String finalResult = result;
            // Go back to the tick loop
            Bukkit.getScheduler().runTask(AltFinder.getInstance(), () ->
            {
                // Call the callback with the result
                callback.onCheckDone(finalResult);
            });
        });
    }

}
