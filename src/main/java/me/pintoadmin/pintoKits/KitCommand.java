package me.pintoadmin.pintoKits;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.io.*;
import java.util.*;

public class KitCommand implements CommandExecutor {
    private final PintoKits plugin;

    public KitCommand(PintoKits plugin) {
        this.plugin = plugin;
        plugin.getCommand("kit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW+"Available kits:");
            for(String kitName : plugin.getKitsSection().getKeys(false)) {
                sender.sendMessage(ChatColor.GREEN + "- " + kitName);
            }
            return true;
        }
        if(!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        String kitName = args[0].toLowerCase();

        if(!sender.hasPermission("pintokits.kit." + kitName) && !sender.hasPermission("pintokits.kit.*")) {
            sender.sendMessage(ChatColor.RED+"You do not have permission to use this kit.");
            return true;
        }

        ConfigurationSection kitsSection = plugin.getKitsSection();
        if (!kitsSection.isConfigurationSection(kitName)) {
            sender.sendMessage("Kit not found: " + kitName);
            return true;
        }

        File kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(kitsFile);

        ConfigurationSection thisKitSection = kitsSection.getConfigurationSection(kitName);
        String kitType = thisKitSection.getString("operationtype", "add");
        if(kitType.equalsIgnoreCase("add")){
            config.set("kits."+kitName+".operationtype", "add");
            try {
                config.save(kitsFile);
            } catch (IOException e) {
                plugin.getLogger().warning("Could not save kits file: "+e.getMessage());
            }
        }
        for(String key : thisKitSection.getKeys(false)) {
            if(key.equalsIgnoreCase("items")) {
                Object items = thisKitSection.get(key);
                ArrayList<ItemStack> itemStacks = (ArrayList<ItemStack>) items;
                if(itemStacks == null) return true;
                if(kitType.equalsIgnoreCase("replace")) {
                    player.getInventory().setContents(itemStacks.toArray(new ItemStack[0]));
                } else if(kitType.equalsIgnoreCase("add")){
                    for(ItemStack item : itemStacks) {
                        if(item == null) continue;
                        player.getInventory().addItem(item);
                    }
                }
            }
        }

        sender.sendMessage(ChatColor.GREEN+"You have been given the kit: " + kitName);
        return true;
    }
}
