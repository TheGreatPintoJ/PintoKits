package me.pintoadmin.pintoKits;


import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

import java.io.*;
import java.util.*;

public class JoinListener implements Listener {
    private final PintoKits plugin;
    private ConfigurationSection kitsSection;

    public JoinListener(PintoKits plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        File startingKitsFile = new File(plugin.getDataFolder(), "startingkit.yml");
        FileConfiguration startingConfig = YamlConfiguration.loadConfiguration(startingKitsFile);
        String startingKit = startingConfig.getString("startingkit");
        String joinKit = startingConfig.getString("joinkit");
        List<String> playersJoined = startingConfig.getStringList("joined");
        kitsSection = plugin.getKitsSection();

        if(event.getPlayer().hasPermission("pintokits.joinkits.disable")) return;

        //JoinKit
        if (loadKit(event.getPlayer(), joinKit)){
            return;
        }

        //StartingKit
        if(!playersJoined.contains(event.getPlayer().getName())){
            loadKit(event.getPlayer(), startingKit);

            playersJoined.add(event.getPlayer().getName());
            startingConfig.set("joined", playersJoined);
            try {
                startingConfig.save(startingKitsFile);
            } catch (IOException e) {
                plugin.getLogger().warning("Could not save startingkit.yml file: "+e.getMessage());
            }
        }
    }

    private boolean loadKit(Player player, String kitName){
        if(kitName == null) return false;
        if (!kitsSection.isConfigurationSection(kitName)) {
            plugin.getLogger().warning("Kit not found: " + kitName);
            return false;
        }
        ConfigurationSection thisKitSection = kitsSection.getConfigurationSection(kitName);
        for(String key : thisKitSection.getKeys(false)) {
            if(key.equalsIgnoreCase("items")) {
                Object items = thisKitSection.get(key);
                ArrayList<?> itemList = (ArrayList<?>) items;
                player.getInventory().setContents(itemList.toArray(new ItemStack[0]));
            }
        }
        return true;
    }
}
