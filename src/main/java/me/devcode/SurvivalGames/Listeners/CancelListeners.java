package me.devcode.SurvivalGames.Listeners;


import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.ArrayList;
import java.util.List;

import me.devcode.SurvivalGames.SurvivalGames;
import me.devcode.SurvivalGames.Utils.Status;

public class CancelListeners implements Listener {
	
	private List<Block> blockPlaced = new ArrayList<>();
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
		if(SurvivalGames.plugin.status != Status.INGAME && SurvivalGames.plugin.status != Status.DEATHMATCH) {
			e.setCancelled(true);
			return;
		}
		}
	}
	
	@EventHandler
	public void onForm(BlockFormEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBurn(BlockBurnEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onTree(StructureGrowEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockGrow(BlockGrowEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onFade(BlockFadeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onLeash(LeavesDecayEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onSpread(BlockSpreadEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(SurvivalGames.plugin.status == Status.TELEPORT) {
			if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ() ) {
				e.setTo(e.getFrom());
			}
		}
		
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if(SurvivalGames.plugin.playerUtils.getSpecs().contains(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}
			  e.setFormat("§7" + e.getPlayer().getDisplayName() + " §8» §f" + e.getMessage());
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		
		e.setCancelled(true);
		}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (SurvivalGames.plugin.status == Status.INGAME || SurvivalGames.plugin.status == Status.DEATHMATCH ||
				SurvivalGames.plugin.status == Status.NODAMAGE) {
			boolean cancel = true;
			for (int blocks : SurvivalGames.plugin.getConfig().getIntegerList("Blocks.Break.Allowed")) {
				if (e.getBlock().getTypeId() == blocks) {
					cancel = false;
					break;
				}
			}
			e.setCancelled(cancel);
			return;
		}
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onAchievment(PlayerAchievementAwardedEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (SurvivalGames.plugin.status == Status.INGAME || SurvivalGames.plugin.status == Status.DEATHMATCH ||
				SurvivalGames.plugin.status == Status.NODAMAGE) {
			boolean cancel = true;
			for (int blocks : SurvivalGames.plugin.getConfig().getIntegerList("Blocks.Place.Allowed")) {
				if (e.getBlock().getTypeId() == blocks) {
					cancel = false;
					break;
				}
			}
			e.setCancelled(cancel);
			SurvivalGames.plugin.chestListeners.getPlaced().add(e.getBlock().getLocation());
			return;
		}
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.getWorld().setThundering(false);
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (SurvivalGames.plugin.status == Status.INGAME || SurvivalGames.plugin.status == Status.DEATHMATCH ||
				SurvivalGames.plugin.status == Status.NODAMAGE) {
	e.setCancelled(false);
			return;
}
e.setCancelled(true);
	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if(SurvivalGames.plugin.playerUtils.getSpecs().contains(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}
		if (SurvivalGames.plugin.status == Status.INGAME || SurvivalGames.plugin.status == Status.DEATHMATCH ||
				SurvivalGames.plugin.status == Status.NODAMAGE) {
				e.setCancelled(false);
			return;
			}
			e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		if(SurvivalGames.plugin.status != Status.INGAME && SurvivalGames.plugin.status != Status.DEATHMATCH) {
			e.setCancelled(true);
			return;
		}
	}
	
}
