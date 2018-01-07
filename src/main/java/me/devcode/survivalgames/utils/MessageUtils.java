package me.devcode.survivalgames.utils;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
@Getter
public class MessageUtils {
	
	private String prefix, death, killPlayer, lobbyCountdown, noMove, ingameCountdown, refill, refilled,
			deathMatch, noteEnoughPlayer, noDamageCountdown, goodLuck, lobbyName,
	serverRestart, win, winner, winnerTitle, serverName, spielerJoin, spielerLeave, distanceToTracked, map;
	private Integer minPlayers = 8;
	private boolean teamsAllowed;
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
		teamsAllowed = getFileConfiguration().getBoolean("Game.TeamsAllowed");
		minPlayers = getFileConfiguration().getInt("Game.MinPlayers");
		prefix = getFileConfiguration().getString("Messages.Prefix").replace("&", "§");
		distanceToTracked = prefix + getFileConfiguration().getString("Messages.DistanceTrackedPlayer").replace("&", "§");
		death = prefix + getFileConfiguration().getString("Messages.Death").replace("&", "§");
		killPlayer = prefix + getFileConfiguration().getString("Messages.KillPlayer").replace("&", "§");
		lobbyCountdown = prefix + getFileConfiguration().getString("Messages.LobbyCountdown").replace("&", "§");
		noMove = prefix + getFileConfiguration().getString("Messages.NoMoveCountdown").replace("&", "§");
		ingameCountdown = prefix + getFileConfiguration().getString("Messages.IngameCountdown").replace("&", "§");
		refill = prefix + getFileConfiguration().getString("Messages.Refill").replace("&", "§");
		refilled = prefix + getFileConfiguration().getString("Messages.Refilled").replace("&", "§");
		deathMatch = prefix + getFileConfiguration().getString("Messages.LobbyCountdown").replace("&", "§");
		noteEnoughPlayer = prefix + getFileConfiguration().getString("Messages.NotEnoughPlayer").replace("&", "§");
		noDamageCountdown = prefix + getFileConfiguration().getString("Messages.NoDamageCountdown").replace("&", "§");
		goodLuck =  prefix +getFileConfiguration().getString("Messages.GoodLuck").replace("&", "§");
		lobbyName = getFileConfiguration().getString("Countdown.LobbyTeleportName");
		serverRestart = prefix + getFileConfiguration().getString("Messages.ServerRestart").replace("&", "§");
		serverName = getFileConfiguration().getString("Scoreboard.ServerName");
		winnerTitle =  getFileConfiguration().getString("Messages.WinnerTitle").replace("&", "§");
		winner =  getFileConfiguration().getString("Messages.Winner").replace("&", "§");
		win =  getFileConfiguration().getString("Messages.Win").replace("&", "§");
		spielerJoin = prefix + getFileConfiguration().getString("Messages.PlayerJoin").replace("&", "§");
		spielerLeave = prefix + getFileConfiguration().getString("Messages.PlayerLeave").replace("&", "§");
	}

}
