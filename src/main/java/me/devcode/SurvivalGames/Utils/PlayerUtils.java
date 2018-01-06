package me.devcode.SurvivalGames.Utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

import lombok.Getter;

@Getter
public class PlayerUtils {
	
	private List<Player> players = new ArrayList<>();
	private List<Player> players2 = new ArrayList<>();
	private List<Player> specs = new ArrayList<>();
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}

	
	public void addPlayer2(Player player) {
		players2.add(player);
	}


	public void removePlayer2(Player player) {
		players2.remove(player);
	}
	
	public void addSpec(Player player) {
		specs.add(player);
	}
	
	public void removeSpec(Player player) {
		specs.remove(player);
	}
	

	

}
