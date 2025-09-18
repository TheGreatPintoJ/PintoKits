package me.pintoadmin.pintoKits;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.inventory.*;

import java.io.*;
import java.util.*;

import static org.bukkit.Bukkit.getLogger;

public class SaveKitCommand implements CommandExecutor {
    private final PintoKits plugin;

    public SaveKitCommand(PintoKits plugin) {
        this.plugin = plugin;
        plugin.getCommand("savekit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof org.bukkit.entity.Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Usage: /savekit <kitname>");
            return true;
        }

        String kitName = args[0].toLowerCase();

        Inventory playerInventory = ((org.bukkit.entity.Player) sender).getInventory();
        File kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(kitsFile);

        HashMap<String, Object> kitData = new HashMap<>();
        kitData.put("items", playerInventory.getContents());
        config.createSection("kits." + kitName, kitData);

        try {
            config.save(kitsFile);
            sender.sendMessage(ChatColor.GREEN+"You have saved the kit: " + kitName);
            plugin.addKitPerms();
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED+"An error occurred while saving the kit.");
            getLogger().severe("Could not save kits.yml: " + e.getMessage());
        }
        return true;
    }
}
