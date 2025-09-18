package me.pintoadmin.pintoKits;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

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
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        String kitName = args[0].toLowerCase();

        ConfigurationSection kitsSection = plugin.getKitsSection();
        if (!kitsSection.isConfigurationSection(kitName)) {
            sender.sendMessage("Kit not found: " + kitName);
            return true;
        }
        ConfigurationSection thisKitSection = kitsSection.getConfigurationSection(kitName);
        for(String key : thisKitSection.getKeys(false)) {
            if(key.equalsIgnoreCase("items")) {
                Object items = thisKitSection.get(key);
                ArrayList<?> itemList = (ArrayList<?>) items;
                ((Player) sender).getInventory().setContents(itemList.toArray(new ItemStack[0]));
            }
        }

        sender.sendMessage(ChatColor.GREEN+"You have been given the kit: " + kitName);
        return true;
    }
}
