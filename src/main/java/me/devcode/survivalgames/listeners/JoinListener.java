package me.devcode.survivalgames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import me.devcode.survivalgames.SurvivalGames;
import me.devcode.survivalgames.utils.Status;

public class JoinListener implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(null);
        player.setExp(0);
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setHealth(20D);
        player.setWalkSpeed((float)0.2);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        player.getActivePotionEffects().forEach(potionEffect ->  {

                player.removePotionEffect(potionEffect.getType());

        });
        if(SurvivalGames.plugin.status == Status.LOBBY) {
            SurvivalGames.plugin.ingameUtils.teleportPlayer(player);

            player.setGameMode(GameMode.SURVIVAL);
            player.setFlying(false);
            player.setAllowFlight(false);
            SurvivalGames.plugin.playerUtils.addPlayer(player);
            SurvivalGames.plugin.playerUtils.addPlayer2(player);
            e.setJoinMessage(SurvivalGames.plugin.messageUtils.getSpielerJoin().replace("%PLAYER%", player.getName()));

            new BukkitRunnable() {

                @Override
                public void run() {
                    SurvivalGames.plugin.methods.createPlayer(player.getUniqueId().toString(), player.getName());
                    SurvivalGames.plugin.mysqlUtils.setMapValues(player);
                    if (SurvivalGames.plugin.playerUtils.getPlayers().size() >= SurvivalGames.plugin.messageUtils.getMinPlayers()) {
                        if (!SurvivalGames.plugin.countdownHandler.started)
                            SurvivalGames.plugin.countdownHandler.onLobby();
                    }
                    cancel();
                }
            }.runTaskAsynchronously(SurvivalGames.plugin);

            return;
        }
        SurvivalGames.plugin.playerUtils.addSpec(player);
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(SurvivalGames.plugin.playerUtils.getPlayers().get(new Random().nextInt(SurvivalGames.plugin.playerUtils.getPlayers().size())));
    }

}
