package fr.lamdis.pog;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.lamdis.pog.capacity.CapacityEffect;
import fr.lamdis.pog.config.advanced.SaveAdvancedConfig;
import fr.lamdis.pog.config.capacity.RequiredLevel;
import fr.lamdis.pog.inventory.InvCreator;
import fr.lamdis.pog.items.capacity.CapacityItems;
import fr.lamdis.pog.items.pog.PogLeveling;
import fr.lamdis.pog.items.pog.PogTool;

public class PogListerner implements Listener {
	
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
    	Boolean send = Pog.CONFIG_PACK.getBoolean("send-resource.active", false);
    	Player p = event.getPlayer();
    	if(send) {
			if (Pog.CONFIG_PACK.getStringList("send-resource.exception").contains(p.getName())) { return; }
    	} else {
			if (!Pog.CONFIG_PACK.getStringList("send-resource.exception").contains(p.getName())) { return; }
    	}
    	p.setResourcePack(Pog.CONFIG_PACK.getString("send-resource.1.14+"));
    }
	
    @EventHandler
    public void onCraft(CraftItemEvent event) {
    	if(event.getWhoClicked() instanceof Player) {
        	Player p = (Player) event.getWhoClicked();
            ItemStack it = event.getCurrentItem();
            if(it != null && it.hasItemMeta() && it.getItemMeta().hasCustomModelData()) {
            	String perm = null;
        		if(PogTool.isItPog(it)) { perm = "pog.craft.pickaxe"; }
        		if (CapacityItems.isBigHole1(it)) { perm = "pog.craft.bighole1"; }
        		if (CapacityItems.isBigHole2(it)) { perm = "pog.craft.bighole2"; }
        		if (CapacityItems.isFurnace(it)) { perm = "pog.craft.furnace"; }
        		if (CapacityItems.isSilkTouch(it)) { perm = "pog.craft.silk-touch"; }
        		if (CapacityItems.isFortune(it)) { perm = "pog.craft.fortune"; }
        		if (CapacityItems.isAog(it)) { perm = "pog.craft.axe-of-the-gods-tool"; }
        		if (CapacityItems.isSog(it)) { perm = "pog.craft.shovel-of-the-gods-tool"; }
            	
            	if(perm != null && !p.hasPermission(perm)) {
                    event.setCancelled(true);
                    p.sendMessage("§cYou do not have permission !");
            	}
            }
    	}
    }
	
	@EventHandler
    public void onCraft2(CraftItemEvent event) { // EMPECHE D'UTILISER DES CAPACITES DANS UN CRAFT
        ItemStack result = event.getInventory().getResult();
        if (result == null || result.getType() != Material.ENDER_EYE) {
            return;
        }

        ItemStack[] ingredients = event.getInventory().getMatrix();
        for (ItemStack ingredient : ingredients) {
        	if(CapacityItems.isCapacity(ingredient)) {
        		event.setCancelled(true);
                event.getView().getPlayer().sendMessage("§r§cThis craft is incorrect !");
                return;
        	}
        }
    }
	
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) { // EMPECHE L'ENDER PEARL D'ETRE UTILISER SI C'EST UNE CAPACITE
        if(CapacityItems.isCapacity(event.getItem())) {
            event.setCancelled(true);
        }
    }
	
	@EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) { // EMPECHE LA POG D'ETRE MISE DANS UNE ENCLUME
		if (PogTool.isItPog(event.getResult())) {
			event.setResult(null);
		}
    }
	
    private final List<Block> handledBlocks = new ArrayList<>();
	
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    	final Player player = event.getPlayer();
    	final Block block = event.getBlock();
    	
    	if (handledBlocks.contains(block)) {
            handledBlocks.remove(block); // Nettoyage
            return;
        }
    	
        ItemStack pog = player.getInventory().getItemInMainHand();
        if(PogTool.isItPog(pog)) {
    		if (player.hasPermission("pog.use.pickaxe")) {
        		// Sans big hole (block du centre)
	            event.setDropItems(false);
                handledBlocks.add(block);
	            BlockBreakEvent ev = new BlockBreakEvent(block, player);
				Bukkit.getPluginManager().callEvent(ev);
				if (!ev.isCancelled()) {
					CapacityEffect.dropsWithEffect(block, pog, player);
	                if(!PogLeveling.isMaxLevel(pog)) {
						if(!xpCancel(event.getBlock())) {
							PogLeveling.addXP(pog, 1);
						}
		        	}
	                PogLeveling.updateLVL(pog);
				}
				
        		if(PogTool.goodForBigHole(pog, player, 0)) {
            		
            		float x = (float) player.getLocation().getDirection().getX();
            		float y = (float) player.getLocation().getDirection().getY();
            		float z = (float) player.getLocation().getDirection().getZ();
            		if(x < 0) {x = -x;}
            		if(y < 0) {y = -y;}
            		if(z < 0) {z = -z;}
            		
            		if(PogTool.goodForBigHole(pog, player, 1)) {
            			for (int X = -1; X <= 1; X++) {
    					    for (int Y = -1; Y <= 1; Y++) {
    					        for (int Z = -1; Z <= 1; Z++) {
    					            Block currentBlock = null;
    					            
    					            if ((x < z && y < z)) {
    					                currentBlock = block.getRelative(X, Y, 0);
    					                Z = 1;
    					            } else if ((x < y && z < y)) {
    					                currentBlock = block.getRelative(X, 0, Z);
    					                Y = 1;
    					            } else if ((z < x && y < x)) {
    					                currentBlock = block.getRelative(0, Y, Z);
    					                X = 1;
    					            }
    					            
        		                    handledBlocks.add(currentBlock);
        		                    
					            	BlockBreakEvent e = new BlockBreakEvent(currentBlock, player);
									Bukkit.getPluginManager().callEvent(e);
									if (currentBlock != null && !bigHoleCancel(currentBlock, pog, player) && !block.getLocation().equals(currentBlock.getLocation())) {
    									if (!e.isCancelled()) {
    										CapacityEffect.dropsWithEffect(currentBlock, pog, player);
    										currentBlock.setType(Material.AIR);
        					                if(Pog.CONFIG_CAPACITY.getBoolean("big-hole-give-xp", false)) {
        					                	if(!PogLeveling.isMaxLevel(pog)) {
            										if(!xpCancel(event.getBlock())) {
            											PogLeveling.addXP(pog, 1);
            										}
            						        	}
        					                }
    									}
									}
    					        }
    					    }
    					}
		                return;
            		}
            		if(PogTool.goodForBigHole(pog, player, 2)) {
            			for (int X = -2; X <= 2; X++) {
    					    for (int Y = -1; Y <= 3; Y++) {
    					        for (int Z = -2; Z <= 2; Z++) {
    					            Block currentBlock = null;

    					            if (x < z) {
    					                if (y < z) {
    					                    currentBlock = block.getRelative(X, Y, 0);
        					                Z = 2;
    					                } else if (z < y) {
    					                    currentBlock = block.getRelative(X, 0, Z);
        					                Y = 3;
    					                }
    					            } else if (z < x) {
    					                if (y < x) {
    					                    currentBlock = block.getRelative(0, Y, Z);
        					                X = 2;
    					                } else if (x < y) {
    					                    currentBlock = block.getRelative(X, 0, Z);
        					                Y = 3;
    					                }
    					            }
        		                    handledBlocks.add(currentBlock);
    					            BlockBreakEvent e = new BlockBreakEvent(currentBlock, player);
									Bukkit.getPluginManager().callEvent(e);
									if (currentBlock != null && !bigHoleCancel(currentBlock, pog, player) && !block.getLocation().equals(currentBlock.getLocation())) {
    									if (!e.isCancelled()) {
    										CapacityEffect.dropsWithEffect(currentBlock, pog, player);
    										currentBlock.setType(Material.AIR);
        					                if(Pog.CONFIG_CAPACITY.getBoolean("big-hole-give-xp", false)) {
        					                	if(!PogLeveling.isMaxLevel(pog)) {
            										if(!xpCancel(event.getBlock())) {
            											PogLeveling.addXP(pog, 1);
            										}
            						        	}
        					                }
    									}
									}
    					        }
    					    }
    					}
		                return;
            		}
        		}
        	} else {
    			event.setCancelled(true);
            	player.sendMessage("§cYou do not have permission !");
    		}
        }
    }
    public Boolean bigHoleCancel(Block b, ItemStack pog, Player p) {
	    Material type = b.getType();
	    List<String> exception = SaveAdvancedConfig.BIG_HOLE_BLOCKS_LIST;
        if (exception.contains(type.toString())) {
			return false;
        } else {
			ItemMeta m = pog.getItemMeta();
			if (SaveAdvancedConfig.AOG_BLOCKS_LIST.contains(b.getType().toString()) || SaveAdvancedConfig.LEAVE_BLOCKS_LIST.contains(b.getType().toString())) {
				if (m.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE) == 1) {
					if (p.hasPermission("pog.use.axe-of-the-gods-tool")) {
						return false;
					}
				}
			} else if(SaveAdvancedConfig.SOG_BLOCKS_LIST.contains(b.getType().toString())) {
				if (m.getEnchantLevel(Enchantment.PROTECTION_FIRE) == 1) {
					if (p.hasPermission("pog.use.shovel-of-the-gods-tool")) {
						return false;
					}
				}
			}
			return true;
		}
	}
	public Boolean xpCancel(Block b) {
	    Material type = b.getType();
	    List<String> noXpPickaxes = Pog.CONFIG.getStringList("pickaxe.no-xp");
        if (noXpPickaxes.contains(type.toString())) {
    	    return true;
        }
	    return false;
	}
	
	
	
	// Capacités :
	
	// Ouvrir l'inventaire

	@EventHandler
    public void onPlayerInteract2(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = event.getPlayer();
        	if(p.isSneaking()) {
				ItemStack pog = p.getEquipment().getItemInMainHand();
				if(PogTool.isItPog(pog)) {
					if (p.hasPermission("pog.use.pickaxe")) {
						if(Pog.CONFIG_CAPACITY.getBoolean("active", false)) {
							event.setCancelled(true);
							Inventory inv = InvCreator.inventoryTool(pog);
							p.openInventory(inv);
							p.updateInventory();
						}
					}
				}
        	}
        }
	}
	
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent event) { 		// Gros Bordel
		if(event.getWhoClicked() instanceof Player) {
			final Player p = (Player) event.getWhoClicked();
			if(p.getOpenInventory().getTitle().startsWith("§rPOG | Capacity")) {
				if(event.getClickedInventory() != null) {
					if(event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
						event.setCancelled(true);
						ItemStack pog = p.getInventory().getItemInMainHand();
						ItemMeta m = pog.getItemMeta();
						if(PogTool.isItPog(pog)) {                                   // Vérifie si c'est toujours une POG en main
							if(event.getSlot() == 13) {
								ItemStack it = event.getCursor();
			    				Inventory inv = event.getClickedInventory();
								if (it != null && it.getType() == Material.ENDER_PEARL) {
									if (it.hasItemMeta() && it.getItemMeta().hasCustomModelData()) {
										if (it.getItemMeta().getCustomModelData() == 1) {
											if(RequiredLevel.forBigHole1() <= PogLeveling.getLVL(pog)) {
												if (m.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 0) {
													addCapacity(it, p, inv, 19, CapacityItems.getCapacityTool(1), pog, m, Enchantment.PROTECTION_ENVIRONMENTAL, 11);
												}
											} else {
												p.sendMessage("§cYou must reach level " + RequiredLevel.forBigHole1() + " of your pickaxe");
											}
										} else if (it.getItemMeta().getCustomModelData() == 2) {
											if(RequiredLevel.forBigHole2() <= PogLeveling.getLVL(pog)) {
												if (m.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 10 && m.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) < 20) {
													addCapacity(it, p, inv, 19, CapacityItems.getCapacityTool(2), pog, m, Enchantment.PROTECTION_ENVIRONMENTAL, 22);
												} else if (m.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 0) {
													p.sendMessage("§cYou must have the first capacity to unlock this one !");
												}
											} else {
												p.sendMessage("§cYou must reach level " + RequiredLevel.forBigHole2() + " of your pickaxe");
											}
										} else if (it.getItemMeta().getCustomModelData() == 3) {
											if(RequiredLevel.forFurnace() <= PogLeveling.getLVL(pog)) {
												if (m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS) == 0) {
													addCapacity(it, p, inv, 25, CapacityItems.getCapacityTool(3), pog, m, Enchantment.PROTECTION_EXPLOSIONS, 11);
												} else if(m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS) > 20 && m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS) < 30) {
													addCapacity(it, p, inv, 25, CapacityItems.getCapacityTool(3), pog, m, Enchantment.PROTECTION_EXPLOSIONS, 31);
												}
											} else {
												p.sendMessage("§cYou must reach level " + RequiredLevel.forFurnace() + " of your pickaxe");
											}
										} else if (it.getItemMeta().getCustomModelData() == 4) {
											if(RequiredLevel.forSilkTouch() <= PogLeveling.getLVL(pog)) {
												if (m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS) == 0) {
													addCapacity(it, p, inv, 25, CapacityItems.getCapacityTool(4), pog, m, Enchantment.PROTECTION_EXPLOSIONS, 21);
													if(m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 11) {
						    							CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 13);
									    				inv.setItem(43, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable :", "§r§c§lSilk Touch active", null));
									    				p.updateInventory();
													}
												} else if(m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS) > 10 && m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS) < 20) {
													addCapacity(it, p, inv, 25, CapacityItems.getCapacityTool(4), pog, m, Enchantment.PROTECTION_EXPLOSIONS, 32);
													if(m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 11) {
														CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 13);
									    				inv.setItem(43, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable :", "§r§c§lSilk Touch active", null));
									    				p.updateInventory();
													}
												}
											} else {
												p.sendMessage("§cYou must reach level " + RequiredLevel.forSilkTouch() + " of your pickaxe");
											}
										} else if (it.getItemMeta().getCustomModelData() == 5) {
											if(RequiredLevel.forFortune() <= PogLeveling.getLVL(pog)) {
												if (m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 0) {
													if(m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 21 || m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 32) {
														addCapacity(it, p, inv, 43, CapacityItems.getCapacityTool(5), pog, m, Enchantment.PROTECTION_FALL, 13);
									    				inv.setItem(43, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable :", "§r§c§lSilk Touch active", null));
													} else {
														addCapacity(it, p, inv, 43, CapacityItems.getCapacityTool(5), pog, m, Enchantment.PROTECTION_FALL, 11);
													}
												}
											} else {
												p.sendMessage("§cYou must reach level " + RequiredLevel.forFortune() + " of your pickaxe");
											}
										} else if (it.getItemMeta().getCustomModelData() == 6) {
											if(RequiredLevel.forAog() <= PogLeveling.getLVL(pog)) {
												if (m.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE) == 0) {
													addCapacity(it, p, inv, 40, CapacityItems.getCapacityTool(6), pog, m, Enchantment.PROTECTION_PROJECTILE, 1);
												} else if (m.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE) == 1) {
													p.sendMessage("§cYou already have this capacity !");
												}
											} else {
												p.sendMessage("§cYou must reach level " + RequiredLevel.forAog() + " of your pickaxe");
											}
										} else if (it.getItemMeta().getCustomModelData() == 7) {
											if(RequiredLevel.forSog() <= PogLeveling.getLVL(pog)) {
												if (m.getEnchantLevel(Enchantment.PROTECTION_FIRE) == 0) {
													addCapacity(it, p, inv, 37, CapacityItems.getCapacityTool(7), pog, m, Enchantment.PROTECTION_FIRE, 1);
												} else if (m.getEnchantLevel(Enchantment.PROTECTION_FIRE) == 1) {
													p.sendMessage("§cYou already have this capacity !");
												}
											} else {
												p.sendMessage("§cYou must reach level " + RequiredLevel.forSog() + " of your pickaxe");
											}
										}
									}
								}
							}
							if(event.getSlot() == 19) {
			    				int lvlPE = m.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
			    				Inventory inv = event.getClickedInventory();
			    				if(lvlPE != 0) {
			    					if(lvlPE > 10) {
			    						if(lvlPE == 11) {
						    				inv.setItem(19, Pog.getItem(Material.GOLDEN_PICKAXE, "§r§b§lBig Hole", "§r§c§lDisable", null, null));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_ENVIRONMENTAL, 12);
			    							return;
				    					}
			    						if(lvlPE == 12) {
						    				inv.setItem(19, CapacityItems.getCapacityTool(1));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_ENVIRONMENTAL, 11);
			    							return;
				    					}
			    					}
			    					if(lvlPE > 20) {
			    						if(lvlPE == 21) {
						    				inv.setItem(19, CapacityItems.getCapacityTool(1));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_ENVIRONMENTAL, 22);
			    							return;
				    					}
			    						if(lvlPE == 22) {
						    				inv.setItem(19, Pog.getItem(Material.GOLDEN_PICKAXE, "§r§b§lBig Hole", "§r§c§lDisable", null, null));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_ENVIRONMENTAL, 23);
			    							return;
				    					}
			    						if(lvlPE == 23) {
						    				inv.setItem(19, CapacityItems.getCapacityTool(2));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_ENVIRONMENTAL, 21);
			    							return;
				    					}
			    					}
			    				}
							}
							if(event.getSlot() == 25) {
			    				int lvl = m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
			    				Inventory inv = event.getClickedInventory();
			    				if(lvl != 0) {
			    					if(lvl > 10) {
			    						if(lvl == 11) {
						    				inv.setItem(25, Pog.getItem(Material.IRON_INGOT, "§r§b§lLoot", "§r§c§lDisable", null, null));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_EXPLOSIONS, 12);
			    							return;
				    					}
			    						if(lvl == 12) {
						    				inv.setItem(25, CapacityItems.getCapacityTool(3));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_EXPLOSIONS, 11);
			    							return;
				    					}
			    					}
			    					if(lvl > 20) {
			    						if(lvl == 21) {
						    				inv.setItem(25, Pog.getItem(Material.IRON_INGOT, "§r§b§lLoot", "§r§c§lDisable", null, null));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_EXPLOSIONS, 22);
			    							if(m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 13) {
			    								CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 11);
							    				inv.setItem(43, CapacityItems.getCapacityTool(5));
			    							}
			    							return;
				    					}
			    						if(lvl == 22) {
						    				inv.setItem(25, CapacityItems.getCapacityTool(4));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_EXPLOSIONS, 21);
			    							if(m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 11) {
			    								CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 13);
							    				inv.setItem(43, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable :", "§r§c§lSilk Touch active", null));
			    							}
			    							return;
				    					}
			    					}
			    					if(lvl > 30) {
			    						if(lvl == 31) {
						    				inv.setItem(25, CapacityItems.getCapacityTool(4));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_EXPLOSIONS, 32);
			    							if(m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 11) {
			    								CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 13);
							    				inv.setItem(43, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable :", "§r§c§lSilk Touch active", null));
			    							}
			    							return;
			    						}
			    						if(lvl == 32) {
						    				inv.setItem(25, Pog.getItem(Material.IRON_INGOT, "§r§b§lLoot", "§r§c§lDisable", null, null));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_EXPLOSIONS, 33);
			    							if(m.getEnchantLevel(Enchantment.PROTECTION_FALL) == 13) {
			    								CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 11);
							    				inv.setItem(43, CapacityItems.getCapacityTool(5));
			    							}
			    							return;
			    						}
			    						if(lvl == 33) {
						    				inv.setItem(25, CapacityItems.getCapacityTool(3));
						    				CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_EXPLOSIONS, 31);
			    							return;
			    						}
			    					}
			    				}
							}
							if(event.getSlot() == 43) {
			    				int lvl = m.getEnchantLevel(Enchantment.PROTECTION_FALL);
			    				int lvlS = m.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
			    				Inventory inv = event.getClickedInventory();
			    				
		    					if(lvl > 10) {
		    						if(lvl == 11) {
		    							CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 12);
					    				inv.setItem(43, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable", null, null));
		    						}
		    						if(lvl == 12) {
		    							if(lvlS == 21 || lvlS == 32) {
		    								CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 13);
						    				inv.setItem(43, Pog.getItem(Material.DIAMOND, "§r§b§lFortune", "§r§c§lDisable :", "§r§c§lSilk Touch active", null));
		    							} else {
		    								CapacityItems.changeCapacity(p, pog, m, Enchantment.PROTECTION_FALL, 11);
						    				inv.setItem(43, CapacityItems.getCapacityTool(5));
		    							}
		    						}
		    					}
							}
						} else {
							p.closeInventory();
							return;
						}
						
					} else if(event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
						if (event.getClick().isShiftClick()) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	public void addCapacity(ItemStack it, Player p, Inventory inv, int slot, ItemStack invIt, ItemStack pog, ItemMeta m, Enchantment enchant, int level) {
		it.setAmount(it.getAmount() - 1);
		p.getOpenInventory().setCursor(it);
		inv.setItem(slot, invIt);
		p.updateInventory();
		CapacityItems.changeCapacity(p, pog, m, enchant, level);
	}
	
	
	@EventHandler
    public void onDrag(InventoryDragEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			final Player p = (Player) event.getWhoClicked();
			if(p.getOpenInventory().getTitle().startsWith("§rPOG | Capacity")) {
				if(event.getRawSlots().contains(13)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	
	
	
	
	
	

	
	@EventHandler
    public void onPlayerStartMining(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
        	Block b = event.getClickedBlock();
            if (b != null) {
            	Material t = b.getType();
            	Player p = event.getPlayer();
        		ItemStack pog = p.getEquipment().getItemInMainHand();
        		if(PogTool.isItPog(pog)) {
        			ItemMeta m = pog.getItemMeta();
    				if(p.hasPermission("pog.use.pickaxe")) {
    					if(SaveAdvancedConfig.SOG_BLOCKS_LIST.contains(b.getType().toString())) {
							if (m.getEnchantLevel(Enchantment.PROTECTION_FIRE) == 1) {
								if (p.hasPermission("pog.use.shovel-of-the-gods-tool")) {

									BlockBreakEvent e = new BlockBreakEvent(b, p);
									Bukkit.getPluginManager().callEvent(e);
									
									if (!e.isCancelled()) {
    									if(t == Material.GRASS_BLOCK) {
        									p.playSound(b.getLocation(), Sound.BLOCK_GRASS_BREAK, 1.0f, 1.0f);
    									}
    									if(t == Material.DIRT || t == Material.CLAY || t == Material.COARSE_DIRT) {
        									p.playSound(b.getLocation(), Sound.BLOCK_WET_GRASS_BREAK, 1.0f, 1.0f);
    									}
										if (t == Material.SNOW || t == Material.SNOW_BLOCK) {
											p.playSound(b.getLocation(), Sound.BLOCK_SNOW_BREAK, 1.0f, 1.0f);
    									}
    									if(t == Material.SAND || t == Material.RED_SAND) {
        									p.playSound(b.getLocation(), Sound.BLOCK_SAND_BREAK, 1.0f, 1.0f);
    									}
    									if(t == Material.GRAVEL) {
        									p.playSound(b.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 1.0f, 1.0f);
    									}
    									b.setType(Material.AIR);
							        }
								}
    						}
    					} else if(SaveAdvancedConfig.AOG_BLOCKS_LIST.contains(b.getType().toString())) {
							if (m.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE) == 1) {
								if (p.hasPermission("pog.use.axe-of-the-gods-tool")) {
									
									BlockBreakEvent e = new BlockBreakEvent(b, p);
									Bukkit.getPluginManager().callEvent(e);
									
									if (!e.isCancelled()) {
    									p.playSound(b.getLocation(), Sound.BLOCK_WOOD_BREAK, 1.0f, 1.0f);
    									b.setType(Material.AIR);
							        }
								}
    						}
    					} else if (SaveAdvancedConfig.LEAVE_BLOCKS_LIST.contains(b.getType().toString())) {
    						if(Pog.CONFIG_CAPACITY.getBoolean("break-leaves-with-god-axe", false)) {
    							if (m.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE) == 1) {
									if (p.hasPermission("pog.use.axe-of-the-gods-tool")) {
    									
    									BlockBreakEvent e = new BlockBreakEvent(b, p);
    									Bukkit.getPluginManager().callEvent(e);
    									
    									if (!e.isCancelled()) {
        									b.setType(Material.AIR);
    							        }
									}
        						}
    						}
    					}
    				}
        		}
            }
        }
    }
	
	
	
	
	
}
