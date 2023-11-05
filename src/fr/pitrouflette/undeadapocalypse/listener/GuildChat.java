package fr.pitrouflette.undeadapocalypse.listener;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.Guild;
import fr.pitrouflette.undeadapocalypse.utils.manager.GuildManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Objects;
import java.util.UUID;

public class GuildChat implements Listener {

    @EventHandler
    public void onChatEvent(PlayerChatEvent ev){
        Player player = ev.getPlayer();
        ev.setCancelled(true);
        if(new GuildManager().isPlayerInAnyGuild(player)){
            Guild guild = new GuildManager().getPlayerGuild(player);
            if(Main.guildChat.get(player).equals(true)){
                for(UUID players : guild.getPlayers()){
                    if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(players))){return;}
                    Objects.requireNonNull(Bukkit.getPlayer(players)).sendMessage("§l§0[ " + guild.getColor() + "⚔ §n§l" + guild.getName() + "'s chat§r§l " + guild.getColor() + player.getName() +" §r§l§0]§r§l " +
                            "" + guild.getColor() + ev.getMessage());
                }
            }else{
                for(Player player1 : Bukkit.getOnlinePlayers()){
                    player1.sendMessage("§7[ " + guild.getColor() + "⚔ §n" + guild.getName() + "§r§l " + guild.getColor() + player.getName() +" §r§7]§r " + ev.getMessage());
                }
            }
        }else{
            for(Player player1 : Bukkit.getOnlinePlayers()){
                player1.sendMessage("§7[§r §l" + player.getName() + " §r§7]§r " + ev.getMessage());
            }

        }
    }
}
