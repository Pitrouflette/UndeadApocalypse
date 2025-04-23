package fr.pitrouflette.undeadapocalypse.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ZombiesTypes implements Listener {

    Random random = new Random();

    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent ev){
        if(ev.getEntity() instanceof Zombie){
            int randomZombie = random.nextInt(100);
            if(randomZombie == 1){
                registerGiantZombie((Zombie) ev.getEntity());
            }else if((randomZombie >= 50)  && !(randomZombie >= 70)){
                registerFastZombie((Zombie) ev.getEntity());
            }else{
                registerResistantZombie((Zombie) ev.getEntity());
            }
        }
    }

    @SuppressWarnings("all")
    public static void registerGiantZombie(Zombie zombie) {
        Giant giant = (Giant) zombie.getWorld().spawnEntity(zombie.getLocation(), EntityType.GIANT);
        giant.setHealth(0.0);
        giant.setCustomName("Zombie Géant");
        giant.setCustomNameVisible(true);
        giant.setMaxHealth(100.0);
        giant.setHealth(100.0);
        giant.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.2);
    }

    @SuppressWarnings("all")
    public static void registerFastZombie(Zombie zombie) {
        zombie.setCustomName("Zombie Rapide");
        zombie.setCustomNameVisible(true);
        zombie.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.35);
        ((LivingEntity) zombie).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
    }

    @SuppressWarnings("all")
    public static void registerResistantZombie(Zombie zombie) {
        zombie.setCustomName("Zombie Résistant");
        zombie.setCustomNameVisible(true);
        zombie.setMaxHealth(40.0);
        zombie.setHealth(40.0);
    }

}