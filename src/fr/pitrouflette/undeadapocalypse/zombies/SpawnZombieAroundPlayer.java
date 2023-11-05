package fr.pitrouflette.undeadapocalypse.zombies;

import fr.pitrouflette.undeadapocalypse.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.Objects;
import java.util.Random;

public class SpawnZombieAroundPlayer {

    Random random = new Random();

    public void scheduleRandomZombieGeneration() {

        int minDelay = 30*20;
        int maxDelay = 60*20;

        int delay = minDelay + random.nextInt(maxDelay - minDelay + 1);
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), this::generateRandomZombies, delay);
    }

    public void generateRandomZombies() {
        Player randomPlayer = getRandomPlayer();
        if (randomPlayer == null) {
            scheduleRandomZombieGeneration();
            return;
        }
        if(!(randomPlayer.getWorld().getTime() >= 12000L)){
            return;
        }
        World world = randomPlayer.getWorld();
        Location spawnLocation = getRandomLocationAround(randomPlayer.getLocation(), 38);
        for (int i = 0; i <= 10; i++){
            Zombie zombie = (Zombie) world.spawnEntity(spawnLocation, EntityType.ZOMBIE);
        }
        scheduleRandomZombieGeneration();
    }

    private Player getRandomPlayer() {
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);

        if (players.length == 0) {
            return null;
        }

        int randomIndex = random.nextInt(players.length);
        players[randomIndex].sendMessage("§4Vous avez été repéré par des zombies !");
        return players[randomIndex];
    }

    private Location getRandomLocationAround(Location location, int radius) {

        int x = location.getBlockX();
        int z = location.getBlockZ();

        int randomX = x + random.nextInt(radius * 2) - radius;
        int randomZ = z + random.nextInt(radius * 2) - radius;

        int y = Objects.requireNonNull(location.getWorld()).getHighestBlockYAt(randomX, randomZ);

        return new Location(location.getWorld(), randomX, y, randomZ);
    }

}
