package me.devcode.survivalgames.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;

import me.devcode.survivalgames.SurvivalGames;
public class IngameUtils {
        
    private HashMap<Player, Integer> kills = new HashMap<>();
    //Adds kill to a Player
    public void addKill(Player player, Integer kill) {
        if(!kills.containsKey(player))
            kills.put(player, 0);
        kills.put(player, kills.get(player)+0);
    }
    //Teleports a Player to the Lobby
    public void teleportPlayer(Player player) {
        if(SurvivalGames.plugin.getConfig().getString("SurvivalGames.World") == null) {
            return;
        }
        World world = Bukkit.getWorld(SurvivalGames.plugin.getConfig().getString("SurvivalGames.World"));
        double x = SurvivalGames.plugin.getConfig().getDouble("SurvivalGames.X");
        double y = SurvivalGames.plugin.getConfig().getDouble("SurvivalGames.Y");
        double z = SurvivalGames.plugin.getConfig().getDouble("SurvivalGames.Z");
        double yaw = SurvivalGames.plugin.getConfig().getDouble("SurvivalGames.Yaw");
        double pitch = SurvivalGames.plugin.getConfig().getDouble("SurvivalGames.Pitch");
        player.teleport(new Location(world, x, y, z, (float)yaw, (float)pitch));
    }



    private ArrayList<Location> spawnLocs2 = new ArrayList<>();
    //Setting the Deathmatch spawns
    private void onTeleport2() {
        if(SurvivalGames.plugin.getConfig().getConfigurationSection("DSpawn.") == null) {
            for(Player all : Bukkit.getOnlinePlayers()) {
                all.kickPlayer("§cSpawns must be configurated.");
            }
            return;
        }
        for(String s : SurvivalGames.plugin.getConfig().getConfigurationSection("DSpawn").getKeys(false)) {
            World world = Bukkit.getWorld(SurvivalGames.plugin.getConfig().getString("Spawn." + s + ".World"));
            double x = SurvivalGames.plugin.getConfig().getDouble("DSpawn." + s + ".X");
            double y = SurvivalGames.plugin.getConfig().getDouble("DSpawn." + s + ".Y");
            double z = SurvivalGames.plugin.getConfig().getDouble("DSpawn." + s + ".Z");
            float yaw = (float) SurvivalGames.plugin.getConfig().getDouble("DSpawn." + s + ".Yaw");
            float pitch = (float) SurvivalGames.plugin.getConfig().getDouble("DSpawn." + s + ".Pitch");
            spawnLocs2.add(new Location(world, x, y, z, yaw, pitch));
        }
    }
    //Teleporting to DM
    public void onTP2() {
        onTeleport2();
        for(int i = 0; i < SurvivalGames.plugin.playerUtils.getPlayers().size(); i++) {
            Player player = SurvivalGames.plugin.playerUtils.getPlayers().get(i);
            try{
                player.teleport(spawnLocs2.get(i));
            }catch(ArrayIndexOutOfBoundsException e) {
                player.kickPlayer("§cSpawns must be configurated.");
            }
        }
    }

