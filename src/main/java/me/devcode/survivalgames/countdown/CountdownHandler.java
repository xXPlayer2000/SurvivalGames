package me.devcode.survivalgames.countdown;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import me.devcode.survivalgames.SurvivalGames;
import me.devcode.survivalgames.utils.Status;

public class CountdownHandler {
    public int timer = 61;
    public boolean started = false;
    public void onLobby() {
        started = true;

                new  BukkitRunnable() {
                    @Override public void run() {
                      if(timer <= timer) {
                          timer--;
                          SurvivalGames.plugin.playerUtils.getPlayers().forEach(new Consumer<Player>() {
                              @Override
                              public void accept(Player player) {
                                  player.setLevel(timer);
                                  player.setExp((float) timer / 60);

                              }
                          });
                          if(SurvivalGames.plugin.playerUtils.getPlayers().size() < SurvivalGames.plugin.messageUtils.getMinplayers()) {
                              for(Player all : Bukkit.getOnlinePlayers()) {
                                  all.setExp(0);
                                  all.setLevel(0);
                                  all.sendMessage(SurvivalGames.plugin.messageUtils.getNotenoughplayer());
                              }
                              started = false;
                              timer = 61;
                             cancel();
                              return;
                          }
                      }
                      if(timer == 60 || timer == 30  || timer == 10 || timer <= 5 && timer >0) {
                          SurvivalGames.plugin.playerUtils.getPlayers().forEach(new Consumer<Player>() {
                              @Override
                              public void accept(Player player) {

                                  player.sendMessage(SurvivalGames.plugin.messageUtils.getLobbycountdown().replace("%SECONDS%", String.valueOf(timer)));
                              }
                          });
                      }
                        if(timer == 0) {
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach(new Consumer<Player>() {
                                @Override
                                public void accept(Player player) {
                                    player.setLevel(timer);
                                    player.setExp((float)timer/60);
                                    player.sendMessage(SurvivalGames.plugin.messageUtils.getGoodluck());
                                    SurvivalGames.plugin.mysqlUtils.addSpieleByPlayer(player.getUniqueId().toString(), 1);
                                    SurvivalGames.plugin.ingameUtils.addKill(player, 0);
                                }
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
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool( 1 );
        scheduler.scheduleAtFixedRate(
                new Runnable() {
                    int timer = 1;
                    @Override public void run() {
                        if(timer <= timer) {
                            timer--;

                        }
                        if(timer == 25 || timer == 20 || timer == 10 || timer <= 5 && timer >0)
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach(new Consumer<Player>() {
                                @Override
                                public void accept(Player player) {
                                    player.sendMessage(SurvivalGames.plugin.messageUtils.getNomove().replace("%SECONDS%", String.valueOf(timer)));
                                }
                            });
                        if(timer == 0) {
                        onNoDamage();
                             scheduler.shutdownNow();
                        }
                    }
                },
                0, 1,
                TimeUnit.SECONDS );
    }

    public void onNoDamage() {

                new BukkitRunnable() {
                    int timer = 5;
                    @Override public void run() {
                        if(timer <= timer) {
                            timer--;
                        }
                        if(timer == 20  || timer == 10 || timer <= 5 && timer >0)
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach(new Consumer<Player>() {
                                @Override
                                public void accept(Player player) {
                                    player.sendMessage(SurvivalGames.plugin.messageUtils.getNoDamagecountdown().replace("%SECONDS%", String.valueOf(timer)));

                                }
                            });
                        if(timer == 0) {
                            onIngame();
                      cancel();
                        }
                    }
                }.runTaskTimer(SurvivalGames.plugin,0,20);
    }
    public int ingameTimer = 1500;
    //No executor because of a bug
    public void onIngame() {

        SurvivalGames.plugin.status = Status.INGAME;

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (ingameTimer <= ingameTimer) {
                            ingameTimer--;
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach(new Consumer<Player>() {
                                @Override
                                public void accept(Player player) {
                                    SurvivalGames.plugin.ingameUtils.updateScoreboard(player);
                                }
                            });
                        }

                        if (ingameTimer == 0) {
                            SurvivalGames.plugin.playerUtils.getPlayers().forEach(new Consumer<Player>() {
                                @Override
                                public void accept(Player player) {
                                    SurvivalGames.plugin.ingameUtils.getscoreboard.remove(player);
                                }
                            });
                            onDeathmatch();
                            cancel();

                        }
                    }
                }.runTaskTimer(SurvivalGames.plugin, 0,20);
    }
    public int deathmatchTimer = 601;
    public void onDeathmatch() {
        SurvivalGames.plugin.status = Status.DEATHMATCH;

                new BukkitRunnable() {

                    @Override public void run() {
                        if(deathmatchTimer <= deathmatchTimer) {
                            deathmatchTimer--;
                            Bukkit.getOnlinePlayers().forEach(new Consumer<Player>() {
                                @Override
                                public void accept(Player player) {
                                   SurvivalGames.plugin.ingameUtils.updateScoreboard(player);
                                }
                            });
                        }

                        if(deathmatchTimer == 0) {
                            onEnd();
                           cancel();
                        }
                    }
                }.runTaskTimer(SurvivalGames.plugin, 0,20);
    }

    public void onEnd() {
        Bukkit.getScheduler().cancelAllTasks();
        SurvivalGames.plugin.status = Status.END;

        SurvivalGames.plugin.playerUtils.getPlayers2().forEach(new Consumer<Player>() {
            @Override
            public void accept(Player player) {
                SurvivalGames.plugin.mysqlUtils.updateStatsForPlayer(player.getUniqueId().toString());

            }
        });
        SurvivalGames.plugin.playerUtils.getPlayers2().clear();
       new BukkitRunnable() {
           int timer = 11;

           @Override
           public void run() {
               if (timer <= timer) {
                   timer--;
                   if (timer > 0)
                       Bukkit.getOnlinePlayers().forEach(new Consumer<Player>() {
                           @Override
                           public void accept(Player player) {
                               player.sendMessage(SurvivalGames.plugin.messageUtils.getServerrestart().replace("%SECONDS%", String.valueOf(timer)));
                           }
                       });
               }

               if (timer == 0) {
                   Bukkit.getOnlinePlayers().forEach(new Consumer<Player>() {
                       @Override
                       public void accept(Player player) {

                           ByteArrayOutputStream b = new ByteArrayOutputStream();
                           DataOutputStream out = new DataOutputStream(b);
                           try {
                               out.writeUTF("Connect");
                               out.writeUTF(SurvivalGames.plugin.messageUtils.getLobbyname());
                               player.sendPluginMessage(SurvivalGames.plugin, "BungeeCord", b.toByteArray());
                           } catch (IOException e) {

                           }
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
