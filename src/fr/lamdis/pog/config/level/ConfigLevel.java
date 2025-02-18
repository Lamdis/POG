package fr.lamdis.pog.config.level;

import fr.lamdis.pog.Pog;

public class ConfigLevel {
	
	public static int getMaxLevel() {return Pog.CONFIG_LEVEL.getInt("level.max-level", 20);}
	
	public static int getRequiredXP(int lvl) {return Pog.CONFIG_LEVEL.getInt("level." + lvl + "-to-" + (lvl + 1), 1000);}
	
	public static int getMiningSpeed(int lvl) {return Pog.CONFIG_LEVEL.getInt("dig-speed.level-" + lvl);}
	
}
