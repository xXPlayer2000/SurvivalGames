package me.devcode.SurvivalGames.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.devcode.SurvivalGames.SurvivalGames;
import me.devcode.SurvivalGames.Utils.PlayerUtils;
import me.devcode.SurvivalGames.Utils.Status;

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

        e.setQuitMessage(SurvivalGames.plugin.messageUtils.getSpielerleave().replace("%PLAYER", player.getName()));
        if(SurvivalGames.plugin.status == Status.LOBBY) {
            SurvivalGames.plugin.playerUtils.removePlayer2(player);
            return;
        }
        if(SurvivalGames.plugin.status != Status.LOBBY && SurvivalGames.plugin.status != Status.END) {
            if(SurvivalGames.plugin.playerUtils.getPlayers2().contains(player)) {
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                //Little cooldown if to many player join at the same moment
                SurvivalGames.plugin.ingameUtils.setWin();
                SurvivalGames.plugin.mysqlUtils.addDeathsByPlayer(player.getUniqueId().toString());
                executorService.execute(() -> {
                SurvivalGames.plugin.mysqlUtils.updateStatsForPlayer(e.getPlayer().getUniqueId().toString());
                 SurvivalGames.plugin.playerUtils.removePlayer2(e.getPlayer());
                    executorService.shutdownNow();
                });
            }
            return;
        }
    }

}
