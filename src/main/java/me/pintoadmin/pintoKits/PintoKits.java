package me.pintoadmin.pintoKits;

import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class PintoKits extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveKitsConfig();

        new SaveKitCommand(this);
        new KitCommand(this);
        new RemoveKitCommand(this);
        new KitCompleter(this);

        addKitPerms();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigurationSection getKitsSection() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(this.getDataFolder().toPath().resolve("kits.yml").toFile());
        if (!config.isConfigurationSection("kits")) {
            config.createSection("kits");
            this.saveConfig();
        }
        return config.getConfigurationSection("kits");
    }

    public void saveKitsConfig() {
        try {
            saveResource("kits.yml", false);
            saveResource("startingkit.yml", false);
        } catch (Exception e) {
            getLogger().severe("Could not save kits.yml: " + e.getMessage());
        }
    }

    public void addKitPerms(){
        if(getServer().getPluginManager().getPermission("pintokits.kit.*") == null)
            getServer().getPluginManager().addPermission(new org.bukkit.permissions.Permission("pintokits.kit.*"));

        if(getKitsSection() != null) {
            for (String kitName : getKitsSection().getKeys(false)) {
                if(getServer().getPluginManager().getPermission("pintokits.kit." + kitName.toLowerCase()) == null)
                    getServer().getPluginManager().addPermission(new org.bukkit.permissions.Permission("pintokits.kit." + kitName.toLowerCase()));
            }
        }
    }
}
