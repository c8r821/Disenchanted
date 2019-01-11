package me.haileykins.disenchanted.listeners;

import me.haileykins.disenchanted.Disenchanted;
import me.haileykins.disenchanted.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateListener implements Listener {

    private Disenchanted plugin;
    private ConfigUtils configUtils;

    public UpdateListener(Disenchanted plugin, ConfigUtils configUtils) {
        this.plugin = plugin;
        this.configUtils = configUtils;
    }

    private final String apiURL = "https://api.spigotmc.org/legacy/update.php?resource=63127/";
    private final String resourceURL = "https://www.spigotmc.org/resources/disenchanted.63127/";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!configUtils.updaterEnabled) {
            return;
        }

        Player player = event.getPlayer();

        if (player.hasPermission("disenchanted.update")) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    HttpsURLConnection connection = (HttpsURLConnection) new URL(apiURL).openConnection();
                    String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

                    if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                        player.sendMessage(configUtils.prefColor(configUtils.updateMessage)
                                .replace("{url}", resourceURL));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}