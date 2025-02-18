package fr.lamdis.pog.version;

import org.bukkit.Bukkit;

public class BukkitVersion {
	
	public static int getNumber() {
		String v = Bukkit.getBukkitVersion();
		if(v.contains("1.7-") || v.contains("1.7.")) { return 1; }
		if(v.contains("1.8-") || v.contains("1.8.")) { return 2; }
		if(v.contains("1.9-") || v.contains("1.9.")) { return 3; }
		if(v.contains("1.10-") || v.contains("1.10.")) { return 4; }
		if(v.contains("1.11-") || v.contains("1.11.")) { return 5; }
		if(v.contains("1.12-") || v.contains("1.12.")) { return 6; }
		if(v.contains("1.13-") || v.contains("1.13.")) { return 7; }
		if(v.contains("1.14-") || v.contains("1.14.")) { return 8; }
		if(v.contains("1.15-") || v.contains("1.15.")) { return 9; }
		if(v.contains("1.16-") || v.contains("1.16.")) { return 10; }
		if(v.contains("1.17-") || v.contains("1.17.")) { return 11; }
		if(v.contains("1.18-") || v.contains("1.18.")) { return 12; }
		if(v.contains("1.19-") || v.contains("1.19.")) { return 13; }
		if(v.contains("1.20-") || v.contains("1.20.")) { return 14; }
		if(v.contains("1.21-")) { return 15; }
		if(v.contains("1.21.1")) { return 16; }
		if(v.contains("1.21.2")) { return 17; }
		if(v.contains("1.21.3")) { return 18; }
		if(v.contains("1.21.4")) { return 19; }
		return 20;
	}
	
}
