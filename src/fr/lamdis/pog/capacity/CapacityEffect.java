package fr.lamdis.pog.capacity;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CapacityEffect {
	
	public static void dropsWithEffect(Block block, ItemStack pog, Player player) {
		if(!player.getGameMode().equals(GameMode.CREATIVE)) {
	        Collection<ItemStack> drops = (Collection<ItemStack>) block.getDrops(new ItemStack(Material.DIAMOND_PICKAXE));
			int lvl = pog.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
			if(lvl == 21 || lvl == 32) {
				drops.clear();
				ItemStack pickaxeSilkTouch = new ItemStack(Material.DIAMOND_PICKAXE);
				pickaxeSilkTouch.addEnchantment(Enchantment.SILK_TOUCH, 1);
				drops = (Collection<ItemStack>) block.getDrops(pickaxeSilkTouch);
			}
			for (ItemStack drop : drops) {
				drop = getCookedVersion(drop, pog, player);
				drop = setFortune(drop, pog, player);
				block.getWorld().dropItemNaturally(block.getLocation(), drop);
			}
		}
	}
	
	
	
	
	private static ItemStack getCookedVersion(ItemStack item, ItemStack pog, Player player) {
		int lvl = pog.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
		if(lvl != 11 && lvl != 31) {
	        return item;
		}
		if(!player.hasPermission("pog.use.furnace") || item == null || item.getType() == Material.AIR) {
	        return item;
		}

	    for (Iterator<Recipe> iterator = Bukkit.recipeIterator(); iterator.hasNext();) {
	        Recipe recipe = iterator.next();

	        if (recipe instanceof FurnaceRecipe) {
	            FurnaceRecipe furnaceRecipe = (FurnaceRecipe) recipe;
	            
	            if (furnaceRecipe.getInput().getType() == item.getType()) {
	                ItemStack result = furnaceRecipe.getResult().clone();
	                result.setAmount(item.getAmount());
	                return result;
	            }
	        }
	    }
	    return item;
	}
	private static ItemStack setFortune(ItemStack item, ItemStack pog, Player player) {
		int lvl = pog.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_FALL);
		if(lvl != 11) {
	        return item;
		}
		if(!player.hasPermission("pog.use.fortune") || item == null || item.getType() == Material.AIR) {
	        return item;
		}
		if(item.getType().isBlock()) {
	        return item;
		}
		
		item.setAmount(item.getAmount() * 2);
		
	    return item;
	}

}
