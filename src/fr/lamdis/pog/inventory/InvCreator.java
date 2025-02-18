package fr.lamdis.pog.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.lamdis.pog.Pog;
import fr.lamdis.pog.items.capacity.CapacityItems;

public class InvCreator {
	
	public static void updateCapacityInventory(Player player) {
		player.openInventory(inventoryTool(player.getInventory().getItemInMainHand()));
		player.updateInventory();
	}
	
	public static Inventory inventoryTool(ItemStack pog) {
		ItemMeta m = pog.getItemMeta();
		Inventory inv = Bukkit.createInventory(null, 54,"§rPOG | Capacity");
		
		for (int x = 0; x <= 53; x++) {
			if(x != 13 && x != 19 && x != 25 && x != 43 && x != 37 && x != 40 && x != 4) {
				inv.setItem(x, Pog.getItem(Material.GRAY_STAINED_GLASS_PANE, "§r§l§c", null, null, null));
			}
			if(x == 4) {
				inv.setItem(x, Pog.getItem(Material.NETHER_STAR, "§r§c§lCapacity", "§f§lSubmit the", "§f§lcapacities below", null));
			}
			if (x == 19) { // Big Hole
				int lvlPE = m.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
				if(lvlPE == 11 || lvlPE == 22) {
    				inv.setItem(x, CapacityItems.getCapacityTool(1));
				}
				if(lvlPE == 21) {
    				inv.setItem(x, CapacityItems.getCapacityTool(2));
				}
				if(lvlPE <= 10) {
    				inv.setItem(x, Pog.getItem(Material.BARRIER, "§r§c§lBig Hole", "§r§c§lNot unlocked", null, null));
				}
				if(lvlPE == 12 || lvlPE == 23) {
    				inv.setItem(x, Pog.getItem(Material.GOLDEN_PICKAXE, "§r§b§lBig Hole", "§r§c§lDisable", null, null));
				}
			}
			if(x == 25) { // Loot
				int lvlPX = m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
				if(lvlPX == 0) {
    				inv.setItem(x, Pog.getItem(Material.BARRIER, "§r§b§lLoot", "§r§c§lNot unlocked", null, null));
				}
				if(lvlPX == 11 || lvlPX == 31) {
    				inv.setItem(x, CapacityItems.getCapacityTool(3));
				}
				if(lvlPX == 21 || lvlPX == 32) {
    				inv.setItem(x, CapacityItems.getCapacityTool(4));
				}
				if(lvlPX == 12 || lvlPX == 22 || lvlPX == 33) {
    				inv.setItem(x, Pog.getItem(Material.IRON_INGOT, "§r§b§lLoot", "§r§c§lDisable", null, null));
				}
			}
			if(x == 43) { // Fortune
				int lvlPF = m.getEnchantLevel(Enchantment.PROTECTION_FALL);
				if(lvlPF <= 10) {
    				inv.setItem(x, Pog.getItem(Material.BARRIER, "§r§b§lFortune", "§r§c§lNot unlocked", null, null));
				}
				if(lvlPF > 10) {
    				if(lvlPF == 11) {
	    				inv.setItem(x, CapacityItems.getCapacityTool(5));
    				}
    				if(lvlPF == 12) {
	    				inv.setItem(x, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable", null, null));
    				}
    				if(lvlPF == 13) {
	    				inv.setItem(x, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable :", "§r§c§lSilk Touch active", null));
    				}
				}
			}
			if(x == 37) { // Shovel of the gods
				int lvlFi = m.getEnchantLevel(Enchantment.PROTECTION_FIRE);
				if(lvlFi == 0) {
    				inv.setItem(x, Pog.getItem(Material.BARRIER, "§r§b§lShovel", "§r§c§lNot unlocked", null, null));
				}
				if(lvlFi == 1) {
    				inv.setItem(x, CapacityItems.getCapacityTool(7));
				}
			}
			if(x == 40) { // Axe of the gods
				int lvlPr = m.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
				if(lvlPr == 0) {
    				inv.setItem(x, Pog.getItem(Material.BARRIER, "§r§b§lAxe", "§r§c§lNot unlocked", null, null));
				}
				if(lvlPr == 1) {
    				inv.setItem(x, CapacityItems.getCapacityTool(6));
				}
			}
		}
		return inv;
	}


}
