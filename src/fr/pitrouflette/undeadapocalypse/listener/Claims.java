package fr.pitrouflette.undeadapocalypse.listener;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.Guild;
import fr.pitrouflette.undeadapocalypse.utils.manager.GuildManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.*;

public class Claims implements Listener {

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent ev){
        Player player = ev.getPlayer();
        if(Main.claimedChunks.contains(player.getLocation().getChunk())){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(player.getLocation().getChunk())){
                    player.sendMessage("§4This chunk is claimed, therefore you can not break blocks here !");
                    ev.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent ev){
        Player player = ev.getPlayer();
        if(Main.claimedChunks.contains(player.getLocation().getChunk())){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(player.getLocation().getChunk())){
                    player.sendMessage("§4This chunk is claimed, therefore you can not fill a bucket here !");
                    ev.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent ev){
        Player player = ev.getPlayer();
        if(Main.claimedChunks.contains(player.getLocation().getChunk())){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(player.getLocation().getChunk())){
                    player.sendMessage("§4This chunk is claimed, therefore you can not drop items here !");
                    ev.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerBed(PlayerBedEnterEvent ev){
        Player player = ev.getPlayer();
        if(Main.claimedChunks.contains(player.getLocation().getChunk())){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(player.getLocation().getChunk())){
                    player.sendMessage("§4This chunk is claimed, therefore you can not  sleep here !");
                    ev.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent ev){
        Player player = ev.getPlayer();
        if(Main.claimedChunks.contains(player.getLocation().getChunk())){
            if(new GuildManager().isPlayerInAnyGuild(player)){
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(!guild.getClaims().contains(player.getLocation().getChunk())){
                    player.sendMessage("§4This chunk is claimed, therefore you can not empty your bucket here !");
                    ev.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("RIGHT_CLICK") && event.getClickedBlock() != null) {
            if(event.getClickedBlock().getType().toString().contains("door")) {
                Player player = event.getPlayer();
                if(Main.claimedChunks.contains(player.getLocation().getChunk())){
                    if(new GuildManager().isPlayerInAnyGuild(player)){
                        Guild guild = new GuildManager().getPlayerGuild(player);
                        if(!guild.getClaims().contains(player.getLocation().getChunk())){
                            player.sendMessage("§4This chunk is claimed, therefore you can not open doors here !");
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
