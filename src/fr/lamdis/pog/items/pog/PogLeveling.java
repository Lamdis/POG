package fr.lamdis.pog.items.pog;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.lamdis.pog.Pog;
import fr.lamdis.pog.config.level.ConfigLevel;
import fr.lamdis.pog.items.data.CustomModelData;

public class PogLeveling {
	
	public static int getLVL(ItemStack pog) {
	    return Math.min(CustomModelData.getData(pog) / Pog.CMD, Pog.CONFIG_LEVEL.getInt("level.max-level", 20));
	}
	
	public static int getAdvancement(ItemStack pog) {
	    return CustomModelData.getData(pog) - (getLVL(pog) * Pog.CMD);
	}
	
	public static Boolean isMaxLevel(ItemStack pog) {
		if (PogTool.isItPog(pog)) {
			if (getLVL(pog) == ConfigLevel.getMaxLevel()) {
				return true;
			}
		}
		return false;
	}
	
	public static void addLevel(ItemStack pog, int level) {
		if(PogTool.isItPog(pog)) {
			setLevel(pog, getLVL(pog) + level);
		}
	}
	
	public static void addXP(ItemStack pog, int xp) {
		if(PogTool.isItPog(pog)) {
			if(getLVL(pog) != ConfigLevel.getMaxLevel()) {
				setXP(pog, getAdvancement(pog) + xp);
			}
		}
	}
	
	public static void setLevel(ItemStack pog, int level) {
		if(PogTool.isItPog(pog)) {
			if (level < ConfigLevel.getMaxLevel()) {
				CustomModelData.setData(pog, Pog.CMD * level);
			} else {
				CustomModelData.setData(pog, Pog.CMD * ConfigLevel.getMaxLevel());
			}
		}
	}
	
	public static void setXP(ItemStack pog, int xp) {
		if(PogTool.isItPog(pog)) {
			if (xp < ConfigLevel.getRequiredXP(getLVL(pog))) {
				CustomModelData.setData(pog, Pog.CMD * getLVL(pog) + xp);
			} else {
				addLevel(pog, 1);
			}
		}
	}
	
	public static void updateLVL(ItemStack pog) {
		if(ConfigLevel.getRequiredXP(getLVL(pog)) <= getAdvancement(pog)) {
			CustomModelData.setData(pog, (CustomModelData.getData(pog) - getAdvancement(pog)) + Pog.CMD);
		}
		PogTool.loadLores(pog);
		PogTool.applyMiningSpeed(pog);
	}
	
	
	
	
	
	// OLD METHODS
	
	public static void OLDgetLevelUp(ItemStack pog) { // REMPLACER PAR updateLVL
		ItemMeta meta = pog.getItemMeta();
		int customModelData = meta.getCustomModelData();
		int lvl = getLVL(pog);
		if(ConfigLevel.getRequiredXP(lvl) <= getAdvancement(pog)) {
			int cmd = (customModelData - getAdvancement(pog)) + Pog.CMD;
			meta.setCustomModelData(cmd);
			pog.setItemMeta(meta);
		}
	}
	
	public static void OLDupdateLVL(ItemStack pog) { // REMPLACER PAR updateLVL
		ItemMeta m = pog.getItemMeta();
		int lvl = getLVL(pog);
		List<String> lore = new ArrayList<>();
		lore.add("§r§fLevel " + lvl);
		if(lvl != Pog.CONFIG_LEVEL.getInt("level.max-level", 20)) {
			lore.add("§r§fXP : " + getAdvancement(pog) + "/" + ConfigLevel.getRequiredXP(lvl) + " xp");
		}
        m.setLore(lore);
        m.addEnchant(Enchantment.DIG_SPEED, ConfigLevel.getMiningSpeed(lvl), true);
        m.addEnchant(Enchantment.MENDING, lvl, true);
        m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pog.setItemMeta(m);
	}

}
