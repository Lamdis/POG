package fr.lamdis.pog.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandTabCompleter implements TabCompleter {

	@Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        
        suggestions.addAll(Arrays.asList("give", "add"));

        if (args.length == 1) {
            suggestions.addAll(Arrays.asList("give", "add", "reload"));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                suggestions.clear();
                suggestions.addAll(Arrays.asList("furnace", "silk-touch", "fortune", "god-axe", "god-shovel", "big-hole-2", "big-hole-1", "pickaxe"));
            } else if (args[0].equalsIgnoreCase("add")) {
                suggestions.clear();
                suggestions.addAll(Arrays.asList("xp", "level"));
            }
		} else {
			suggestions.clear();
		}
        return suggestions;
    }

}
