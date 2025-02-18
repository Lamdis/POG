package fr.lamdis.pog.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.lamdis.pog.Pog;
import fr.lamdis.pog.config.PogProperties;
import fr.lamdis.pog.items.pog.PogLeveling;
import fr.lamdis.pog.items.pog.PogTool;

public class PluginItems {
	
	public static ItemStack getPogItem(int level) {
		ItemStack pog = new ItemStack(Material.STONE_PICKAXE, 1);
		ItemMeta m = pog.getItemMeta();
		m.setDisplayName(PogProperties.getName());
		m.setCustomModelData(Pog.CMD);
		m.setUnbreakable(true);										// Création de la base de la POG
        m.addEnchant(Enchantment.MENDING, 1, true);
		m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pog.setItemMeta(m);
		
		if(level != 1) {PogLeveling.setLevel(pog, level);}			// Update du niveau de la POG
		
		PogTool.loadLores(pog);
		PogTool.applyMiningSpeed(pog);								// Update des paramètres celon le niveau de la POG
		
		return pog;
	}
	
	public static ItemStack getBigHole1Item() {
		return createCapactityTool("§r§b§lBig Hole I", "§r§fAbility to break", "§r§finto §a3x3", 1);
	}
	
	public static ItemStack getBigHole2Item() {
		return createCapactityTool("§r§b§lBig Hole II", "§r§fAbility to break", "§r§finto §a5x5", 2);
	}
	
	public static ItemStack getFurnaceItem() {
		return createCapactityTool("§r§b§lFurnace Tool", "§r§fCook the", "§r§fbroken blocks", 3);
	}
	
	public static ItemStack getSilkTouchItem() {
		return createCapactityTool("§r§b§lSilk Touch Tool", "§r§fKeep blocks", "§r§funtransformed", 4);
	}
	
	public static ItemStack getFortuneItem() {
		return createCapactityTool("§r§b§lFortune", "§r§fMultiplies", "§r§floot", 5);
	}
	
	public static ItemStack getAogItem() {
		return createCapactityTool("§r§b§lAxe of the Gods", "§r§fBreak the", "§r§fwood", 6);
	}
	
	public static ItemStack getSogItem() {
		return createCapactityTool("§r§b§lShovel of the Gods", "§r§fBreak the dirt", "§r§fand sand", 7);
	}
	
	private static ItemStack createCapactityTool(String name, String lore1, String lore2, int customModelData) {
		ItemStack it = new ItemStack(Material.ENDER_PEARL, 1);
		ItemMeta m = it.getItemMeta();
		m.setDisplayName(name);
		List<String> lore = new ArrayList<>();
		lore.add(lore1);
		lore.add(lore2);
		m.setLore(lore);
		m.setCustomModelData(customModelData);
		it.setItemMeta(m);
		return it;
	}

}