    private ArrayList<Location> spawnLocs = new ArrayList<>();
    //Setting the Ingame spawns
    private void onTeleport() {
        if(SurvivalGames.plugin.getConfig().getConfigurationSection("Spawn.") == null) {
            for(Player all : Bukkit.getOnlinePlayers()) {
                all.kickPlayer("§cSpawns must be configurated.");
            }
            return;
        }
        for(String s : SurvivalGames.plugin.getConfig().getConfigurationSection("Spawn").getKeys(false)) {
            World world = Bukkit.getWorld(SurvivalGames.plugin.getConfig().getString("Spawn." + s + ".World"));
            double x = SurvivalGames.plugin.getConfig().getDouble("Spawn." + s + ".X");
            double y = SurvivalGames.plugin.getConfig().getDouble("Spawn." + s + ".Y");
            double z = SurvivalGames.plugin.getConfig().getDouble("Spawn." + s + ".Z");
            float yaw = (float) SurvivalGames.plugin.getConfig().getDouble("Spawn." + s + ".Yaw");
            float pitch = (float) SurvivalGames.plugin.getConfig().getDouble("Spawn." + s + ".Pitch");
            spawnLocs.add(new Location(world, x, y, z, yaw, pitch));
        }
    }
    //Teleporting to Ingame
    public void onTP() {
        onTeleport();
        for(int i = 0; i < SurvivalGames.plugin.playerUtils.getPlayers().size(); i++) {
            Player player = SurvivalGames.plugin.playerUtils.getPlayers().get(i);
            try{
                player.teleport(spawnLocs.get(i));
            }catch(ArrayIndexOutOfBoundsException e) {
                player.kickPlayer("§cSpawns must be configurated.");
                return;
            }
        }
    }
    //Setting a win
    public void setWin() {
        if(SurvivalGames.plugin.playerUtils.getPlayers().size() == 1) {
            Player winner = SurvivalGames.plugin.playerUtils.getPlayers().get(0);
            winner.sendMessage(SurvivalGames.plugin.messageUtils.getWin());
            SurvivalGames.plugin.mysqlUtils.addWinsByPlayer(winner.getPlayer().getUniqueId().toString());
            Bukkit.getOnlinePlayers().forEach(player -> {

                    if(player != winner) {
                        player.sendMessage(SurvivalGames.plugin.messageUtils.getWinner().replace("%PLAYER%", player.getName()));
                    }
                    TitleApi.sendTitel(player, SurvivalGames.plugin.messageUtils.getWinnerTitle().replace("%PLAYER%", player.getName()));

            });
            SurvivalGames.plugin.countdownHandler.onEnd();
        }
    }

