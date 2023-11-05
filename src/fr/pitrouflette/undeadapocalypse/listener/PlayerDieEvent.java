package fr.pitrouflette.undeadapocalypse.listener;

import fr.pitrouflette.undeadapocalypse.utils.Guild;
import fr.pitrouflette.undeadapocalypse.utils.manager.GuildManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDieEvent implements Listener {

    @EventHandler
    public void playerDieEvent(PlayerDeathEvent ev){
        Player player = ev.getEntity().getPlayer();
        assert player != null;
        Player murder = player.getKiller();
        if(new GuildManager().isPlayerInAnyGuild(player)){
            Guild guild = new GuildManager().getPlayerGuild(player);
            if(guild.getPower() <= 9){return;}
            guild.removePower(5);
        }
        assert murder != null;
        if(new GuildManager().isPlayerInAnyGuild(murder)){
            Guild guild = new GuildManager().getPlayerGuild(murder);
            if(guild.getPower() >= 91){return;}
            guild.addPower(5);
        }
    }
}
