package me.devcode.survivalgames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Random;

import me.devcode.survivalgames.SurvivalGames;

public class DeathListener implements Listener{

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {


        Player player = e.getEntity();
        SurvivalGames.plugin.mysqlUtils.addDeathsByPlayer(player.getUniqueId().toString());
        if(player.getKiller() != null) {
            Player killer = player.getKiller();
            e.setDeathMessage(SurvivalGames.plugin.messageUtils.getDeath().replace("%PLAYER%", player.getName()).replace("%KILLER%", killer.getName()));

            SurvivalGames.plugin.ingameUtils.addKill(killer, 0);

            SurvivalGames.plugin.mysqlUtils.addKillsByPlayer(killer.getUniqueId().toString());

        }else{
            e.setDeathMessage(SurvivalGames.plugin.messageUtils.getDeath().replace("%PLAYER%", player.getName()));
        }
        SurvivalGames.plugin.playerUtils.removePlayer(player);

        SurvivalGames.plugin.playerUtils.addSpec(player);
        player.setWalkSpeed((float)0.2);
        player.setGameMode(GameMode.SPECTATOR);
        SurvivalGames.plugin.ingameUtils.setWin();
        Bukkit.getOnlinePlayers().forEach(player2 ->{


                if(player2 != player)
                    player2.hidePlayer(player);

        });



    }


    @EventHandler
    public void onReawn(PlayerRespawnEvent e) {
        Random ran = new Random();
        Player player = SurvivalGames.plugin.playerUtils.getPlayers().get(ran.nextInt(SurvivalGames.plugin.playerUtils.getPlayers().size()));
        e.setRespawnLocation(player.getLocation());
    }

}
