package fr.pitrouflette.undeadapocalypse;

import fr.pitrouflette.undeadapocalypse.commands.GuildCommand;
import fr.pitrouflette.undeadapocalypse.listener.GuildChat;
import fr.pitrouflette.undeadapocalypse.menu.MenuInteractEvent;
import fr.pitrouflette.undeadapocalypse.listener.PlayerDieEvent;
import fr.pitrouflette.undeadapocalypse.menu.MenuOpenedEvents;
import fr.pitrouflette.undeadapocalypse.listener.ZombiesTypes;
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
import org.spigotmc.SpigotConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin {

    public static HashMap<String, Guild> guilds = new HashMap<>();
    public static HashMap<Player, Guild> invitation = new HashMap<>();
    public static HashMap<Player, Boolean> guildChat = new HashMap<>();
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

        SpigotConfig.unknownCommandMessage = "§e[§6U§8A§e] Il semble il y avoir quelques interférences... Cette commande n'a pas pu être reconnu...";

        new GuildManager().loadGuilds();

        configInit.initConfig("item.yml");
        configInit.initConfig("config.yml");

        //new SpawnZombieAroundPlayer().scheduleRandomZombieGeneration();

        Bukkit.getPluginManager().registerEvents(new ZombiesTypes(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDieEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MenuOpenedEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MenuInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new GuildChat(), this);
        Objects.requireNonNull(getCommand("guild")).setExecutor(new GuildCommand(this));

        if (!ConfigUtils.configFileExist(this.getDataFolder(), "apocalypse.yml")) {
            ConfigUtils.createConfigFile("apocalypse.yml");
        }
        File file = new File(this.getDataFolder(), "apocalypse.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        try {
            config.save(file);
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        if (!ConfigUtils.configFileExist(this.getDataFolder(), "guild.yml")) {
            ConfigUtils.createConfigFile("guild.yml");
        }
        File guildFile = new File(this.getDataFolder(), "guild.yml");
        FileConfiguration guild = YamlConfiguration.loadConfiguration(guildFile);
        try {
            guild.save(guildFile);
        } catch (IOException var8) {
            var8.printStackTrace();
        }
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
