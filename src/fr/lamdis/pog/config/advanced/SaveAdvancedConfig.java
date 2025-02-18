package fr.lamdis.pog.config.advanced;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.lamdis.pog.Pog;

public class SaveAdvancedConfig {
	
	private static FileConfiguration BIG_HOLE_BLOCKS;
	
	private static FileConfiguration SOG_BLOCKS;
	
	private static FileConfiguration AOG_BLOCKS;
	
	private static FileConfiguration LEAVE_BLOCKS;
	
	public static List<String> BIG_HOLE_BLOCKS_LIST;
	
	public static List<String> AOG_BLOCKS_LIST;
	
	public static List<String> SOG_BLOCKS_LIST;
	
	public static List<String> LEAVE_BLOCKS_LIST;
	
	public static void saveAdvancedConfig() {
		File filePog = new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig/big_hole_blocks.yml");
		if (!filePog.exists()) {savePogBlocks();}
		File fileAog = new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig/aog_blocks.yml");
		if (!fileAog.exists()) {saveAogBlocks();}
		File fileSog = new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig/sog_blocks.yml");
		if (!fileSog.exists()) {saveSogBlocks();}
		File fileLeave = new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig/leave_blocks.yml");
		if (!fileLeave.exists()) {saveLeaveBlocks();}
		
		BIG_HOLE_BLOCKS = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "big_hole_blocks.yml"));
		AOG_BLOCKS = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "aog_blocks.yml"));
		SOG_BLOCKS = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "sog_blocks.yml"));
		LEAVE_BLOCKS = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "leave_blocks.yml"));
		
		BIG_HOLE_BLOCKS_LIST = BIG_HOLE_BLOCKS.getStringList("big-hole-blocks");
		AOG_BLOCKS_LIST = AOG_BLOCKS.getStringList("god-axe-blocks");
		SOG_BLOCKS_LIST = SOG_BLOCKS.getStringList("god-shovel-blocks");
		LEAVE_BLOCKS_LIST = LEAVE_BLOCKS.getStringList("god-leave-blocks");
	}
	
	private static void savePogBlocks() {
		BIG_HOLE_BLOCKS = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "big_hole_blocks.yml"));
		
		BIG_HOLE_BLOCKS.set("big-hole-blocks", big_hole_blocks_default);
        try {
        	BIG_HOLE_BLOCKS.save(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "big_hole_blocks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private static void saveAogBlocks() {
		AOG_BLOCKS = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "aog_blocks.yml"));
		
		AOG_BLOCKS.set("god-axe-blocks", axe_blocks_default);
        try {
        	AOG_BLOCKS.save(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "aog_blocks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private static void saveSogBlocks() {
		SOG_BLOCKS = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "sog_blocks.yml"));
		
		SOG_BLOCKS.set("god-shovel-blocks", shovel_blocks_default);
        try {
        	SOG_BLOCKS.save(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "sog_blocks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private static void saveLeaveBlocks() {
		LEAVE_BLOCKS = YamlConfiguration.loadConfiguration(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "leave_blocks.yml"));
		
		LEAVE_BLOCKS.set("god-leave-blocks", leaves_blocks_default);
        try {
        	LEAVE_BLOCKS.save(new File(Pog.PLUGIN.getDataFolder() + "/AdvancedConfig", "leave_blocks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private static final List<String> big_hole_blocks_default = Arrays.asList("STONE", "GRANITE", "DIORITE", "ANDESITE", "SANDSTONE", "DEEPSLATE", "TUFF", 
			"COBBLESTONE", "NETHERRACK", "END_STONE", "BASALT", "POLISHED_BASALT", "STONE_BRICKS", "TERRACOTTA", "ORANGE_TERRACOTTA", "WHITE_TERRACOTTA",
			"LIGHT_GRAY_TERRACOTTA", "GRAY_TERRACOTTA", "BLACK_TERRACOTTA", "BROWN_TERRACOTTA", "RED_TERRACOTTA", "ORANGE_TERRACOTTA", "YELLOW_TERRACOTTA", 
			"LIME_TERRACOTTA", "GREEN_TERRACOTTA", "CYAN_TERRACOTTA", "LIGHT_BLUE_TERRACOTTA", "BLUE_TERRACOTTA", "PURPLE_TERRACOTTA", "MAGENTA_TERRACOTTA", 
			"PINK_TERRACOTTA", "LIGHT_GRAY_TERRACOTTA", "GRAY_TERRACOTTA");
	
	private static final List<String> axe_blocks_default = Arrays.asList("OAK_LOG", "SPRUCE_LOG", "BIRCH_LOG", "JUNGLE_LOG", "ACACIA_LOG", 
		    "DARK_OAK_LOG", "MANGROVE_LOG", "BAMBOO_BLOCK", "CHERRY_LOG", "CRIMSON_STEM", "WARPED_STEM", "STRIPPED_OAK_LOG", "STRIPPED_SPRUCE_LOG", 
		    "STRIPPED_BIRCH_LOG", "STRIPPED_JUNGLE_LOG", "STRIPPED_ACACIA_LOG", "STRIPPED_DARK_OAK_LOG", "STRIPPED_MANGROVE_LOG", "STRIPPED_CHERRY_LOG", 
		    "STRIPPED_CRIMSON_STEM", "STRIPPED_WARPED_STEM", "OAK_WOOD", "SPRUCE_WOOD", "BIRCH_WOOD", "JUNGLE_WOOD", "ACACIA_WOOD", "DARK_OAK_WOOD", "MANGROVE_WOOD", 
		    "CHERRY_WOOD", "CRIMSON_HYPHAE", "WARPED_HYPHAE", "STRIPPED_OAK_WOOD", "STRIPPED_SPRUCE_WOOD", "STRIPPED_BIRCH_WOOD", "STRIPPED_JUNGLE_WOOD", 
		    "STRIPPED_ACACIA_WOOD", "STRIPPED_DARK_OAK_WOOD", "STRIPPED_MANGROVE_WOOD", "STRIPPED_CHERRY_WOOD", "STRIPPED_CRIMSON_HYPHAE", "STRIPPED_WARPED_HYPHAE", 
		    "OAK_PLANKS", "SPRUCE_PLANKS", "BIRCH_PLANKS", "JUNGLE_PLANKS", "ACACIA_PLANKS", "DARK_OAK_PLANKS", "MANGROVE_PLANKS", "CHERRY_PLANKS", "CRIMSON_PLANKS", 
		    "WARPED_PLANKS", "OAK_SLAB", "SPRUCE_SLAB", "BIRCH_SLAB", "JUNGLE_SLAB", "ACACIA_SLAB", "DARK_OAK_SLAB", "MANGROVE_SLAB", "CHERRY_SLAB", "CRIMSON_SLAB", 
		    "WARPED_SLAB", "OAK_STAIRS", "SPRUCE_STAIRS", "BIRCH_STAIRS", "JUNGLE_STAIRS", "ACACIA_STAIRS", "DARK_OAK_STAIRS", "MANGROVE_STAIRS", "CHERRY_STAIRS", 
		    "CRIMSON_STAIRS", "WARPED_STAIRS", "OAK_FENCE", "SPRUCE_FENCE", "BIRCH_FENCE", "JUNGLE_FENCE", "ACACIA_FENCE", "DARK_OAK_FENCE", "MANGROVE_FENCE", 
		    "CHERRY_FENCE", "CRIMSON_FENCE", "WARPED_FENCE", "OAK_FENCE_GATE", "SPRUCE_FENCE_GATE", "BIRCH_FENCE_GATE", "JUNGLE_FENCE_GATE", "ACACIA_FENCE_GATE", 
		    "DARK_OAK_FENCE_GATE", "MANGROVE_FENCE_GATE", "CHERRY_FENCE_GATE", "CRIMSON_FENCE_GATE", "WARPED_FENCE_GATE", "OAK_DOOR", "SPRUCE_DOOR", 
		    "BIRCH_DOOR", "JUNGLE_DOOR", "ACACIA_DOOR", "DARK_OAK_DOOR", "MANGROVE_DOOR", "CHERRY_DOOR", "CRIMSON_DOOR", "WARPED_DOOR", "OAK_TRAPDOOR", "SPRUCE_TRAPDOOR", 
		    "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OAK_TRAPDOOR", "MANGROVE_TRAPDOOR", "CHERRY_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR", 
		    "OAK_BUTTON", "SPRUCE_BUTTON", "BIRCH_BUTTON", "JUNGLE_BUTTON", "ACACIA_BUTTON", "DARK_OAK_BUTTON", "MANGROVE_BUTTON", "CHERRY_BUTTON", "CRIMSON_BUTTON", 
		    "WARPED_BUTTON", "OAK_PRESSURE_PLATE", "SPRUCE_PRESSURE_PLATE", "BIRCH_PRESSURE_PLATE", "JUNGLE_PRESSURE_PLATE", "ACACIA_PRESSURE_PLATE", "DARK_OAK_PRESSURE_PLATE", 
		    "MANGROVE_PRESSURE_PLATE", "CHERRY_PRESSURE_PLATE", "CRIMSON_PRESSURE_PLATE", "WARPED_PRESSURE_PLATE", "OAK_SIGN", "SPRUCE_SIGN", "BIRCH_SIGN", "JUNGLE_SIGN", 
		    "ACACIA_SIGN", "DARK_OAK_SIGN", "MANGROVE_SIGN", "CHERRY_SIGN", "CRIMSON_SIGN", "WARPED_SIGN", "OAK_WOODEN_SLAB", "SPRUCE_WOODEN_SLAB", "BIRCH_WOODEN_SLAB", 
		    "JUNGLE_WOODEN_SLAB", "ACACIA_WOODEN_SLAB", "DARK_OAK_WOODEN_SLAB", "MANGROVE_WOODEN_SLAB", "CHERRY_WOODEN_SLAB", "CRIMSON_WOODEN_SLAB", "WARPED_WOODEN_SLAB");
	
	private static final List<String> shovel_blocks_default = Arrays.asList("GRASS_BLOCK", "DIRT", "COARSE_DIRT", "PODZOL", "ROOTED_DIRT", "MYCELIUM", "SAND", "RED_SAND", "GRAVEL", 
			"CLAY", "SNOW", "SNOW_BLOCK", "SOUL_SAND", "SOUL_SOIL", "MUD", "MUDDY_MANGROVE_ROOTS", "FARMLAND", "DIRT_PATH", "SUSPICIOUS_SAND", "POWDER_SNOW");
	
	private static final List<String> leaves_blocks_default = Arrays.asList("OAK_LEAVES", "SPRUCE_LEAVES", "BIRCH_LEAVES", "JUNGLE_LEAVES", "ACACIA_LEAVES", "DARK_OAK_LEAVES", 
			"MANGROVE_LEAVES", "CHERRY_LEAVES", "AZALEA_LEAVES", "FLOWERING_AZALEA_LEAVES");
	
}
