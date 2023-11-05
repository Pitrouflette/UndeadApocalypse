package fr.pitrouflette.undeadapocalypse.menu;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.manager.ItemManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MenuInteractEvent implements Listener {

    File configg = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configg);

    MenuManagerGUI menuManagerGUI = new MenuManagerGUI();
    ItemManager itemManager = new ItemManager();

    @EventHandler
    @SuppressWarnings("all")
    public void OnPlayerClickGUIEvent(InventoryClickEvent event) throws IOException {

        Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem() == null) return;

        if(!event.getView().getTitle().contains(Objects.requireNonNull(config.getString("gui.menu.name")))){return;}

        switch(event.getCurrentItem().getItemMeta().getDisplayName()){
            case "§4Quittez":
                player.closeInventory();
                player.getInventory().setItem(8, itemManager.getItem("§6Menu"));
                break;

            case "§4Retour":
                menuManagerGUI.openMainGUI(player);
                break;

            case "Guild":
                menuManagerGUI.openGuildGUI(player);
                break;

            case "Waprs":
            case "Server Statues :":
            case "Create a new guil":
            case " ":
                event.setCancelled(true);
                break;

            default:
                if(Main.guilds.keySet().contains(event.getCurrentItem().getItemMeta().getDisplayName())){
                    player.teleport(Main.guilds.get(event.getCurrentItem().getItemMeta().getDisplayName()).getHdv());
                }else{
                    break;
                }
                break;
        }
    }

}
