package fr.pitrouflette.undeadapocalypse.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkullUtil {

    public static ItemStack getCustomSkull(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (url == null || url.isEmpty()) return head;

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        String json = "{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}";
        String encodedData = Base64.getEncoder().encodeToString(json.getBytes());


        Map<String, Property> properties = new HashMap<>();
        properties.put("textures", new Property("textures", encodedData));

        try {
            Field propertiesField = profile.getClass().getDeclaredField("properties");
            propertiesField.setAccessible(true);
            propertiesField.set(profile, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        head.setItemMeta(skullMeta);
        return head;
    }

}
