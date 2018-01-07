package me.devcode.survivalgames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devcode.survivalgames.SurvivalGames;
import me.devcode.survivalgames.utils.Status;


public class Start implements CommandExecutor{
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			
			return true;
		}
		Player player = (Player) sender;
		if(!player.hasPermission("survivalgames.start")) {
			return true;
		}
		if(SurvivalGames.plugin.status != Status.LOBBY) {
			
			return true;
		}
		if(SurvivalGames.plugin.countdownHandler.timer < 10) {
			return true;
		}
		SurvivalGames.plugin.countdownHandler.timer = 11;
		return true;
		
		
	}

}
