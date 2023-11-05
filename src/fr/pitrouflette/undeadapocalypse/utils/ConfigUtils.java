package fr.pitrouflette.undeadapocalypse.utils;

import fr.pitrouflette.undeadapocalypse.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtils {

    public ConfigUtils() {
    }

    public static void createConfigFile(String name) {
        File file = new File(Main.getPlugin(Main.class).getDataFolder(), name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var4) {
                IOException e = var4;

                for(int i = 0; i < 5; ++i) {
                    System.out.println("AriamaMC -- Le fichier " + name + " n'a pas pu  tre g n r  ! ( " + e + " )");
                }

            }
        }
    }

    public static boolean saveConfigFile(File folder, String name) {
        File file = new File(folder, name);
        new YamlConfiguration();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (file.exists()) {
            try {
                config.save(file);
                return true;
            } catch (IOException var5) {
                var5.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean configFileExist(File folder, String name) {
        File file = new File(folder, name);
        return file.exists();
    }

}
