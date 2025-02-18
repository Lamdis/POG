package fr.lamdis.pog.command;

import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.pog.Pog;
import fr.lamdis.pog.config.advanced.SaveAdvancedConfig;
import fr.lamdis.pog.items.PluginItems;
import fr.lamdis.pog.items.pog.PogLeveling;
import fr.lamdis.pog.items.pog.PogTool;
import fr.lamdis.pog.recipe.PogRecipe;

public class Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String msg, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pog")){
			if (args.length == 0) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage("§a/pog give [type] [number] : Give a POG");
					player.sendMessage("§a/pog add [xp or level] [number] : Add XP or Level to the POG");
					player.sendMessage("§a/pog reload : Reload the config");
				}
			}
			if(args[0].equalsIgnoreCase("give")) {
				if(sender instanceof Player) {
					Player player = (Player) sender;

					if(args.length == 1) {
						ItemStack pog = PluginItems.getPogItem(1);
						player.getInventory().addItem(pog);
					} else if(args.length == 2 || args.length == 3) {
						int number = 1;
						if(args.length == 3) {
							try {
					            number = Integer.parseInt(args[2]);
							} catch (NumberFormatException e) {
								number = 1;
							}
						}
						ItemStack it = null;
						if(args[1].equalsIgnoreCase("pickaxe")) {it = PluginItems.getPogItem(1);}
						if(args[1].equalsIgnoreCase("big-hole-1")) {it = PluginItems.getBigHole1Item();}
						if(args[1].equalsIgnoreCase("big-hole-2")) {it = PluginItems.getBigHole2Item();}
						if(args[1].equalsIgnoreCase("furnace")) {it = PluginItems.getFurnaceItem();}
						if(args[1].equalsIgnoreCase("fortune")) {it = PluginItems.getFortuneItem();}
						if(args[1].equalsIgnoreCase("god-axe")) {it = PluginItems.getAogItem();}
						if(args[1].equalsIgnoreCase("god-shovel")) {it = PluginItems.getSogItem();}
						if(args[1].equalsIgnoreCase("silk-touch")) {it = PluginItems.getSilkTouchItem();}
						if (it != null) {
							for(int i = 0; i < number; i++) {
								player.getInventory().addItem(it);
							}
							player.sendMessage("§f" + number + " " + it.getItemMeta().getDisplayName() + "§f has been added to your inventory");
						}
					}
					
				}
			}
			if(args[0].equalsIgnoreCase("add")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if(args.length == 3) {
						ItemStack item = player.getEquipment().getItemInMainHand();
						if(PogTool.isItPog(item)) {
							if(args[1].equalsIgnoreCase("xp")) {
								PogLeveling.addXP(item, Integer.parseInt(args[2]));
								player.sendMessage("§a" + Integer.parseInt(args[2]) + " XP has been added to the pickaxe");
							}
							if(args[1].equalsIgnoreCase("level")) {
								PogLeveling.addLevel(item, Integer.parseInt(args[2]));
								player.sendMessage("§a" + Integer.parseInt(args[2]) + " level has been added to the pickaxe");
							}
						} else {
							player.sendMessage("§cYou must have the POG in your hands");
						}
					} else {
						player.sendMessage("§a/pog add [xp or level] [number]");
					}
				}
			}
			if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
				Pog.PLUGIN.reloadConfig();
				Pog.CONFIG = Pog.PLUGIN.getConfig();
				Pog.CONFIG_CRAFT = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder(), "crafts.yml"));
				Pog.CONFIG_CAPACITY = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder(), "capacity.yml"));
				Pog.CONFIG_LEVEL = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder(), "levels.yml"));
				SaveAdvancedConfig.saveAdvancedConfig();
				Pog.SERVER.resetRecipes();
				PogRecipe.loadRecipe();
				System.out.println("POG : Config reloaded !");
				if(sender instanceof Player) {
					sender.sendMessage("§aConfig reloaded !");
				}
			}
		}
		
		return false;
	}
	
	
	
}
