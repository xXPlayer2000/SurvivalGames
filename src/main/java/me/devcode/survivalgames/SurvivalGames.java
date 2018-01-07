package me.devcode.survivalgames;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.devcode.survivalgames.commands.SetLobby;
import me.devcode.survivalgames.commands.SetSpawn;
import me.devcode.survivalgames.commands.SetSpawn2;
import me.devcode.survivalgames.commands.Start;
import me.devcode.survivalgames.commands.Stats;
import me.devcode.survivalgames.countdown.CountdownHandler;
import me.devcode.survivalgames.listeners.CancelListeners;
import me.devcode.survivalgames.listeners.ChestListeners;
import me.devcode.survivalgames.listeners.DeathListener;
import me.devcode.survivalgames.listeners.JoinListener;
import me.devcode.survivalgames.listeners.LoginListener;
import me.devcode.survivalgames.listeners.QuitListener;
import me.devcode.survivalgames.mysql.AsyncMySql;
import me.devcode.survivalgames.mysql.MySqlMethods;
import me.devcode.survivalgames.mysql.MySqlStats;
import me.devcode.survivalgames.mysql.MySqlUtils;
import me.devcode.survivalgames.utils.IngameUtils;
import me.devcode.survivalgames.utils.MessageUtils;
import me.devcode.survivalgames.utils.PlayerUtils;
import me.devcode.survivalgames.utils.Status;

public class SurvivalGames extends JavaPlugin{

    public static SurvivalGames plugin;
    public AsyncMySql mysql;
    public MySqlMethods methods = new MySqlMethods();
    public MySqlStats stats = new MySqlStats();
    public MySqlUtils mysqlUtils = new MySqlUtils();
    public Status status;
    public PlayerUtils playerUtils = new PlayerUtils();
    public MessageUtils messageUtils;
    public IngameUtils ingameUtils = new IngameUtils();
    public CountdownHandler countdownHandler = new CountdownHandler();
    public ChestListeners chestListeners = new ChestListeners();
    public File file = new File("plugins/survivalgames", "chest.yml");
    public FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    @Override
    public void onEnable() {
        plugin = this;
        status = Status.LOBBY;
        messageUtils = new MessageUtils();
        System.out.println("survivalgames enabled.");
        getConfig().addDefault("MySQL.Host", "localhost");
        getConfig().addDefault("MySQL.User", "USER");
        getConfig().addDefault("MySQL.Password", "PASSWORD");
        getConfig().addDefault("mysql.Database", "DATABASE");
        getConfig().addDefault("Worlds.Load", Arrays.asList("world2", "world3"));
        getConfig().addDefault("Blocks.Place.Allowed", Arrays.asList(1,2,3,4));
        getConfig().addDefault("Blocks.Break.Allowed", Arrays.asList(1,2,3,4));
        getConfig().options().copyDefaults(true);
        saveConfig();
        List<String> testitems = new ArrayList<>();
        testitems.add("1:0, 1, 50, 0, 0");
        testitems.add("2:0, 1, 50, 0, 0");
        testitems.add("276:0, 1, 50, 1, 1");
        cfg.options().header("ID:SUBID, AMOUNT, CHANCE, ENCHANTID, ENCHANTAMOUNT");
        cfg.options().copyHeader(true);
        cfg.addDefault("items", testitems);
        cfg.options().copyDefaults(true);
        try {
            cfg.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        registerEvents();
        registerCommands();
        mysqlStuff();
       getConfig().getStringList("Worlds.Load").forEach(worlds -> {
           new WorldCreator(worlds).createWorld();
       });
            Bukkit.getWorlds().forEach(world -> {
            Bukkit.unloadWorld(world.getName(), false);
        world.getEntities().forEach(en -> {
                if(!(en instanceof Player)) {
                    en.remove();
                }
            });

            world.setAutoSave(false);
            world.setTime(1000);
            if(!new File(world.getName()+"Backup").exists()) {
                copyFileStructure(world.getWorldFolder(), new File(world.getName()+"Backup"));
            }
        });
        new BukkitRunnable() {
                    @Override public void run() {
                     //Check if mysql is still connected if not > reconnect
                    mysql.getMySQL().checkConnection();
                    }
                }.runTaskTimerAsynchronously(this, 1800,1800);
    }

    public void registerEvents() {
        //Register Events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new QuitListener(), this);
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new ChestListeners(), this);
        pm.registerEvents(new CancelListeners(), this);
        pm.registerEvents(new LoginListener(), this);
    }

    public void registerCommands() {
        //Register Commands
        getCommand("setlobby").setExecutor(new SetLobby());
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("setdspawn").setExecutor(new SetSpawn2());
        getCommand("stats").setExecutor(new Stats());
        getCommand("start").setExecutor(new Start());
    }

    @Override
    public void onDisable() {
            Bukkit.getWorlds().forEach(world -> {

            deleteWorld(world.getWorldFolder());
            copyFileStructure(new File(world.getName()+"Backup"), new File(world.getName()));
        });
        mysql.getMySQL().closeConnection();
    }

    private boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }


    private void copyFileStructure(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mysqlStuff() {
        plugin.mysql = new AsyncMySql(plugin, plugin.getConfig().getString("MySQL.Host"), 3306, plugin.getConfig().getString("MySQL.User"), plugin.getConfig().getString("MySQL.Password"),plugin.getConfig().getString("MySQL.Database"));
        mysql.update("CREATE TABLE IF NOT EXISTS survivalgames(UUID varchar(64), NAME varchar(64), KILLS int, DEATHS int, WINS int, GAMES int);");
        //methods.setRanks();
    }

}
