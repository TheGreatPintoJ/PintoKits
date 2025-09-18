package me.pintoadmin.pintoKits;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;

import java.io.*;
import java.util.*;

public class RemoveKitCommand implements CommandExecutor {
    private final PintoKits plugin;

    public RemoveKitCommand(PintoKits plugin) {
        this.plugin = plugin;
        plugin.getCommand("removekit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /removekit <kitname>");
            return true;
        }

        String kitName = args[0].toLowerCase();

        if (plugin.getKitsSection() != null && plugin.getKitsSection().isConfigurationSection(kitName)) {
            File kitsFile = new File(plugin.getDataFolder(), "kits.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(kitsFile);

            ConfigurationSection kitsSection = plugin.getKitsSection();
            if (!kitsSection.isConfigurationSection(kitName)) {
                sender.sendMessage(ChatColor.GREEN+"Kit not found: " + kitName);
                return true;
            }

            config.getConfigurationSection("kits").set(kitName, null);
            try {
                config.save(kitsFile);
            } catch (IOException e) {
                sender.sendMessage("An error occurred while removing the kit.");
                e.printStackTrace();
                return true;
            }
            sender.sendMessage("Kit removed: " + kitName);
        } else {
            sender.sendMessage("Kit not found: " + kitName);
        }
        return true;
    }
}
