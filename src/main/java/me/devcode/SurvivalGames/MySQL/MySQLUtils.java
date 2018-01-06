package me.devcode.SurvivalGames.MySQL;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class MySQLUtils extends MySQLMethods{

	private HashMap<String, Integer> kills = new HashMap<>();
	private HashMap<String, Integer> deaths = new HashMap<>();
	private HashMap<String, Integer> wins = new HashMap<>();
	private HashMap<String, Integer> spiele = new HashMap<>();
	private HashMap<String, Integer> rank = new HashMap<>();
	private HashMap<String, String> name = new HashMap<>();
	public void updateStatsForPlayer(String uuid) {
		setAllMethod("SurvivalGames", "UUID", uuid, name.get(uuid), getKillsByPlayer(uuid), getDeathsByPlayer(uuid), getWinsByPlayer(uuid), getSpieleByPlayer(uuid));

	}

	public void setMapValues(Player player) {
		kills.put(player.getUniqueId().toString(), getKills(player.getUniqueId().toString()));
		deaths.put(player.getUniqueId().toString(), getDeaths(player.getUniqueId().toString()));
		wins.put(player.getUniqueId().toString(), getWins(player.getUniqueId().toString()));
		spiele.put(player.getUniqueId().toString(), getGames(player.getUniqueId().toString()));
		rank.put(player.getUniqueId().toString(), getRank("SurvivalGames", "UUID", player.getUniqueId().toString(), "WINS"));
		name.put(player.getUniqueId().toString(), player.getName());
	}

	public void addKillsByPlayer(String uuid) {
		kills.put(uuid, getKillsByPlayer(uuid)+1);
	}

	public void addDeathsByPlayer(String uuid) {
		deaths.put(uuid, getDeathsByPlayer(uuid)+1);
	}

	public void addWinsByPlayer(String uuid) {
		wins.put(uuid, getWinsByPlayer(uuid)+1);
	}

	public Integer getRankByPlayer(String uuid) {
		if(rank.containsKey(uuid)) {
			return rank.get(uuid);
		}
		return 0;
	}

	public void addSpieleByPlayer(String uuid, Integer spiele) {
		this.spiele.put(uuid, getSpieleByPlayer(uuid)+spiele);
	}

	public Integer getWinsByPlayer(String uuid) {
		if(wins.containsKey(uuid)) {
			return wins.get(uuid);
		}
		return 0;
	}

	public Integer getSpieleByPlayer(String uuid) {
		if(spiele.containsKey(uuid)) {
			return spiele.get(uuid);
		}
		return 0;
	}

	public Integer getKillsByPlayer(String uuid) {
		if(kills.containsKey(uuid)) {
			return kills.get(uuid);
		}
		return 0;
	}

	public Integer getDeathsByPlayer(String uuid) {
		if(deaths.containsKey(uuid)) {
			return deaths.get(uuid);
		}
		return 0;
	}
}
