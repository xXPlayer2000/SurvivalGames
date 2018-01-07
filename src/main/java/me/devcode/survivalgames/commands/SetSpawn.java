package me.devcode.survivalgames.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devcode.survivalgames.SurvivalGames;

public class SetSpawn implements CommandExecutor{


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {

			return true;
		}
		Player player = (Player) sender;
		if(!player.hasPermission("survivalgames.admin")) {
			return true;

		}
		if(args.length == 0) {
			player.sendMessage("/setspawn <Number>");
			return true;
		}
		try{
			Integer.valueOf(args[0]);
		}catch(NumberFormatException e) {
			player.sendMessage("/setspawn <Number");
			return true;
		}

		World world = player.getWorld();
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getLocation().getYaw();
		double pitch = player.getLocation().getPitch();
		SurvivalGames.plugin.getConfig().set("Spawn."+ args[0] +".World", world.getName());
		SurvivalGames.plugin.getConfig().set("Spawn."+ args[0] +".X", x);
		SurvivalGames.plugin.getConfig().set("Spawn."+ args[0] +".Y", y);
		SurvivalGames.plugin.getConfig().set("Spawn."+ args[0] +".Z", z);
		SurvivalGames.plugin.getConfig().set("Spawn."+ args[0] +".Yaw", yaw);
		SurvivalGames.plugin.getConfig().set("Spawn."+ args[0] +".Pitch", pitch);
		SurvivalGames.plugin.saveConfig();
		player.sendMessage("You set the spawn for " + args[0]);
		return true;


	}

}
