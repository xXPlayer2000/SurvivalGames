package me.devcode.survivalgames.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.function.Consumer;

import me.devcode.survivalgames.SurvivalGames;
import me.devcode.survivalgames.utils.Status;

public class LoginListener implements Listener{

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(SurvivalGames.plugin.status == Status.LOBBY) {
            if(e.getPlayer().hasPermission("survivalgames.join")) {
                SurvivalGames.plugin.playerUtils.getPlayers().forEach(new Consumer<Player>() {
                    @Override
                    public void accept(Player player) {
                        if(!player.hasPermission("survivalgames.join")) {
                            player.kickPlayer("§cYou got kicked for a Premium-User.");
                            e.allow();
                            return;
                        }
                    }
                });
                return;
            }
            return;
        }
        if (SurvivalGames.plugin.status == Status.END) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, null);
            return;
        }
        if(SurvivalGames.plugin.status != Status.LOBBY) {
            e.allow();
            return;
        }
    }

}
