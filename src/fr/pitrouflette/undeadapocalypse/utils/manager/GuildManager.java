package fr.pitrouflette.undeadapocalypse.utils.manager;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.Guild;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GuildManager {

    File guildFile = new File(Main.getPlugin(Main.class).getDataFolder(), "guild.yml");
    FileConfiguration guild = YamlConfiguration.loadConfiguration(guildFile);

    public void create(Player player, String name) throws IOException {
        if(isPlayerInAnyGuild(player)){
            player.sendMessage("§4Vous faites déja parties d'une guild !");
            return;
        }
        List<UUID> members = new ArrayList<>();
        members.add(player.getUniqueId());
        List<UUID> rank1 = new ArrayList<>();
        List<UUID> rank2 = new ArrayList<>();
        List<UUID> rank3 = new ArrayList<>();
        Guild guild = new Guild(name, members, player, 10, player.getLocation(), false, rank1, rank2, rank3, "§6");
        Main.guilds.put(name, guild);

        saveGuilds();
        player.sendMessage("§2Guild crée avec succès !");
        player.setCustomNameVisible(true);
        player.setCustomName("§4" + name + " " + player.getDisplayName());
        player.setPlayerListName("§4" + name + " " + player.getDisplayName());
    }

    public void join(Player player, String name){
        if(isPlayerInAnyGuild(player)){
            player.sendMessage("§4Vous faites déja parties d'une guild !");
            return;
        }
        if(Main.guilds.get(name) != null){
            if(Main.guilds.get(name).isPublic()){
                Main.guilds.get(name).addPlayers(player);
                player.setCustomNameVisible(true);
                player.setCustomName(Main.guilds.get(name).getColor() + name  + " " + player.getDisplayName());
                player.setDisplayName(Main.guilds.get(name).getColor() + name  + " " + player.getDisplayName());
                player.setPlayerListName(Main.guilds.get(name).getColor() + name  + " " + player.getDisplayName());
                Main.guilds.get(name).addPower(10);
            }else if (Main.invitation.keySet().contains(player)){
                if(Main.invitation.get(player).getName().equals(name)){
                    Main.guilds.get(name).addPlayers(player);
                    player.setCustomNameVisible(true);
                    player.setCustomName(Main.guilds.get(name).getColor() + name  + " " + player.getDisplayName());
                    player.setDisplayName(Main.guilds.get(name).getColor() + name  + " " + player.getDisplayName());
                    player.setPlayerListName(Main.guilds.get(name).getColor() + name  + " " + player.getDisplayName());
                    Main.guilds.get(name).addPower(10);
                }
            }else{
                player.sendMessage("§4Vous devez être invité pour rejoindre cette guild !!");
            }

        }else {
            player.sendMessage("§4Cette guild n'existe pas");
        }
    }

    public boolean isPlayerInAnyGuild(Player player) {
        UUID playerUUID = player.getUniqueId();

        for (Guild guild : Main.guilds.values()) {
            List<UUID> members = guild.getPlayers();
            if (members.contains(playerUUID)) {
                return true;
            }
        }
        return false;
    }

    public Guild getPlayerGuild(Player player) {
        UUID playerUUID = player.getUniqueId();

        for (Guild guild : Main.guilds.values()) {
            List<UUID> members = guild.getPlayers();
            if (members.contains(playerUUID)) {
                return guild;
            }
        }
        return null;
    }

    public void loadGuilds() {

        guild = YamlConfiguration.loadConfiguration(guildFile);

        Main.guilds.clear();

        ConfigurationSection guildsSection = guild.getConfigurationSection("guilds");
        if (guildsSection != null) {
            for (String guildName : guildsSection.getKeys(false)) {
                ConfigurationSection guildSection = guildsSection.getConfigurationSection(guildName);

                assert guildSection != null;
                String leaderName = guildSection.getString("leader");
                assert leaderName != null;
                Player leader = Bukkit.getServer().getPlayer(leaderName);

                int reputation = guildSection.getInt("reputation");

                Location hdv = guildSection.getLocation("hdv");

                Boolean publicc = guildSection.getBoolean("public");

                String name = guildSection.getString("name");

                String color = guildSection.getString("color");

                List<String> memberUUIDs = guildSection.getStringList("members");
                List<String> rank1UUIDs = guildSection.getStringList("rank1");
                List<String> rank2UUIDs = guildSection.getStringList("rank2");
                List<String> rank3UUIDs = guildSection.getStringList("rank3");

                List<UUID> members = new ArrayList<>();
                for (String memberUUID : memberUUIDs) {
                    members.add(UUID.fromString(memberUUID));
                }

                List<UUID> rank1 = new ArrayList<>();
                for (String rank1UUID : rank1UUIDs) {
                    rank1.add(UUID.fromString(rank1UUID));
                }

                List<UUID> rank2 = new ArrayList<>();
                for (String rank2UUID : rank2UUIDs) {
                    rank2.add(UUID.fromString(rank2UUID));
                }

                List<UUID> rank3 = new ArrayList<>();
                for (String rank3UUID : rank3UUIDs) {
                    rank3.add(UUID.fromString(rank3UUID));
                }
                Guild guild = new Guild(name, members, leader, reputation, hdv, publicc, rank1, rank2, rank3, color);
                Main.guilds.put(guildName, guild);
            }
        }
    }

    public void saveGuilds() {
        guild = new YamlConfiguration();

        ConfigurationSection guildsSection = guild.createSection("guilds");
        for (Map.Entry<String, Guild> entry : Main.guilds.entrySet()) {
            String guildName = entry.getKey();
            Guild guild = entry.getValue();

            ConfigurationSection guildSection = guildsSection.createSection(guildName);

            guildSection.set("leader", guild.getChef().getDisplayName());
            guildSection.set("reputation", guild.getPower());
            guildSection.set("hdv", guild.getHdv());
            guildSection.set("name", guild.getName());
            guildSection.set("public", guild.isPublic());
            guildSection.set("color", guild.getColor());

            List<String> memberUUIDs = new ArrayList<>();
            for (UUID memberUUID : guild.getPlayers()) {
                memberUUIDs.add(memberUUID.toString());
            }
            guildSection.set("members", memberUUIDs);

            List<String> rank1UUIDs = new ArrayList<>();
            for (UUID rank1UUID : guild.getRank1()) {
                rank1UUIDs.add(rank1UUID.toString());
            }
            guildSection.set("rank1", rank1UUIDs);

            List<String> rank2UUIDs = new ArrayList<>();
            for (UUID rank2UUID : guild.getRank2()) {
                rank2UUIDs.add(rank2UUID.toString());
            }
            guildSection.set("rank2", rank2UUIDs);

            List<String> rank3UUIDs = new ArrayList<>();
            for (UUID rank3UUID : guild.getRank3()) {
                rank3UUIDs.add(rank3UUID.toString());
            }
            guildSection.set("rank3", rank3UUIDs);
        }

        try {
            guild.save(guildFile);
            loadGuilds();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

