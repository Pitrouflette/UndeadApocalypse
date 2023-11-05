package fr.pitrouflette.undeadapocalypse.menu;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.Guild;
import fr.pitrouflette.undeadapocalypse.utils.ItemBuilder;
import fr.pitrouflette.undeadapocalypse.utils.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MenuManagerGUI {

    File configg = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configg);

    Inventory gui = Bukkit.createInventory(null, 9*6, Objects.requireNonNull(config.getString("gui.menu.name")));

    ItemManager itemManager = new ItemManager();

    ArrayList<String> loreList = new ArrayList<>();

    public void createBasicInventory(){
        loreList.clear();
        loreList.add("Nombre de joueurs : " + Bukkit.getOnlinePlayers().size());
        ItemStack server = new ItemBuilder(Material.PLAYER_HEAD)
                .getCustomSkull(Objects.requireNonNull(config.getString("head.server")))
                .setDisplayName("Server Statues :")
                .setLore(loreList).build();

        ItemStack quit = new ItemBuilder(Material.BARRIER)
                .setDisplayName("§4Quittez").setLore("Click here to quit").build();

        for (int i = 0; i <= 11; i++){
            gui.setItem(config.getIntegerList("gui.border-items-slot-list").get(i), new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName(" ").build());
        }
        gui.setItem(4, server);
        gui.setItem(49, quit);

    }

    public void openMainGUI(Player player){
        gui.clear();
        createBasicInventory();

        ItemStack join = new ItemBuilder(Material.PLAYER_HEAD)
                .getCustomSkull(Objects.requireNonNull(config.getString("head.earth")))
                .setDisplayName("Guild").setLore("Click here navigate btw guilds !").build();

        ItemStack create = new ItemBuilder(Material.PLAYER_HEAD)
                .getCustomSkull(Objects.requireNonNull(config.getString("head.create")))
                .setDisplayName("Waprs").setLore("Click here to go to any warp !").build();

        gui.setItem(20, join);
        gui.setItem(24, create);

        player.openInventory(gui);
        player.getInventory().setItem(8, itemManager.getItem("§6Opened Menu"));

    }

    public void openGuildGUI(Player player){

        int slot = 9;
        gui.clear();
        createBasicInventory();

        ItemStack back = new ItemBuilder(Material.ARROW)
                .setDisplayName("§4Retour").setLore("Click here to go back").build();

        gui.setItem(47, back);

        for(Guild guild : Main.guilds.values()){
            if(slot == 43){return;}
            if(slot == 16 || slot == 25 || slot == 34){
                slot ++;
                slot ++;
            }
            slot ++;
            ArrayList<String> loreList = new ArrayList<>();
            loreList.add("Click to be teleported !");
            Random randomo = new Random();
            int randomIndex = randomo.nextInt(config.getStringList("head.guild-list").size());
            ItemStack Load = new ItemBuilder(Material.PLAYER_HEAD)
                    .getCustomSkull(Objects.requireNonNull(config.getStringList("head.guild-list").get(randomIndex)))
                    .setDisplayName(guild.getName()).setLore(loreList).build();
            gui.setItem(slot, Load);
        }
        ArrayList<String> loreList2 = new ArrayList<>();
        loreList2.add("Seems like there is no");
        loreList2.add("guild available for you...");
        loreList2.add("Then juste create  one !");
        loreList2.add("   -   /guild create");
        ItemStack create = new ItemBuilder(Material.PLAYER_HEAD)
                .getCustomSkull(Objects.requireNonNull(config.getString("head.create")))
                .setDisplayName("Create a new guil").setLore(loreList2).build();
        gui.setItem(51, create);

        player.openInventory(gui);
        player.getInventory().setItem(8, itemManager.getItem("§6Opened Menu"));
    }
}
