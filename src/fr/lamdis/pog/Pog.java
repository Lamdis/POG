package fr.lamdis.pog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import fr.lamdis.pog.command.Command;
import fr.lamdis.pog.command.CommandTabCompleter;
import fr.lamdis.pog.config.advanced.SaveAdvancedConfig;
import fr.lamdis.pog.recipe.PogRecipe;
import fr.lamdis.pog.version.BukkitVersion;



public class Pog extends JavaPlugin {
	
	public static Pog PLUGIN;
	
	public static FileConfiguration CONFIG;
	
	public static FileConfiguration CONFIG_CRAFT;
	
	public static FileConfiguration CONFIG_LEVEL;
	
	public static FileConfiguration CONFIG_CAPACITY;
	
	public static FileConfiguration CONFIG_PACK;
	
	public static Server SERVER;
	
	public static int CMD = 100000;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		String configVersion = getConfig().getString("config-version", null);
		if (!"1.2.0".equals(configVersion)) {
		    String chemin;
		    if (configVersion == null) {
		        chemin = getDataFolder() + "/oldConfig/config-0.0/";
		    } else {
		        chemin = getDataFolder() + "/oldConfig/config-" + configVersion + "/";
		    }

		    File dossier = new File(chemin);
		    Path p = dossier.toPath();
		    
		    if (!dossier.exists()) {
		        if (dossier.mkdirs()) {
		            try {
		                File[] files = getDataFolder().listFiles();
		                if (files != null) {
		                    for (File file : files) {
		                        if (file.isDirectory() && file.getName().equals("oldConfig")) {
		                            continue;
		                        }
		                        Files.move(file.toPath(), p.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
		                    }
		                }
			            System.out.println("§aThe old POG configuration was saved in << " + chemin + " >>");
			            System.out.println("§c§lWARNING : Old POGs will level up!"); 						// ONLY FOR 1.2.0 !
		                saveDefaultConfig();

		            } catch (IOException e) {
		                System.err.println("Error : " + e.getMessage());
		            }
		        } else {
		            System.out.println("§cFailed to create a backup");
		        }
		    } else {
		        try {
		            File[] files = getDataFolder().listFiles();
		            if (files != null) {
		                for (File file : files) {
		                    if (file.isDirectory() && file.getName().equals("oldConfig")) {
		                        continue;
		                    }
		                    Files.move(file.toPath(), p.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
		                }
		            }
		            System.out.println("§aThe old POG configuration was saved in << " + chemin + " >>");
		            saveDefaultConfig();
		        } catch (IOException e) {
		            System.err.println("Error : " + e.getMessage());
		        }
		    }
		}

		File craftFile = new File(getDataFolder(), "crafts.yml");
		File levelFile = new File(getDataFolder(), "levels.yml");
		File capacityFile = new File(getDataFolder(), "capacity.yml");
		File packFile = new File(getDataFolder(), "resource-pack.yml");
		
		if(!craftFile.exists()) { saveResource("crafts.yml", true); }
		if(!levelFile.exists()) { saveResource("levels.yml", true); }
		if(!capacityFile.exists()) { saveResource("capacity.yml", true); }
		if(!packFile.exists()) { saveResource("resource-pack.yml", true); }
		
		PLUGIN = this;
		CONFIG = PLUGIN.getConfig();
		CONFIG_CRAFT = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "crafts.yml"));
		CONFIG_LEVEL = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "levels.yml"));
		CONFIG_CAPACITY = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "capacity.yml"));
		CONFIG_PACK = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "resource-pack.yml"));
		CMD = getConfig().getInt("custom_model_data_per_level", 100000);
		SERVER = PLUGIN.getServer();
		
		SaveAdvancedConfig.saveAdvancedConfig();
		
		getServer().getPluginManager().registerEvents(new PogListerner(), this);
		getCommand("pog").setExecutor(new Command());
		getCommand("pog").setTabCompleter(new CommandTabCompleter());
		
		PogRecipe.loadRecipe();
		
		if (BukkitVersion.getNumber() <= 9) {
			if (getConfig().getString("pickaxe.ingredients.B") != null && getConfig().getString("pickaxe.ingredients.B") == "NETHERITE_PICKAXE") {
				getConfig().set("pickaxe.ingredients.B", "GOLDEN_PICKAXE");
				saveConfig();
			}
			if (getConfig().getString("capacity.big-hole.big-hole-2.ingredients.C") != null && getConfig().getString("capacity.big-hole.big-hole-2.ingredients.C") == "NETHERITE_PICKAXE") {
				getConfig().set("capacity.big-hole.big-hole-2.ingredients.C", "GOLDEN_PICKAXE");
				saveConfig();
			}
		}
		
		System.out.println("POG is a successful start !");
		if(BukkitVersion.getNumber() < 8) {
            System.out.println("§c§lWARNING : The POG plugin is not compatible with versions of Minecraft lower than 1.14 !");
		}
	}
	
	@Override
	public void onDisable() {
		System.out.println("POG is to stop !");
	}
	
	public static ItemStack getItem(Material material, String name, String lore1, String lore2, ItemFlag itemflag) {		// Créer un item
		ItemStack it = new ItemStack(material, 1);
		ItemMeta meta = it.getItemMeta();
		if(name != null) {meta.setDisplayName(name);}
		List<String> lore = new ArrayList<>();
		if(lore1 != null) {lore.add(lore1);}
		if(lore2 != null) {lore.add(lore2);}
		meta.setLore(lore);
		if(itemflag != null) {meta.addItemFlags(itemflag);}
		it.setItemMeta(meta);
		
		return it;
	}
	

}
