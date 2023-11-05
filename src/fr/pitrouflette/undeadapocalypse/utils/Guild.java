package fr.pitrouflette.undeadapocalypse.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Guild {

    private final List<UUID> players;
    private final List<UUID> rank1;
    private final List<UUID> rank2;
    private final List<UUID> rank3;
    private String name;
    private String color;
    private Player chef;
    private int power;
    private Location hdv;
    private Boolean publicc;


    public Guild(String name, List<UUID> players, Player chef, int power, Location hdv, Boolean publicc, List<UUID> rank1, List<UUID> rank2, List<UUID> rank3, String color) {
        this.name = name;
        this.players = players;
        this.chef = chef;
        this.power = power;
        this.hdv = hdv;
        this.publicc = publicc;
        this.rank1 = rank1;
        this.rank2 = rank2;
        this.rank3 = rank3;
        this.color = color;
    }


    public Location getHdv(){return hdv;}
    public void setHdv(Location loc){ hdv = loc;}

    public List<UUID> getPlayers(){ return players; }
    public void removePlayer(Player player){ players.remove(player.getUniqueId()); }
    public void addPlayers(Player player){ players.add(player.getUniqueId()); }

    public Player getChef(){
        return chef;
    }
    public void setChef(Player player){ chef = player; }

    public int getPower(){
        return power;
    }
    public void addPower(int amount){ power += amount; }
    public void removePower(int amount){ power -= amount; }

    public Boolean isPublic(){return publicc;}
    public void setPublic(boolean bol){publicc = bol;}

    public String getName(){return name;}
    public void setName(String newName){name = newName;}

    public List<UUID> getRank1(){ return rank1; }
    public void removeRank1(Player player){ rank1.remove(player.getUniqueId()); }
    public void addRank1(Player player){ rank1.add(player.getUniqueId()); }

    public List<UUID> getRank2(){ return rank2; }
    public void removeRank2(Player player){ rank2.remove(player.getUniqueId()); }
    public void addRank2(Player player){ rank2.add(player.getUniqueId()); }

    public List<UUID> getRank3(){ return rank3; }
    public void removeRank3(Player player){ rank3.remove(player.getUniqueId()); }
    public void addRank3(Player player){ rank3.add(player.getUniqueId()); }

    public String getColor(){return color;}
    public void setColor(String newColor){color = newColor;}

}
