package me.devcode.SurvivalGames.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devcode.SurvivalGames.Countdowns.CountdownHandler;
import me.devcode.SurvivalGames.SurvivalGames;
import me.devcode.SurvivalGames.Utils.Status;


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
