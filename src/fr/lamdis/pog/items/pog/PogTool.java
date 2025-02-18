package fr.lamdis.pog.items.pog;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.lamdis.pog.Pog;
import fr.lamdis.pog.config.level.ConfigLevel;

public class PogTool {
	
	public static Boolean isItPog(ItemStack it) {
		if (it != null && it.getType() != null && it.getType() == Material.STONE_PICKAXE) {
			ItemMeta m = it.getItemMeta();
			if (m != null && m.hasCustomModelData() && m.getCustomModelData() >= Pog.CMD) {
				return true;
			}
		}
		return false;
	}
	
	public static Boolean goodForBigHole(ItemStack it, Player player, int level) {
		int bg = it.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
		if(level == 0) {
			if(bg > 10) { return true; }
		} else if(level == 1) {
			if(player.hasPermission("pog.use.bighole1")) { if(bg == 11 || bg == 22) { return true; } }
		} else if(level == 2) {
			if(player.hasPermission("pog.use.bighole2")) { if(bg == 21) { return true; } }
		}
		return false;
	}
	
	public static void loadLores(ItemStack pog) {
		if (PogTool.isItPog(pog)) {
			ItemMeta m = pog.getItemMeta();
			List<String> configLore = Pog.CONFIG.getStringList("pickaxe.lore");
			List<String> lore = new ArrayList<>();
			int lvl = PogLeveling.getLVL(pog);
			
			for (String line : configLore) {
			    line = line.replace("%LEVEL%", lvl + "");
				if (line.contains("%XP%") || line.contains("%REQUIRED%")) {
					if(PogLeveling.getLVL(pog) != ConfigLevel.getMaxLevel()) {
					    line = line.replace("%XP%", PogLeveling.getAdvancement(pog) + "");
					    line = line.replace("%REQUIRED%", ConfigLevel.getRequiredXP(lvl) + "");
					} else {
						continue;
					}
				}
			    line = line.replace("&", "§");
			    lore.add(line);
			}
			
			m.setLore(lore);
			pog.setItemMeta(m);
		}
	}
	
	public static void applyMiningSpeed(ItemStack pog) {
		if(PogTool.isItPog(pog)) {
			ItemMeta m = pog.getItemMeta();
	        m.addEnchant(Enchantment.DIG_SPEED, ConfigLevel.getMiningSpeed(PogLeveling.getLVL(pog)), true);
			
			pog.setItemMeta(m);
		}
		
	}

}
