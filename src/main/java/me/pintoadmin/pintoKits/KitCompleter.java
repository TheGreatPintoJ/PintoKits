package me.pintoadmin.pintoKits;

import org.bukkit.command.*;

import java.util.*;

public class KitCompleter implements TabCompleter {
    private final PintoKits plugin;

    public KitCompleter(PintoKits plugin) {
        this.plugin = plugin;
        plugin.getCommand("kit").setTabCompleter(this);
        plugin.getCommand("removekit").setTabCompleter(this);
        plugin.getCommand("savekit").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            if (command.getName().equalsIgnoreCase("kit") || command.getName().equalsIgnoreCase("removekit")) {
                if (plugin.getKitsSection() != null) {
                    completions.addAll(plugin.getKitsSection().getKeys(false));
                }
            } else if (command.getName().equalsIgnoreCase("savekit")) {
                completions.add("<kitname>");
            }
            if(!completions.isEmpty())
                return completions;
        } else if(args.length == 2){
            if(command.getLabel().equalsIgnoreCase("savekit")){
                return List.of("add", "replace");
            }
        }
        return null;
    }
}
