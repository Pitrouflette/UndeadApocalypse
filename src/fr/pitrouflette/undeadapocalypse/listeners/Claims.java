package fr.pitrouflette.undeadapocalypse.listeners;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.Guild;
import fr.pitrouflette.undeadapocalypse.utils.manager.GuildManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.List;

public class Claims implements Listener {

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent ev){
        Player player = ev.getPlayer();
        List<Integer> claim = new ArrayList<>();
        claim.add(player.getLocation().getChunk().getX());
        claim.add(player.getLocation().getChunk().getZ());
        if(Main.claimedChunks.contains(claim)){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(claim)){
                    player.sendMessage("§4This chunk is claimed, therefore you can not break blocks here !");
                    ev.setCancelled(true);
                }
            }else{
                player.sendMessage("§4This chunk is claimed, therefore you can not break blocks here !");
                ev.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent ev){
        Player player = ev.getPlayer();
        List<Integer> claim = new ArrayList<>();
        claim.add(player.getLocation().getChunk().getX());
        claim.add(player.getLocation().getChunk().getZ());
        if(Main.claimedChunks.contains(claim)){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(claim)){
                    player.sendMessage("§4This chunk is claimed, therefore you can not place blocks here !");
                    ev.setCancelled(true);
                }
            }else{
                player.sendMessage("§4This chunk is claimed, therefore you can not place blocks here !");
                ev.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent ev){
        Player player = ev.getPlayer();
        List<Integer> claim = new ArrayList<>();
        claim.add(player.getLocation().getChunk().getX());
        claim.add(player.getLocation().getChunk().getZ());
        if(Main.claimedChunks.contains(claim)){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(claim)){
                    player.sendMessage("§4This chunk is claimed, therefore you can not fill a bucket here !");
                    ev.setCancelled(true);
                }
            }else{
                player.sendMessage("§4This chunk is claimed, therefore you can not break blocks here !");
                ev.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent ev){
        Player player = ev.getPlayer();
        List<Integer> claim = new ArrayList<>();
        claim.add(player.getLocation().getChunk().getX());
        claim.add(player.getLocation().getChunk().getZ());
        if(Main.claimedChunks.contains(claim)){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(claim)){
                    player.sendMessage("§4This chunk is claimed, therefore you can not drop items here !");
                    ev.setCancelled(true);
                }
            }else{
                player.sendMessage("§4This chunk is claimed, therefore you can not break blocks here !");
                ev.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerBed(PlayerBedEnterEvent ev){
        Player player = ev.getPlayer();
        List<Integer> claim = new ArrayList<>();
        claim.add(player.getLocation().getChunk().getX());
        claim.add(player.getLocation().getChunk().getZ());
        if(Main.claimedChunks.contains(claim)){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(claim)){
                    player.sendMessage("§4This chunk is claimed, therefore you can not  sleep here !");
                    ev.setCancelled(true);
                }
            }else{
                player.sendMessage("§4This chunk is claimed, therefore you can not break blocks here !");
                ev.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent ev){
        Player player = ev.getPlayer();
        List<Integer> claim = new ArrayList<>();
        claim.add(player.getLocation().getChunk().getX());
        claim.add(player.getLocation().getChunk().getZ());
        if(Main.claimedChunks.contains(claim)){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(claim)){
                    player.sendMessage("§4This chunk is claimed, therefore you can not empty your bucket here !");
                    ev.setCancelled(true);
                }
            }else{
                player.sendMessage("§4This chunk is claimed, therefore you can not break blocks here !");
                ev.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent ev){
        if(ev.getClickedBlock() == null) return;

        Material type = ev.getClickedBlock().getType();
        if(type == Material.CHEST || type == Material.TRAPPED_CHEST ||
                type.name().endsWith("_DOOR") ||
                type.name().endsWith("_TRAPDOOR")) {

            Player player = ev.getPlayer();
            List<Integer> claim = new ArrayList<>();
            claim.add(ev.getClickedBlock().getChunk().getX());
            claim.add(ev.getClickedBlock().getChunk().getZ());

            if(Main.claimedChunks.contains(claim)){
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(!guild.getClaims().contains(claim)){
                        player.sendMessage("§4This chunk is claimed, you can’t interact with containers or doors here!");
                        ev.setCancelled(true);
                    }
                } else {
                    player.sendMessage("§4This chunk is claimed, you can’t interact with containers or doors here!");
                    ev.setCancelled(true);
                }
            }
        }
    }

}
