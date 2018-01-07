package me.devcode.survivalgames.utils;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
@Getter
public class MessageUtils {
	
	private String prefix, death, killplayer, lobbycountdown, nomove, ingamecountdown, refill, refilled, deathmatch, notenoughplayer, noDamagecountdown, goodluck, lobbyname,
	serverrestart, win, winner, winnertitle, servername, spielerjoin, spielerleave, distancetotracked, map;
	private Integer minplayers = 8;
	private boolean teamsallowed;
	private File file = new File("plugins/survivalgames", "ingame.yml");
	private FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

	public MessageUtils() {
		getFileConfiguration().addDefault("Messages.Prefix", "&8[&bSurvivalGames&8] ");
		getFileConfiguration().addDefault("Messages.Death", "&6%PLAYER% died.");
		getFileConfiguration().addDefault("Messages.KillPlayer", "&6%KILLER% killed %PLAYER%.");
		getFileConfiguration().addDefault("Messages.LobbyCountdown", "&6Teleport in %SECONDS%.");
		getFileConfiguration().addDefault("Messages.NoMoveCountdown", "&6Start in %SECONDS%.");
		getFileConfiguration().addDefault("Messages.IngameCountdown", "&6Deathmatch starts in %SECONDS%.");
		getFileConfiguration().addDefault("Messages.Refill", "&6Refill in 30 Seconds.");
		getFileConfiguration().addDefault("Messages.Refilled", "&6Refilled.");
		getFileConfiguration().addDefault("Messages.DeathmatchCountdown", "&6Game ends in %SECONDS%.");
		getFileConfiguration().addDefault("Messages.NotEnoughPlayer", "&cNot enough player");
		getFileConfiguration().addDefault("Messages.NoDamageCountdown", "&6No damage for %SECONDS% seconds.");
		getFileConfiguration().addDefault("Messages.GoodLuck", "&bGood Luck!");
		getFileConfiguration().addDefault("Countdown.LobbyTeleportName", "Lobby");
		getFileConfiguration().addDefault("Messages.ServerRestart", "&6Server restart in %SECONDS%.");
		getFileConfiguration().addDefault("Messages.Win", "&6Win!");
		getFileConfiguration().addDefault("Messages.Winner", "&6%PLAYER% is the Winner!");
		getFileConfiguration().addDefault("Messages.WinnerTitle", "&6%PLAYER% is the Winner!");
		getFileConfiguration().addDefault("Scoreboard.ServerName", "YourDomain");;
		getFileConfiguration().addDefault("Messages.PlayerJoin", "&6%PLAYER% has joined.");
		getFileConfiguration().addDefault("Messages.PlayerLeave", "&6%PLAYER% has left.");
		getFileConfiguration().addDefault("Messages.DistanceTrackedPlayer", "&6%DISTANCE% to %PLAYER%.");
		getFileConfiguration().addDefault("Game.MinPlayers", 8);
		getFileConfiguration().addDefault("Game.TeamsAllowed", false);
		getFileConfiguration().addDefault("Game.Map", "MAPNAME");
		getFileConfiguration().options().copyDefaults(true);
		try {
			getFileConfiguration().save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		map = getFileConfiguration().getString("Game.Map");
		teamsallowed = getFileConfiguration().getBoolean("Game.TeamsAllowed");
		minplayers = getFileConfiguration().getInt("Game.MinPlayers");
		prefix = getFileConfiguration().getString("Messages.Prefix").replace("&", "§");
		distancetotracked = prefix + getFileConfiguration().getString("Messages.DistanceTrackedPlayer").replace("&", "§");
		death = prefix + getFileConfiguration().getString("Messages.Death").replace("&", "§");
		killplayer = prefix + getFileConfiguration().getString("Messages.KillPlayer").replace("&", "§");
		lobbycountdown = prefix + getFileConfiguration().getString("Messages.LobbyCountdown").replace("&", "§");
		nomove = prefix + getFileConfiguration().getString("Messages.NoMoveCountdown").replace("&", "§");
		ingamecountdown = prefix + getFileConfiguration().getString("Messages.IngameCountdown").replace("&", "§");
		refill = prefix + getFileConfiguration().getString("Messages.Refill").replace("&", "§");
		refilled = prefix + getFileConfiguration().getString("Messages.Refilled").replace("&", "§");
		deathmatch = prefix + getFileConfiguration().getString("Messages.LobbyCountdown").replace("&", "§");
		notenoughplayer = prefix + getFileConfiguration().getString("Messages.NotEnoughPlayer").replace("&", "§");
		noDamagecountdown = prefix + getFileConfiguration().getString("Messages.NoDamageCountdown").replace("&", "§");
		goodluck =  prefix +getFileConfiguration().getString("Messages.GoodLuck").replace("&", "§");
		lobbyname = getFileConfiguration().getString("Countdown.LobbyTeleportName");
		serverrestart = prefix + getFileConfiguration().getString("Messages.ServerRestart").replace("&", "§");
		servername = getFileConfiguration().getString("Scoreboard.ServerName");
		winnertitle =  getFileConfiguration().getString("Messages.WinnerTitle").replace("&", "§");
		winner =  getFileConfiguration().getString("Messages.Winner").replace("&", "§");
		win =  getFileConfiguration().getString("Messages.Win").replace("&", "§");
		spielerjoin = prefix + getFileConfiguration().getString("Messages.PlayerJoin").replace("&", "§");
		spielerleave = prefix + getFileConfiguration().getString("Messages.PlayerLeave").replace("&", "§");
	}

}
