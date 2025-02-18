package fr.lamdis.pog.recipe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import fr.lamdis.pog.Pog;
import fr.lamdis.pog.items.PluginItems;
import fr.lamdis.pog.items.capacity.CapacityItems;

public class PogRecipe {
	
	public static void loadRecipe() {
		List<String> items = Arrays.asList("bighole1", "bighole2", "furnacetool", "silktouchtool", "fortunetool", "godaxe", "godshovel", "pog");
		
		for (int i = 0; i < items.size(); i++) {
			if(Pog.CONFIG_CRAFT.getBoolean(items.get(i) + ".active", true)) {
				if(i == 7) {
					createRecipe(PluginItems.getPogItem(1), "pog.table", "pog.ingredients", "pog");
				} else {
		            createRecipe(CapacityItems.getCapacityTool(i + 1), items.get(i) + ".table", items.get(i) + ".ingredients", items.get(i));
				}
			}
        }
	}
	
	private static void createRecipe(ItemStack it, String t, String i, String id) {
		List<String> table = Pog.CONFIG_CRAFT.getStringList(t);
		Map<Character, Material> ingre = new HashMap<>();
		ConfigurationSection ingredients = Pog.CONFIG_CRAFT.getConfigurationSection(i);
		for (String key : ingredients.getKeys(false)) {
			  Material material = Material.valueOf(ingredients.getString(key));
			  ingre.put(key.charAt(0), material);
		}
		NamespacedKey key = new NamespacedKey(Pog.PLUGIN, id);
		ShapedRecipe recipe = new ShapedRecipe(key, it);
		recipe.shape(table.toArray(new String[0]));
		for (Map.Entry<Character, Material> entry : ingre.entrySet()) {
			recipe.setIngredient(entry.getKey(), entry.getValue());
		}
        Pog.SERVER.addRecipe(recipe);
	}
	
}
