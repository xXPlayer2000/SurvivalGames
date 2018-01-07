package me.devcode.survivalgames.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devcode.survivalgames.SurvivalGames;

public class SetLobby implements CommandExecutor{
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		if(!player.hasPermission("survivalgames.admin")) {
			return true;
		}
		
			World world = player.getWorld();
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			double yaw = player.getLocation().getYaw();
			double pitch = player.getLocation().getPitch();
			SurvivalGames.plugin.getConfig().set("SurvivalGames.World", world.getName());
			SurvivalGames.plugin.getConfig().set("SurvivalGames.X", x);
			SurvivalGames.plugin.getConfig().set("SurvivalGames.Y", y);
			SurvivalGames.plugin.getConfig().set("SurvivalGames.Z", z);
			SurvivalGames.plugin.getConfig().set("SurvivalGames.Yaw", yaw);
			SurvivalGames.plugin.getConfig().set("SurvivalGames.Pitch", pitch);
			SurvivalGames.plugin.saveConfig();
			player.sendMessage("Lobby set.");
			return true;
		
		
	}

}
