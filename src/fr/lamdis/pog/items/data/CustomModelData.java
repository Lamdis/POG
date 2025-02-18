package fr.lamdis.pog.items.data;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.lamdis.pog.Pog;
import fr.lamdis.pog.config.level.ConfigLevel;
import fr.lamdis.pog.items.pog.PogLeveling;
import fr.lamdis.pog.items.pog.PogTool;

public class CustomModelData {
	
	public static int getData(ItemStack it) {
		if(it != null && it.getType() != null && it.hasItemMeta() && it.getItemMeta().hasCustomModelData()) {
			return it.getItemMeta().getCustomModelData();
		}
		return 0;
	}
	
	public static void setData(ItemStack it, int data) {
		if(data > ConfigLevel.getMaxLevel() * Pog.CMD) { data = ConfigLevel.getMaxLevel() * Pog.CMD; }
		ItemMeta m = null;
		if(it != null && it.getType() != null) { if(it.hasItemMeta()) { m = it.getItemMeta(); } }
		m.setCustomModelData(data);
		it.setItemMeta(m);
		if(PogTool.isItPog(it)) { PogLeveling.updateLVL(it); }
	}
	
	public static void addData(ItemStack it, int data) { setData(it, getData(it) + data); }

}
