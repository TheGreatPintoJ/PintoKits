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
            FileConfiguration config = YamlConfiguration.loadConfiguration(this.getDataFolder().toPath().resolve("kits.yml").toFile());
            config.save(this.getDataFolder().toPath().resolve("kits.yml").toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
