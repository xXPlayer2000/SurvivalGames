package me.devcode.SurvivalGames.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import me.devcode.SurvivalGames.SurvivalGames;

public class MySQLStats {

	/*
	 * Nicht alle Methoden werden hier benutzt.
	 */

	public boolean getBooleanMethod(String table, String from, String uuid) {

		boolean isIn = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = SurvivalGames.plugin.mysql.prepare("SELECT " + from + " FROM " + table + " WHERE " + from + "=?");
			ps.setString(1, uuid);

			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				if (rs != null)

					isIn = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isIn;
	}

	public int getIntMethod(String table, String from, String uuid, String get) {
		PreparedStatement statement = null;
		if (getBooleanMethod(table, from, uuid)) {
			ResultSet rs = null;
			try {
				statement = SurvivalGames.plugin.mysql.prepare("SELECT " + get + " FROM " + table + " WHERE " + from + "=?");

				statement.setString(1, uuid);
				rs = statement.executeQuery();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			int intmethod = 0;
			try {
				while (rs.next()) {
					if (rs != null) {
						intmethod = rs.getInt(get);

					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return intmethod;
		}
		return 0;
	}

	public long getLongMethod(String table, String from, String uuid, String get) {
		PreparedStatement statement = null;
		if (getBooleanMethod(table, from, uuid)) {
			ResultSet rs = null;
			try {
				statement = SurvivalGames.plugin.mysql.prepare("SELECT " + get + " FROM " + table + " WHERE " + from + "=?");

				statement.setString(1, uuid);
				rs = statement.executeQuery();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			long longmethod = 0;
			try {
				while (rs.next()) {
					if (rs != null) {
						longmethod = rs.getInt(get);

					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return longmethod;
		}
		return 0;
	}

	public String getStringMethod(String table, String from, String uuid, String get) {

		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			statement = SurvivalGames.plugin.mysql.prepare("SELECT " + get + " FROM " + table + " WHERE " + from + "=?");
			statement.setString(1, uuid);
			rs = statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String stringmethod = "";
		try {
			while (rs.next()) {
				if (rs != null) {
					stringmethod = rs.getString(get);

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringmethod;

	}

	public void setIntMethod(String table, String from, String uuid, int setint, String set) {

		if (getBooleanMethod(table, from, uuid)) {
			SurvivalGames.plugin.mysql.update(
					"UPDATE " + table + " SET " + set + "= '" + setint + "' WHERE " + from + "= '" + uuid + "';");
		}
	}

	public void setLongMethod(String table, String from, String uuid, long setlong, String set) {

		if (getBooleanMethod(table, from, uuid)) {
			SurvivalGames.plugin.mysql.update(
					"UPDATE " + table + " SET " + set + "= '" + setlong + "' WHERE " + from + "= '" + uuid + "';");
		}
	}

	public String getList(String table, String from, String uuid, String get) {

		ResultSet rs = null;
		try {
			PreparedStatement ps = SurvivalGames.plugin.mysql
					.prepare("SELECT " + get + " FROM " + table + " WHERE " + from + "=?");
			ps.setString(1, uuid);

			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String stringmethod = "";
		try {
			while (rs.next()) {
				if (rs != null) {
					stringmethod = rs.getString(get);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stringmethod;
	}

	public void setStringMethod(String table, String from, String uuid, String setstring, String set) {

		SurvivalGames.plugin.mysql.update(
				"UPDATE " + table + " SET " + set + "= '" + setstring + "' WHERE " + from + "= '" + uuid + "';");

	}

	public void setListMethod(String table, String from, String uuid, List<String> a, String set) {

		SurvivalGames.plugin.mysql
				.update("UPDATE " + table + " SET " + set + "= '" + a + "' WHERE " + from + "= '" + uuid + "';");

	}

}
