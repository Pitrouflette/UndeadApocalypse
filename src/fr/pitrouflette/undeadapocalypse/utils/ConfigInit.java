package fr.pitrouflette.undeadapocalypse.utils;

import fr.pitrouflette.undeadapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigInit{

    public List<String> itemsList = new ArrayList<>();
    public List<String> loremImpsum = new ArrayList<>();
    public List<String> modifiedItemsList = new ArrayList<>();
    public List<String> skullListGame = new ArrayList<>();
    public List<Integer> borderItemsSlotList = new ArrayList<>();

    public void initConfig(String fileName){  //cette class est exécuter une seul fois dans la vie du plugin, son optimisation n'est pas un problème

        borderItemsSlotList.add(0);
        borderItemsSlotList.add(1);
        borderItemsSlotList.add(7);
        borderItemsSlotList.add(8);
        borderItemsSlotList.add(9);
        borderItemsSlotList.add(17);
        borderItemsSlotList.add(36);
        borderItemsSlotList.add(44);
        borderItemsSlotList.add(45);
        borderItemsSlotList.add(46);
        borderItemsSlotList.add(52);
        borderItemsSlotList.add(53);

        skullListGame.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE5YTdmMDdkYTliZGEyODBiMGY5MDliNDk1ZWJjOWZiMWFlZTM1NTJjYjE3ZTM5YmExYjRjOTZkMDJhMjBjYSJ9fX0=");
        skullListGame.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTRjYmZmYjk3MTIyMzhjOTU2OTc3ZDFiZmRhNGIzMDhlZDQ3N2JhMzEwNmMzMTMwMmMyNDA4NjJlZjc3OWEifX19");
        skullListGame.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ZjZTA1OWU2MTg1MDU5NDAwMGFlMzk3Nzg1NTZiN2ViMGFmYTkyOGZhZmQ1NzNiMGQ4MjMxNGU4YmRmNDlkMyJ9fX0=");
        skullListGame.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ1NWMzMmFjYWNlNzY2ZjljOTRiNDNkMmEzNzYyMWViODVlMDdjMDlkZWJlNWUzM2UwZTBiMzIxZDI3MDkxMiJ9fX0=");
        skullListGame.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNlZDM2ODgyMWI0NjZkMzliZGY1N2U3OGZjMWRkZjc1ZWM5NjZjMTY5NTVlMDJlNTk0OGJlN2FkZmQxZmU1NSJ9fX0=");
        skullListGame.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjYxOTNjMjgzMGM1MmFmNTQ2ZmU2ZDE4MDAwYTRmMjdlNDhkMjJmMjQ5NmRkNDIwNzNiOWYyMDljOTU1ZmRiNSJ9fX0=");
        skullListGame.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjZlMjk1M2VlMDVmMWE0NzlmYmRhYjRlM2FkYWQ1ZDY0ZWE4ZDMzMzY2MzhmYWU1YThmNWI4OGM0MjA0NmZkIn19fQ==");

        itemsList.add("§6Menu");
        itemsList.add("§6Opened Menu");

        loremImpsum.add("lorem impsum");
        loremImpsum.add("lorem impsum");
        loremImpsum.add("lorem impsum");

        modifiedItemsList.add("none");

        switch (fileName) {
            case "item.yml" -> {
                if (!ConfigUtils.configFileExist(Main.getPlugin(Main.class).getDataFolder(), "item.yml")) {
                    ConfigUtils.createConfigFile("item.yml");
                    File item = new File(Main.getPlugin(Main.class).getDataFolder(), "item.yml");
                    FileConfiguration items = YamlConfiguration.loadConfiguration(item);

                    for (int i = 0; i <= itemsList.size(); i++){
                        items.set("items." + itemsList.get(i) + ".material", "STONE");
                        items.set("items." + itemsList.get(i) + ".name", itemsList.get(i));
                        items.set("items." + itemsList.get(i) + ".glowing", false);
                        items.set("items." + itemsList.get(i) + ".custom-model", 1);
                        items.set("items." + itemsList.get(i) + ".lore", loremImpsum);
                        items.set("items." + itemsList.get(i) + ".use", 3);
                        if(modifiedItemsList.contains(itemsList.get(i))){
                            items.set("items." + itemsList.get(i) + ".modifier", 0);
                        }
                    }
                    items.set("items.modified-item-list", modifiedItemsList);

                    try {
                        items.save(item);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
            case "config.yml" -> {
                if (!ConfigUtils.configFileExist(Main.getPlugin(Main.class).getDataFolder(), "config.yml")) {
                    ConfigUtils.createConfigFile("config.yml");
                    File configs = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
                    FileConfiguration config = YamlConfiguration.loadConfiguration(configs);

                    config.set("gui.menu.name", "Main Menu");
                    config.set("gui.border-items-slot-list", borderItemsSlotList);
                    config.set("head.server",
                            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTgyMDg2ZDE1NDVhZTg4OGFhNzY2ZjhlZDljNjZlNDc1NWI0MmVkM2E3YmU0ZTBjZmEwNjhkN2Y2NzZkNmRmIn19fQ==");
                    config.set("head.earth",
                            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=");
                    config.set("head.create",
                            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjOTdlNGI2OGFhYWFlODQ3MmUzNDFiMWQ4NzJiOTNiMzZkNGViNmVhODllY2VjMjZhNjZlNmM0ZTE3OCJ9fX0=");
                    config.set("head.volcano",
                            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTJkZDUyMWJlYjY3NTMzMzExY2NmNmQ3MjM0YmEwZjI4OTYzYzA2MzM1ZjgzNGIyMTM2Y2U4OWFiM2JhMzFkYiJ9fX0=");
                    config.set("head.load",
                            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ3ZDFkZDRhN2RhZmYyYWFmMjhlNmExMmEwMWY0MmQ3ZTUxNTkzZWYzZGVhNzYyZWY4MTg0N2IxZDRjNTUzOCJ9fX0=");
                    config.set("head.guild-list", skullListGame);

                    try {
                        config.save(configs);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
            case "lang.yml" -> {
                if (!ConfigUtils.configFileExist(Main.getPlugin(Main.class).getDataFolder(), "lang.yml")) {
                    ConfigUtils.createConfigFile("lang.yml");
                    File langFile = new File(Main.getPlugin(Main.class).getDataFolder(), "lang.yml");
                    FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);

                    lang.set("prefix", "&7[&c&l &6&lApocalypse&7]&r");

                    lang.set("events.join.message", "&7[&2+&7] <player>");
                    lang.set("events.join.titles.title", "&c&l&6&lUndeadApocalypse&c&l");
                    lang.set("events.join.titles.subtitle", "&eAdventure &6&lServer");

                    lang.set("events.leave.message", "&7[&4-&7] <player>");

                    lang.set("events.reload.success", "&7[&c&l &6&lApocalypse&7]&r &2Plugin reloaded successfully");
                    lang.set("events.reload.fail", "&7[&c&l &6&lApocalypse&7]&r &4Plugin reload failure");

                    try {
                        lang.save(langFile);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
            case "guild.yml" -> {
                if (!ConfigUtils.configFileExist(Main.getPlugin(Main.class).getDataFolder(), "guild.yml")) {
                    ConfigUtils.createConfigFile("guild.yml");
                    File guildFile = new File(Main.getPlugin(Main.class).getDataFolder(), "lang.yml");
                    FileConfiguration guild = YamlConfiguration.loadConfiguration(guildFile);

                    try {
                        guild.save(guildFile);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
            case "apocalypse.yml" -> {
                if (!ConfigUtils.configFileExist(Main.getPlugin(Main.class).getDataFolder(), "apocalypse.yml")) {
                    ConfigUtils.createConfigFile("apocalypse.yml");
                    File apoFile = new File(Main.getPlugin(Main.class).getDataFolder(), "lang.yml");
                    FileConfiguration apo = YamlConfiguration.loadConfiguration(apoFile);

                    try {
                        apo.save(apoFile);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
            default -> {
                Bukkit.getServer().getConsoleSender().sendMessage("§4Le fichier spécifier n'existe pas :/");
            }
        }
    }
}