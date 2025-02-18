package fr.lamdis.pog.config.capacity;

import fr.lamdis.pog.Pog;

public class RequiredLevel {
	
	public static int forBigHole1() {return requiredLevel("big-hole-1");}
	
	public static int forBigHole2() {return requiredLevel("big-hole-2");}
	
	public static int forFurnace() {return requiredLevel("furnace");}
	
	public static int forSilkTouch() {return requiredLevel("silk-touch");}
	
	public static int forFortune() {return requiredLevel("fortune");}
	
	public static int forAog() {return requiredLevel("god-axe");}
	
	public static int forSog() {return requiredLevel("god-shovel");}
	
	private static int requiredLevel(String capacity) {return Pog.CONFIG_CAPACITY.getInt("required-level." + capacity, 1000000000);}

}
