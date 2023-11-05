package fr.pitrouflette.undeadapocalypse.menu;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.manager.GuildManager;
import fr.pitrouflette.undeadapocalypse.utils.manager.ItemManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

public class MenuOpenedEvents implements Listener {

    File configs = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configs);

    ItemManager itemManager = new ItemManager();
    MenuManagerGUI menuManagerGUI = new MenuManagerGUI();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent ev){

        Player player= ev.getPlayer();
        Inventory inv = player.getInventory();
        inv.setItem(8, itemManager.getItem("§6Menu"));
        Main.guildChat.put(player, false);
        new GuildManager().loadGuilds();

    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent ev){
        Player player = ev.getPlayer();
        if(player.getItemInHand().equals(itemManager.getItem("§6Menu"))){
            menuManagerGUI.openMainGUI(player);
        }
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent ev){
        ev.getPlayer().getInventory().setItem(8, itemManager.getItem("§6Menu"));
    }

    @EventHandler
    public void OnPlayerClickGUIEvent(InventoryClickEvent event) throws IOException {

        Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem() == null){return;}
        if(!event.getCurrentItem().hasItemMeta()){return;}

        if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§6Menu")){
            menuManagerGUI.openMainGUI(player);
        }else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§6Opened Menu")){
            event.setCancelled(true);
        }
    }
}
