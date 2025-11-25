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

        if(!startingConfig.getStringList("players").contains(event.getPlayer().getName())){
            ConfigurationSection kitsSection = plugin.getKitsSection();
            if (!kitsSection.isConfigurationSection("startingkit")) {
                plugin.getLogger().warning("Kit not found: " + "startingkit");
                return;
            }
            ConfigurationSection thisKitSection = kitsSection.getConfigurationSection("startingkit");
            for(String key : thisKitSection.getKeys(false)) {
                if(key.equalsIgnoreCase("items")) {
                    Object items = thisKitSection.get(key);
                    ArrayList<?> itemList = (ArrayList<?>) items;
                    event.getPlayer().getInventory().setContents(itemList.toArray(new ItemStack[0]));
                }
            }
            startingConfig.set("players", ((List<String>) startingConfig.get("players")).add(event.getPlayer().getName()));
            try {
                startingConfig.save(startingKitsFile);
            } catch (IOException e) {
                plugin.getLogger().warning("Could not save startingkit.yml file: "+e.getMessage());
            }
        }
    }
}
