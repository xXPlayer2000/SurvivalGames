package me.devcode.survivalgames.countdown;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.devcode.survivalgames.SurvivalGames;
import me.devcode.survivalgames.utils.Status;

public class CountdownHandler {
    public int timer = 61;
    public boolean started = false;

    /**
     * All Countdowns for SurvivalGames
     */
    public void onLobby() {
        started = true;

                new  BukkitRunnable() {
                    @Override public void run() {
                      if(timer <= timer) {
                          timer--;
                          SurvivalGames.plugin.playerUtils.getPlayers().forEach(player -> {

                                  player.setLevel(timer);
                                  player.setExp((float) timer / 60);


                          });
                          if(SurvivalGames.plugin.playerUtils.getPlayers().size() < SurvivalGames.plugin.messageUtils.getMinPlayers()) {
                             Bukkit.getOnlinePlayers().forEach((all) -> {
                                  all.setExp(0);
                                  all.setLevel(0);
                                  all.sendMessage(SurvivalGames.plugin.messageUtils.getNoteEnoughPlayer());
                              });
                              started = false;
                              timer = 61;
                             cancel();
                              return;
                          }
                      }
                      if(timer == 60 || timer == 30  || timer == 10 || timer <= 5 && timer >0) {
                          SurvivalGames.plugin.playerUtils.getPlayers().forEach((player ->


                                  player.sendMessage(SurvivalGames.plugin.messageUtils.getLobbyCountdown().replace("%SECONDS%", String.valueOf(timer)))));

                      }
                        if(timer == 0) {
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach((player) -> {
                                    player.setLevel(timer);
                                    player.setExp((float)timer/60);
                                    player.sendMessage(SurvivalGames.plugin.messageUtils.getGoodLuck());
                                    SurvivalGames.plugin.mysqlUtils.addSpieleByPlayer(player.getUniqueId().toString(), 1);
                                    SurvivalGames.plugin.ingameUtils.addKill(player, 0);

                            });
                            SurvivalGames.plugin.ingameUtils.onTP();
                            onNoMove();
                           cancel();
                        }
                    }
                }.runTaskTimer(SurvivalGames.plugin, 0,20);
    }

    public void onNoMove() {
        SurvivalGames.plugin.status = Status.TELEPORT;

                new BukkitRunnable() {
                    int timer = 1;
                    @Override public void run() {
                        if(timer <= timer) {
                            timer--;

                        }
                        if(timer == 25 || timer == 20 || timer == 10 || timer <= 5 && timer >0)
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach((player ->

                                    player.sendMessage(SurvivalGames.plugin.messageUtils.getNoMove().replace("%SECONDS%", String.valueOf(timer)))));

                        if(timer == 0) {
                        onNoDamage();

                        }
                    }
                }.runTaskTimer(SurvivalGames.plugin, 0,20);
    }

    public void onNoDamage() {

                new BukkitRunnable() {
                    int timer = 5;
                    @Override public void run() {
                        if(timer <= timer) {
                            timer--;
                        }
                        if(timer == 20  || timer == 10 || timer <= 5 && timer >0)
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach((player ->

                                    player.sendMessage(SurvivalGames.plugin.messageUtils.getNoDamageCountdown().replace("%SECONDS%", String.valueOf(timer)))));


                        if(timer == 0) {
                            onInGame();
                      cancel();
                        }
                    }
                }.runTaskTimer(SurvivalGames.plugin,0,20);
    }
    public int inGameTimer = 1500;

    public void onInGame() {

        SurvivalGames.plugin.status = Status.INGAME;

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (inGameTimer <= inGameTimer) {
                            inGameTimer--;
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach((player ->
                                    SurvivalGames.plugin.ingameUtils.updateScoreboard(player)));

                            //Refill after time
                            if(inGameTimer == 750) {
                                SurvivalGames.plugin.playerUtils.getPlayers().forEach((player ->
                                       player.sendMessage(SurvivalGames.plugin.messageUtils.getRefilled());
                                SurvivalGames.plugin.chestListeners.getSgChests().clear();
                            }

                            if(inGameTimer == 120) {
                                SurvivalGames.plugin.playerUtils.getPlayers().forEach((player ->
                                        player.sendMessage(SurvivalGames.plugin.messageUtils.getRefilled());
                                SurvivalGames.plugin.chestListeners.getSgChests().clear();
                            }

                            if (inGameTimer == 0) {
                                SurvivalGames.plugin.playerUtils.getPlayers().forEach((player ->

                                        SurvivalGames.plugin.ingameUtils.getscoreboard.remove(player)));
                                        SurvivalGames.plugin.ingameUtils.onTP2();
                                onDeathMatch();
                                cancel();

                            }
                        }
                    }
                }.runTaskTimer(SurvivalGames.plugin, 0,20);
    }
    public int deathMatchTimer = 601;
    public void onDeathMatch() {
        SurvivalGames.plugin.status = Status.DEATHMATCH;

                new BukkitRunnable() {

                    @Override public void run() {
                        if (deathMatchTimer <= deathMatchTimer) {
                            deathMatchTimer--;
                            Bukkit.getOnlinePlayers().forEach((player ->

                                    SurvivalGames.plugin.ingameUtils.updateScoreboard(player)));


                            if (deathMatchTimer == 0) {
                                onEnd();
                                cancel();
                            }
                        }
                    }
                }.runTaskTimer(SurvivalGames.plugin, 0,20);
    }

    public void onEnd() {
        Bukkit.getScheduler().cancelAllTasks();
        SurvivalGames.plugin.status = Status.END;
        new BukkitRunnable() {

            @Override
            public void run() {
                SurvivalGames.plugin.playerUtils.getPlayers2().forEach((player -> {
                        SurvivalGames.plugin.mysqlUtils.updateStatsForPlayer(player.getUniqueId().toString());
                    cancel();


            }));
            }
        }.runTaskAsynchronously(SurvivalGames.plugin);
        SurvivalGames.plugin.playerUtils.getPlayers2().clear();
       new BukkitRunnable() {
           int timer = 11;

           @Override
           public void run() {
               if (timer <= timer) {
                   timer--;
                   if (timer > 0)
                       Bukkit.getOnlinePlayers().forEach(player ->

                               player.sendMessage(SurvivalGames.plugin.messageUtils.getServerRestart().replace("%SECONDS%", String.valueOf(timer))));


               }

               if (timer == 0) {
                   Bukkit.getOnlinePlayers().forEach(player ->{


                           ByteArrayOutputStream b = new ByteArrayOutputStream();
                           DataOutputStream out = new DataOutputStream(b);
                           try {
                               out.writeUTF("Connect");
                               out.writeUTF(SurvivalGames.plugin.messageUtils.getLobbyName());
                               player.sendPluginMessage(SurvivalGames.plugin, "BungeeCord", b.toByteArray());
                           } catch (IOException e) {


                       }

                   });
               }
               if(timer == -1) {
                   Bukkit.shutdown();
               }
           }
       }.runTaskTimer(SurvivalGames.plugin, 0,20);
    }


}
