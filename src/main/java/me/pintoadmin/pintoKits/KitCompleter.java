package me.pintoadmin.pintoKits;

import org.bukkit.command.*;

public class KitCompleter implements TabCompleter {
    private final PintoKits plugin;

    public KitCompleter(PintoKits plugin) {
        this.plugin = plugin;
        plugin.getCommand("kit").setTabCompleter(this);
        plugin.getCommand("removekit").setTabCompleter(this);
        plugin.getCommand("savekit").setTabCompleter(this);
    }

    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            java.util.List<String> completions = new java.util.ArrayList<>();
            if (command.getName().equalsIgnoreCase("kit") || command.getName().equalsIgnoreCase("removekit")) {
                if (plugin.getKitsSection() != null) {
                    completions.addAll(plugin.getKitsSection().getKeys(false));
                }
            } else if (command.getName().equalsIgnoreCase("savekit")) {
                completions.add("<kitname>");
            }
            return completions;
        }
        return null;
    }
}
