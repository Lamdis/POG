package fr.lamdis.pog.config;

import fr.lamdis.pog.Pog;

public class PogProperties {
	
	public static String getName() {return Pog.CONFIG.getString("pickaxe.name", "&r&b&lPickaxe of the Gods").replace("&", "§");}
	
}
