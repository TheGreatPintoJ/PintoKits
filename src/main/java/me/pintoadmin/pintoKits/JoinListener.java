package me.pintoadmin.pintoKits;


import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

import java.io.*;
import java.util.*;

public class JoinListener implements Listener {
    private final PintoKits plugin;

    public JoinListener(PintoKits plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        File startingKitsFile = new File(plugin.getDataFolder(), "startingkit.yml");
        FileConfiguration startingConfig = YamlConfiguration.loadConfiguration(startingKitsFile);
        String startingKit = startingConfig.getString("startingkit");

        List<String> playersJoined = startingConfig.getStringList("joined");

        if(!playersJoined.contains(event.getPlayer().getName())){

            ConfigurationSection kitsSection = plugin.getKitsSection();
            if (!kitsSection.isConfigurationSection(startingKit)) {
                plugin.getLogger().warning("Kit not found: " + startingKit);
                return;
            }

            ConfigurationSection thisKitSection = kitsSection.getConfigurationSection(startingKit);
            for(String key : thisKitSection.getKeys(false)) {
                if(key.equalsIgnoreCase("items")) {
                    Object items = thisKitSection.get(key);
                    ArrayList<?> itemList = (ArrayList<?>) items;
                    event.getPlayer().getInventory().setContents(itemList.toArray(new ItemStack[0]));
                }
            }
            playersJoined.add(event.getPlayer().getName());
            startingConfig.set("joined", playersJoined);
            try {
                startingConfig.save(startingKitsFile);
            } catch (IOException e) {
                plugin.getLogger().warning("Could not save startingkit.yml file: "+e.getMessage());
            }
        }
    }
}
