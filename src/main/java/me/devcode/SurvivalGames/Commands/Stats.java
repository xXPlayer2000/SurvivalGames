package me.devcode.SurvivalGames.Commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devcode.SurvivalGames.SurvivalGames;

public class Stats implements CommandExecutor{
	
	@SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(final CommandSender cs, Command cmd, String label,
            String[] args) {
        if(!(cs instanceof Player)) {
            cs.sendMessage("Du musst ein Spieler sein.");
            return true;
        }
        if(args.length == 0) {     
           
            Player p = (Player) cs;
            int kills = SurvivalGames.plugin.mysqlUtils.getKillsByPlayer(p.getUniqueId().toString());
            int deaths = SurvivalGames.plugin.mysqlUtils.getDeathsByPlayer(p.getUniqueId().toString());
            int wins = SurvivalGames.plugin.mysqlUtils.getWinsByPlayer(p.getUniqueId().toString());
            int spiele = SurvivalGames.plugin.mysqlUtils.getSpieleByPlayer(p.getUniqueId().toString());
            double kd = Double.valueOf(kills) / Double.valueOf(deaths);
            if(deaths == 0) {
                kd = kills;
            }      
           
            DecimalFormat f = new DecimalFormat("#0.00");
            double toFormat = ((double)Math.round(kd*100))/100;
           
            String formatted = f.format(toFormat);
            cs.sendMessage("");
            cs.sendMessage("§6Stats §8● §7Player §8» §e"+cs.getName());
            cs.sendMessage("§6Stats §8● §7Kills §8» §e" + kills);
            cs.sendMessage("§6Stats §8● §7Deaths §8» §e" + deaths);
            cs.sendMessage("§6Stats §8● §7K/D §8» §e" + formatted.replace("NaN", "0").replace("Infinity", "0"));
            cs.sendMessage("§6Stats §8● §7Wins §8» §e" + wins);
            
            if(spiele <=0) {
                cs.sendMessage("§6Stats §8● §7Looses §8» §e" + (0));
                }else{
                	int niederlagen = spiele-wins;
                	if(niederlagen < 0) {
                		niederlagen = 0;
                	}
            cs.sendMessage("§6Stats §8● §7Looses §8» §e" +niederlagen);
                }
            cs.sendMessage("§6Stats §8● §7Rank §8» §e" + SurvivalGames.plugin.mysqlUtils.getRankByPlayer(p.getUniqueId().toString()));
            cs.sendMessage("");
           
            return true;
            }
        	if(Bukkit.getPlayer(args[0]) != null) {
        		Player p = Bukkit.getPlayer(args[0]);
                int kills = SurvivalGames.plugin.mysqlUtils.getKillsByPlayer(p.getUniqueId().toString());
                int wins = SurvivalGames.plugin.mysqlUtils.getWinsByPlayer(p.getUniqueId().toString());
                int deaths = SurvivalGames.plugin.mysqlUtils.getDeathsByPlayer(p.getUniqueId().toString());
               
                int spiele = SurvivalGames.plugin.mysqlUtils.getSpieleByPlayer(p.getUniqueId().toString());
                double kd = Double.valueOf(kills) / Double.valueOf(deaths);
                if(deaths == 0) {
                    kd = kills;
                }      
               
                DecimalFormat f = new DecimalFormat("#0.00");
                double toFormat = ((double)Math.round(kd*100))/100;
               
                String formatted = f.format(toFormat);
                cs.sendMessage("");
                cs.sendMessage("§6Stats §8● §7Player §8» §e"+p.getName());
                cs.sendMessage("§6Stats §8● §7Kills §8» §e" + kills);
                cs.sendMessage("§6Stats §8● §7Deaths §8» §e" + deaths);
                cs.sendMessage("§6Stats §8● §7K/D §8» §e" + formatted.replace("NaN", "0").replace("Infinity", "0"));
                cs.sendMessage("§6Stats §8● §7Wins §8» §e" + wins);
                
                if(spiele <=0) {
                    cs.sendMessage("§6Stats §8● §7Looses §8» §e" + (0));
                    }else{
                    	int niederlagen = spiele-wins;
                    	if(niederlagen < 0) {
                    		niederlagen = 0;
                    	}
                cs.sendMessage("§6Stats §8● §7Looses §8» §e" +niederlagen);
                    }
                cs.sendMessage("§6Stats §8● §7Rank §8» §e" + SurvivalGames.plugin.mysqlUtils.getRankByPlayer(p.getUniqueId().toString()));
                cs.sendMessage("");
              
                return true;
        	}else{
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
            if(!SurvivalGames.plugin.stats.getBooleanMethod("SurvivalGames", "UUID", p.getUniqueId().toString())) {
                cs.sendMessage("§cNo Stats found.");
                return true;
            }
            int kills = SurvivalGames.plugin.methods.getKills(p.getUniqueId().toString());
            int deaths = SurvivalGames.plugin.methods.getDeaths(p.getUniqueId().toString());
            int wins = SurvivalGames.plugin.methods.getWins(p.getUniqueId().toString());
            int spiele = SurvivalGames.plugin.methods.getGames(p.getUniqueId().toString());
            double kd = Double.valueOf(kills) / Double.valueOf(deaths);
            if(deaths == 0) {
                kd = kills;
            }
           
          
           
            DecimalFormat f = new DecimalFormat("#0.00");
            double toFormat = ((double)Math.round(kd*100))/100;
           
            String formatted = f.format(toFormat);
            cs.sendMessage("");
            cs.sendMessage("§6Stats §8● §7Player §8» §e"+p.getName());
            cs.sendMessage("§6Stats §8● §7Kills §8» §e" + kills);
            cs.sendMessage("§6Stats §8● §7Deaths §8» §e" + deaths);
            cs.sendMessage("§6Stats §8● §7K/D §8» §e" + formatted.replace("NaN", "0").replace("Infinity", "0"));
            cs.sendMessage("§6Stats §8● §7Wins §8» §e" + wins);
            
            if(spiele <=0) {
                cs.sendMessage("§6Stats §8● §7Looses §8» §e" + (0));
                }else{
            cs.sendMessage("§6Stats §8● §7Looses §8» §e" + (spiele-wins));
                }
            cs.sendMessage("§6Stats §8● §7Rank §8» §e" + SurvivalGames.plugin.mysqlUtils.getRankByPlayer(p.getUniqueId().toString()));
            cs.sendMessage("");
           
        }
        	 return true;
	}

}
