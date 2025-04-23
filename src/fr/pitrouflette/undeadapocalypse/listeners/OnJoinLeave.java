package fr.pitrouflette.undeadapocalypse.listeners;

import fr.pitrouflette.undeadapocalypse.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class OnJoinLeave implements Listener {

    File langFile = new File(Main.getPlugin(Main.class).getDataFolder(), "lang.yml");
    FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();

        Main.guildChat.put(player, false);

        event.setJoinMessage(lang.getString("events.join.message").replace("&", "§").replace("<player>", player.getPlayerListName()));

        player.sendTitle(
                lang.getString("events.join.titles.title").replace("&", "§"),
                lang.getString("events.join.titles.subtitle").replace("&", "§")
        );
    }
}
