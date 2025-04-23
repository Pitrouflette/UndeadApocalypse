package fr.pitrouflette.undeadapocalypse;

import fr.pitrouflette.undeadapocalypse.commands.GuildCommand;
import fr.pitrouflette.undeadapocalypse.listeners.*;
import fr.pitrouflette.undeadapocalypse.menu.MenuInteractEvent;
import fr.pitrouflette.undeadapocalypse.menu.MenuOpenedEvents;
import fr.pitrouflette.undeadapocalypse.utils.ConfigInit;
import fr.pitrouflette.undeadapocalypse.utils.ConfigUtils;
import fr.pitrouflette.undeadapocalypse.utils.Guild;
import fr.pitrouflette.undeadapocalypse.utils.manager.GuildManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin {

    public static HashMap<String, Guild> guilds = new HashMap<>();
    public static HashMap<Player, Guild> invitation = new HashMap<>();
    public static HashMap<Player, Boolean> guildChat = new HashMap<>();
    public static List<List> claimedChunks = new ArrayList<>();
    private static Main instance;

    ConfigInit configInit = new ConfigInit();

    @Override
    public void onEnable(){

        this.getServer().getConsoleSender().sendMessage("§6===================UndeadApocalyspe===================");
        this.getServer().getConsoleSender().sendMessage("§8prefix initialisé : §e[§6U§8A§e]");
        this.getServer().getConsoleSender().sendMessage("§8classement des meilleurs joueurs : §2✔");
        this.getServer().getConsoleSender().sendMessage("§8écriture du config.yml...");
        this.getServer().getConsoleSender().sendMessage("§8status des fichiers de configurations : §2✔");
        this.getServer().getConsoleSender().sendMessage("§6===================§2PLugin started§6===================");

        new GuildManager().loadGuilds();

        configInit.initConfig("item.yml");
        configInit.initConfig("config.yml");
        configInit.initConfig("guild.yml");
        configInit.initConfig("apocalypse.yml");
        configInit.initConfig("lang.yml");

        //new SpawnZombieAroundPlayer().scheduleRandomZombieGeneration();

        Bukkit.getPluginManager().registerEvents(new ZombiesTypes(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDieEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MenuOpenedEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MenuInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new GuildChat(), this);
        Bukkit.getPluginManager().registerEvents(new Claims(), this);
        Bukkit.getPluginManager().registerEvents(new OnJoinLeave(), this);
        Objects.requireNonNull(getCommand("guild")).setExecutor(new GuildCommand(this));
    }

    public static Main getInstance(){
        return instance;
    }

    File items = new File(this.getDataFolder(), "item.yml");
    FileConfiguration item = YamlConfiguration.loadConfiguration(items);

    public FileConfiguration getItem(){
        return item;
    }

    public String getName(String path) {return item.getString("items." + path + ".name");}
    public Material getMaterial(String path) {
        return Material.getMaterial(Objects.requireNonNull(item.getString("items." + path + ".material")));
    }
    public List<String> getLore(String path) {return item.getStringList("items." + path + ".lore");}
    public int getCustomModel(String path) {return item.getInt("items." + path + ".custom-model");}
    public boolean isGlowing(String path) {return item.getBoolean("items." + path + ".glowing");}

    @Override
    public void onDisable(){
        new GuildManager().saveGuilds();
    }

}


// ||
