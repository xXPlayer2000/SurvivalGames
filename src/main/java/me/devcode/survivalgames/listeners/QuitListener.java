package me.devcode.survivalgames.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.devcode.survivalgames.SurvivalGames;
import me.devcode.survivalgames.utils.Status;

public class QuitListener implements Listener{

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player player = e.getPlayer();
        if(SurvivalGames.plugin.playerUtils.getSpecs().contains(player)) {
            SurvivalGames.plugin.playerUtils.removeSpec(player);
            return;
        }
        SurvivalGames.plugin.playerUtils.removePlayer(player);

        e.setQuitMessage(SurvivalGames.plugin.messageUtils.getSpielerLeave().replace("%PLAYER", player.getName()));
        if(SurvivalGames.plugin.status == Status.LOBBY) {
            SurvivalGames.plugin.playerUtils.removePlayer2(player);
            return;
        }
        if(SurvivalGames.plugin.status != Status.LOBBY && SurvivalGames.plugin.status != Status.END) {
            if(SurvivalGames.plugin.playerUtils.getPlayers2().contains(player)) {

                //Little cooldown if to many player join at the same moment
                SurvivalGames.plugin.ingameUtils.setWin();
                SurvivalGames.plugin.mysqlUtils.addDeathsByPlayer(player.getUniqueId().toString());
                new BukkitRunnable() {

                        @Override
                        public void run() {

                        SurvivalGames.plugin.mysqlUtils.updateStatsForPlayer(e.getPlayer().getUniqueId().toString());
                        SurvivalGames.plugin.playerUtils.removePlayer2(e.getPlayer());
                        cancel();
                    }
                }.runTaskAsynchronously(SurvivalGames.plugin);

            }
            return;
        }
    }

}
