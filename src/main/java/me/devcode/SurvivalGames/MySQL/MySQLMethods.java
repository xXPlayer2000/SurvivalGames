package me.devcode.SurvivalGames.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import me.devcode.SurvivalGames.SurvivalGames;
import me.devcode.SurvivalGames.SurvivalGames;

public class MySQLMethods {

	public void createPlayer(String uuid, String name) {

		if (!SurvivalGames.plugin.stats.getBooleanMethod("SurvivalGames", "UUID", uuid)) {

			PreparedStatement createPlayer = SurvivalGames.plugin.mysql
					.prepare("INSERT INTO SurvivalGames(UUID, NAME, KILLS, DEATHS, WINS, GAMES) VALUES (?, ?, ?, ?, ?, ?);");
			try {
				createPlayer.setString(1, uuid);
				createPlayer.setString(2, name);
				createPlayer.setInt(3, 0);
				createPlayer.setInt(4, 0);
				createPlayer.setInt(5, 0);
				createPlayer.setInt(6, 0);
				createPlayer.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				createPlayer.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}


	public int getRank(String table, String from, String uuid, String get) {
		int count = 0;
		if(SurvivalGames.plugin.stats.getBooleanMethod(table, from, uuid)) {
			ResultSet rs = null;
			try{

				PreparedStatement statement = SurvivalGames.plugin.mysql.prepare("SELECT * FROM " + table + " ORDER BY WINS DESC");
				rs = statement.executeQuery();
				while(rs.next()) {


					count++;
					String nameduuid = rs.getString("UUID");
					UUID uuid1 = UUID.fromString(nameduuid);
					try {
						statement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(uuid1.toString().equals(uuid)) {
						return count;
					}
				}
			}catch(SQLException e) {

			}
		}
		return count;
	}

	public Integer getGames(String uuid) {
		if(!SurvivalGames.plugin.stats.getBooleanMethod("SurvivalGames", "UUID", uuid)) {

			return 0;
		}
		return SurvivalGames.plugin.stats.getIntMethod("SurvivalGames", "UUID",uuid,"GAMES");
	}

	public Integer getWins(String uuid) {
		if(!SurvivalGames.plugin.stats.getBooleanMethod("SurvivalGames", "UUID", uuid)) {

			return 0;
		}
		return SurvivalGames.plugin.stats.getIntMethod("SurvivalGames", "UUID",uuid,"WINS");
	}

	public Integer getKills(String uuid) {
		if(!SurvivalGames.plugin.stats.getBooleanMethod("SurvivalGames", "UUID", uuid)) {

			return 0;
		}
		return SurvivalGames.plugin.stats.getIntMethod("SurvivalGames", "UUID",uuid,"KILLS");
	}

	public Integer getDeaths(String uuid) {
		if(!SurvivalGames.plugin.stats.getBooleanMethod("SurvivalGames", "UUID", uuid)) {

			return 0;
		}
		return SurvivalGames.plugin.stats.getIntMethod("SurvivalGames", "UUID",uuid,"DEATHS");
	}
	public static HashMap<Integer, String> rang = new HashMap<>();
	public static HashMap<Integer, String> WINS = new HashMap<>();

	public void setRanks() {
		try {
			PreparedStatement ps = SurvivalGames.plugin.mysql.prepare("SELECT name FROM SurvivalGames ORDER BY WINS DESC LIMIT 10");
			ResultSet rs =ps.executeQuery();
			int i = 0;
			while(rs.next()) {
				i++;


				rang.put(i, rs.getString("NAME"));
				WINS.put(i, SurvivalGames.plugin.stats.getStringMethod("SurvivalGames", "NAME", rang.get(i), "WINS"));
			}
			ps.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAllMethod(String table, String from, String uuid,String name, Integer kills, Integer deaths, Integer wins, Integer games) {
		if (!SurvivalGames.plugin.stats.getBooleanMethod("SurvivalGames", "UUID", uuid)) {
			createPlayer(uuid, name);
			setAllMethod(table, from, uuid, name, kills, deaths, wins, games);
			return;
		}
		PreparedStatement statement = SurvivalGames.plugin.mysql.prepare(
				"UPDATE " + table + " SET NAME = ?, KILLS = ?, DEATHS = ?, WINS = ?, GAMES = ? WHERE " + from + "= ?;");
		try {
			statement.setString(1,name);
			statement.setInt(2, kills);

			statement.setInt(3, deaths);
			statement.setInt(4, wins);
			statement.setInt(5, games);
			statement.setString(6, uuid);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