    //Updating a scoreboard
    public HashMap<Player, Scoreboard> getscoreboard = new HashMap<>();
    public void updateScoreboard(Player player) {
        if(SurvivalGames.plugin.status == Status.INGAME) {
        if(getscoreboard.containsKey(player)) {
            Scoreboard board = getscoreboard.get(player);
            int totalSecs = SurvivalGames.plugin.countdownHandler.inGameTimer;
            int minutes =(totalSecs %3600)/60;
            int seconds = totalSecs %60;

            String timeString =String.format("%02d:%02d", minutes, seconds);
            board.getTeam("time").setSuffix("§e" + timeString);
            board.getTeam("kills").setSuffix("§e" + this.kills.get(player));
            board.getTeam("spieler").setSuffix("§e" + SurvivalGames.plugin.playerUtils.getPlayers().size());
            getscoreboard.put(player, board);

            return;

        }
        System.out.println("abceded");
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            System.out.println("dee");
                Objective obj =  board.registerNewObjective("aaa", "bbb");

        obj.setDisplayName("§8Test");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            System.out.println("de");

            int totalSecs = SurvivalGames.plugin.countdownHandler.inGameTimer;
            int minutes =(totalSecs %3600)/60;
            int seconds = totalSecs %60;

            String timeString =String.format("%02d:%02d", minutes, seconds);
            Team time = board.registerNewTeam("time");
            time.setPrefix("§fZeit§8: ");
            time.setSuffix("§e" +timeString);
            time.addEntry("§s");
            obj.getScore("§s").setScore(11);
            obj.getScore("§o").setScore(10);
            Team teams = board.registerNewTeam("teams");
            teams.setPrefix("§fTeams§8: ");

            if(SurvivalGames.plugin.messageUtils.isTeamsAllowed()) {
                teams.setSuffix("§aallowed");
            }else{
                teams.setSuffix("§cforbidden");
            }
            teams.addEntry("§l");
            obj.getScore("§1").setScore(9);
            obj.getScore("§a").setScore(8);
            Team map = board.registerNewTeam("map");
            map.setPrefix("§fMap§8: ");
            map.setSuffix("§e" + SurvivalGames.plugin.messageUtils.getMap());
            map.addEntry("§d");
            obj.getScore("§d").setScore(7);
            obj.getScore("§u").setScore(6);
            Team kills = board.registerNewTeam("kills");
            kills.setPrefix("§fKills§8: ");
            kills.setSuffix("§e" + this.kills.get(player));
            kills.addEntry("§p");
            obj.getScore("§p").setScore(5);
            obj.getScore("§c").setScore(4);
            Team spieler = board.registerNewTeam("spieler");
            spieler.setPrefix("§fSpieler§8: ");
            spieler.setSuffix("§e" + SurvivalGames.plugin.playerUtils.getPlayers().size());
            spieler.addEntry("§o");
            obj.getScore("§o").setScore(3);
            obj.getScore("§b").setScore(2);
            System.out.println("aaaaaaa");
            obj.getScore(SurvivalGames.plugin.messageUtils.getServerName()).setScore(1);
            player.setScoreboard(board);
            System.out.println(board.getEntries() + ":" + board.getPlayers());
            getscoreboard.put(player, board);
            return;
}
        if(SurvivalGames.plugin.status == Status.DEATHMATCH) {
            if(getscoreboard.containsKey(player)) {
                Scoreboard board = getscoreboard.get(player);
                int totalSecs = SurvivalGames.plugin.countdownHandler.deathMatchTimer;
                int minutes =(totalSecs %3600)/60;
                int seconds = totalSecs %60;

                String timeString =String.format("%02d:%02d", minutes, seconds);
                board.getTeam("time").setSuffix("§e" + timeString);
                board.getTeam("kills").setSuffix("§e" + this.kills.get(player));
                board.getTeam("spieler").setSuffix("§e" + SurvivalGames.plugin.playerUtils.getPlayers().size());
                getscoreboard.put(player, board);

                return;

            }
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective obj = board.getObjective("aaa");
            if(board.getObjective("aaa") == null) {
                obj = board.registerNewObjective("aaa", "bbb");
            }else{
                board.getObjective("aaa").unregister();
                obj = board.registerNewObjective("aaa", "bbb");
            }
            obj.setDisplayName("§8» §eSurvivalGames");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);


            int totalSecs = SurvivalGames.plugin.countdownHandler.deathMatchTimer;
            int minutes =(totalSecs %3600)/60;
            int seconds = totalSecs %60;

            String timeString =String.format("%02d:%02d", minutes, seconds);
            Team time = board.registerNewTeam("time");
            time.setPrefix("§fZeit§8: ");
            time.setSuffix("§e" +timeString);
            time.addEntry("§s");
            obj.getScore("§s").setScore(11);
            obj.getScore("§o").setScore(10);
            Team teams = board.registerNewTeam("teams");
            teams.setPrefix("§fTeams§8: ");

            if(SurvivalGames.plugin.messageUtils.isTeamsAllowed()) {
                teams.setSuffix("§aallowed");
            }else{
                teams.setSuffix("§cforbidden");
            }
            teams.addEntry("§l");
            obj.getScore("§1").setScore(9);
            obj.getScore("§a").setScore(8);
            Team map = board.registerNewTeam("map");
            map.setPrefix("§fMap§8: ");
            map.setSuffix("§e" + SurvivalGames.plugin.messageUtils.getMap());
            map.addEntry("§d");
            obj.getScore("§d").setScore(7);
            obj.getScore("§u").setScore(6);
            Team kills = board.registerNewTeam("kills");
            kills.setPrefix("§fKills§8: ");
            kills.setSuffix("§e" + this.kills.get(player));
            kills.addEntry("§p");
            obj.getScore("§p").setScore(5);
            obj.getScore("§c").setScore(4);
            Team spieler = board.registerNewTeam("spieler");
            spieler.setPrefix("§fSpieler§8: ");
            spieler.setSuffix("§e" + SurvivalGames.plugin.playerUtils.getPlayers().size());
            spieler.addEntry("§o");
            obj.getScore("§o").setScore(3);
            obj.getScore("§b").setScore(2);
            obj.getScore(SurvivalGames.plugin.messageUtils.getServerName()).setScore(1);
            player.setScoreboard(board);
            getscoreboard.put(player, board);
            return;
        }
    }
    
}
