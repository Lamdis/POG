package fr.lamdis.pog.items.capacity;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.lamdis.pog.items.PluginItems;
import fr.lamdis.pog.items.data.CustomModelData;

public class CapacityItems {
	
	public static ItemStack getCapacityTool(int customModelData) {
		if (customModelData == 1) { return PluginItems.getBigHole1Item();}
		if (customModelData == 2) { return PluginItems.getBigHole2Item(); }
		if (customModelData == 3) { return PluginItems.getFurnaceItem(); }
		if (customModelData == 4) { return PluginItems.getSilkTouchItem(); }
		if (customModelData == 5) { return PluginItems.getFortuneItem(); }
		if (customModelData == 6) { return PluginItems.getAogItem(); }
		if (customModelData == 7) { return PluginItems.getSogItem(); }
		return PluginItems.getBigHole1Item();
	}
	
	public static void changeCapacity(Player p, ItemStack pog, ItemMeta m, Enchantment e, int lvl) {						// Change la capacité de la POG
		m.addEnchant(e, lvl, true);
		pog.setItemMeta(m);
		p.getInventory().setItemInMainHand(pog);
		p.updateInventory();
	}
	
	public static Boolean isBigHole1(ItemStack it) { if(isCapacity(it)) { if (CustomModelData.getData(it) == 1) { return true; }} return false; }
	public static Boolean isBigHole2(ItemStack it) { if(isCapacity(it)) { if (CustomModelData.getData(it) == 2) { return true; }} return false; }
	public static Boolean isFurnace(ItemStack it) { if(isCapacity(it)) { if (CustomModelData.getData(it) == 3) { return true; }} return false; }
	public static Boolean isSilkTouch(ItemStack it) { if(isCapacity(it)) { if (CustomModelData.getData(it) == 4) { return true; }} return false; }
	public static Boolean isFortune(ItemStack it) { if(isCapacity(it)) { if (CustomModelData.getData(it) == 5) { return true; }} return false; }
	public static Boolean isAog(ItemStack it) { if(isCapacity(it)) { if (CustomModelData.getData(it) == 6) { return true; }} return false; }
	public static Boolean isSog(ItemStack it) { if(isCapacity(it)) { if (CustomModelData.getData(it) == 7) { return true; }} return false; }
	
	public static Boolean isCapacity(ItemStack it) {
		if (it != null && it.getType() != null && it.getType() == Material.ENDER_PEARL && it.hasItemMeta() && it.getItemMeta().hasCustomModelData()) {
			int cmd = it.getItemMeta().getCustomModelData();
			if (cmd == 1 || cmd == 2 || cmd == 3 || cmd == 4 || cmd == 5 || cmd == 6 || cmd == 7) {
				return true;
			}
        }
    	return false;
	}

}
