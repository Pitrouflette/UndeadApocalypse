package fr.pitrouflette.undeadapocalypse.commands;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.Guild;
import fr.pitrouflette.undeadapocalypse.utils.manager.GuildManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class GuildCommand implements TabExecutor {

    public GuildCommand(Main main) { }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length >= 1){
            if (args[0].equals("join") && args.length == 2) {
                new GuildManager().join(player, args[1]);
                new GuildManager().saveGuilds();
            }else if (args[0].equals("leave")) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    player.sendMessage("§4Vous venez de quitter la guild : " + new GuildManager().getPlayerGuild(player).getName());
                    new GuildManager().getPlayerGuild(player).removePlayer(player);
                    player.setCustomName(player.getDisplayName());
                    new GuildManager().saveGuilds();
                }else{
                    player.sendMessage("§4Vous n'êtes pas membres d'une guild !!");
                }
            }else if (args[0].equals("create") && args.length == 2) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    player.sendMessage("Vous ne pouvez pas créer de guild tant que vous êtes membre d'une autre guild !! (logique)");
                    return false;
                }
                try {
                    new GuildManager().create(player, args[1]);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }else if (args[0].equals("show")) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    player.sendMessage("§6===================" + guild.getColor() + guild.getName() + "§6===================");
                    player.sendMessage(guild.getColor() +"§lleader : §r§6" + guild.getChef().getDisplayName());
                    player.sendMessage(guild.getColor() +"§lréputation : §r§6" + guild.getPower());
                    player.sendMessage(guild.getColor() +"§lguild public : §r§6" + guild.isPublic());
                    player.sendMessage(guild.getColor() +"§lNombre de membres : §r§6" + guild.getPlayers().size());
                    player.sendMessage(" ");
                    player.sendMessage(guild.getColor() +"§lGénéral(aux) : §r§6");
                    for(UUID uuid : guild.getRank3()){
                        player.sendMessage(guild.getColor() +"- " + Objects.requireNonNull(Bukkit.getPlayer(uuid)).getDisplayName());
                    }
                    player.sendMessage(" ");
                    player.sendMessage(guild.getColor() +"§lofficier(s) : §r§6");
                    for(UUID uuid : guild.getRank2()){
                        player.sendMessage(guild.getColor() +"- " + Objects.requireNonNull(Bukkit.getPlayer(uuid)).getDisplayName());
                    }
                    player.sendMessage(" ");
                    player.sendMessage(guild.getColor() +"§lCommendant(s) : §r§6");
                    for(UUID uuid : guild.getRank1()){
                        player.sendMessage(guild.getColor() +"- " + Objects.requireNonNull(Bukkit.getPlayer(uuid)).getDisplayName());
                    }
                    player.sendMessage(" ");
                    player.sendMessage(guild.getColor() +"§lmembres(s) : §r§6");
                    List<UUID> members = guild.getPlayers();
                    //members.remove(guild.getRank1());
                    //members.remove(guild.getRank2());         -----> A REVOIR
                    //members.remove(guild.getRank3());
                    //members.remove(guild.getChef());
                    for(UUID uuid : members){
                        player.sendMessage(guild.getColor() +"- " + Objects.requireNonNull(Bukkit.getPlayer(uuid)).getDisplayName());
                    }
                    player.sendMessage("§6===================" + guild.getColor() + guild.getName() + "§6===================");
                }else{
                    player.sendMessage("§4Vous n'êtes dans aucune guild !!");
                }
            }else if (args[0].equals("disband")) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player)){
                        Main.guilds.remove(guild.getName());
                        new GuildManager().saveGuilds();
                    }
                }
            }else if (args[0].equals("setPrivate") && args.length == 2) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        if(args[1].equals("true")){
                            guild.setPublic(true);
                            player.sendMessage("§2Votre guild est désormais privée !");
                        }else{
                            guild.setPublic(false);
                            player.sendMessage("§2Votre guild est désormais publique !");
                        }
                        new GuildManager().saveGuilds();
                    }
                }
            }else if (args[0].equals("setName") && args.length == 2) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player)){
                        player.sendMessage("§2Le nom de votre guild est désormais : " + args[1]);
                        Main.guilds.remove(guild.getName());
                        Main.guilds.put(args[1], guild);
                        guild.setName(args[1]);
                        new GuildManager().saveGuilds();
                    }
                }
            }else if (args[0].equals("setHDV")) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        player.sendMessage("§2Vous avez définis les lieux de votre hotel de ville !");
                        guild.setHdv(player.getLocation());
                        guild.getClaims().set(0, player.getLocation().getChunk());
                        new GuildManager().saveGuilds();
                    }
                }
            }else if (args[0].equals("HDV")) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    player.teleport(guild.getHdv());
                    player.sendMessage("§4Vous avez été téléporté à votre hotel de ville");
                }
            }else if (args[0].equals("promote") && args.length == 2) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        if(Bukkit.getPlayer(args[1]) != null && guild.getPlayers().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                            Player playerProm = Bukkit.getPlayer(args[1]);
                            assert playerProm != null;
                            if(guild.getRank3().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                                player.sendMessage("§2Ce joueur est déjà au rang maximum !! " +
                                        "Si vous voulez le promouvoir d'aventage, vous devez le mettre chef de la guild ! (/guild setChef " + args[1] + ")");
                            }else if(guild.getRank2().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                                guild.removeRank2(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                guild.addRank3(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                player.sendMessage("§2Vous avez promu " + args[1] + " au rang de général");
                                playerProm.sendMessage("§2Vous avez été promu au rang de général par votre chef !");
                            }else if(guild.getRank1().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                                guild.removeRank1(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                guild.addRank2(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                player.sendMessage("§2Vous avez promu " + args[1] + " au rang de d'officier");
                                playerProm.sendMessage("§2Vous avez été promu au rang d'officier par votre chef !");
                            }else{
                                guild.addRank1(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                player.sendMessage("§2Vous avez promu " + args[1] + " au rang de commendant");
                                playerProm.sendMessage("§2Vous avez été promu au rang de commandant par votre chef !");
                            }
                            new GuildManager().saveGuilds();
                        }else{
                            player.sendMessage("§4Vous ne pouvez pas promote ce joueur !!");
                        }
                    }
                }
            }else if (args[0].equals("demote") && args.length == 2) {
                if(new GuildManager().isPlayerInAnyGuild(player)){
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        if(Bukkit.getPlayer(args[1]) != null && guild.getPlayers().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                            Player playerDem = Bukkit.getPlayer(args[1]);
                            assert playerDem != null;
                            if(guild.getRank3().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                                guild.removeRank3(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                guild.addRank2(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                playerDem.sendMessage("§4Vous avez été demote du rang de général, vous etes maintenant officier");
                                player.sendMessage("§4Vous avez demote " + args[1] + " du rang de général, il est maintenant officier");
                            }else if(guild.getRank2().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                                guild.removeRank2(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                guild.addRank1(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                playerDem.sendMessage("§4Vous avez été demote du rang d'officier, vous etes maintenant commendant");
                                player.sendMessage("§4Vous avez demote " + args[1] + " du rang d'officier, il est maintenant commendant");
                            }else if(guild.getRank1().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                                guild.removeRank1(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                                playerDem.sendMessage("§4Vous avez été demote du rang de commendant, vous etes maintenant simple membre");
                                player.sendMessage("§4Vous avez demote " + args[1] + " du rang de commendant, il est maintenant simple membre");
                            }
                            new GuildManager().saveGuilds();
                        }else{
                            player.sendMessage("§4Vous ne pouvez pas promote ce joueur !!");
                        }
                    }
                }
            }else if (args[0].equals("debug")) {
                Guild guild = new GuildManager().getPlayerGuild(Objects.requireNonNull(Bukkit.getPlayer("Pitrouflette")));
                for(Player players : Bukkit.getOnlinePlayers()){
                    guild.addPlayers(players);
                }
                new GuildManager().saveGuilds();
            }else if (args[0].equals("setChef") && args.length == 2) {
                if(new GuildManager().isPlayerInAnyGuild(player)) {
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player)){
                        if(Bukkit.getPlayer(args[1]) != null && guild.getPlayers().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                            guild.setChef(Bukkit.getPlayer(args[1]));
                            player.sendMessage("§2Le nouveau leader de la guild est : " + args[1]);
                            guild.addRank1(player);
                            player.sendMessage("§4Vous avez été promu au rang de commendant");
                            new GuildManager().saveGuilds();
                        }
                    }
                }
            }else if (args[0].equals("kick") && args.length == 2) {
                if(new GuildManager().isPlayerInAnyGuild(player)) {
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        if(Bukkit.getPlayer(args[1]) != null && guild.getPlayers().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                            guild.removePlayer(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                            player.sendMessage("§4Vous avez expulsé : " + args[1]);
                            new GuildManager().saveGuilds();
                        }
                    }
                }
            }else if (args[0].equals("setColor")) {
                if(new GuildManager().isPlayerInAnyGuild(player)) {
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        guild.setColor(args[1].replace("&", "§"));
                        player.sendMessage(args[1].replace("&", "§") + "La couleur de votre guild a été changé !");
                        new GuildManager().saveGuilds();
                    }
                }
            }else if (args[0].equals("invite") && args.length == 2){
                if(new GuildManager().isPlayerInAnyGuild(player)) {
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(!guild.isPublic()){player.sendMessage("§4Votre guild est public, pas besoin d'invitation !"); return true;}
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        if(Bukkit.getPlayer(args[1]) != null && !guild.getPlayers().contains(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId())){
                            Player invited = Bukkit.getPlayer(args[1]);
                            invited.sendMessage("§2Vous avez été invité dans la guild " + guild.getName());
                            Main.invitation.put(invited, guild);
                        }
                    }
                }
            }else if(args[0].equals("chat")){
                if(Main.guildChat.get(player).equals(true)){
                    Main.guildChat.put(player, false);
                    player.sendMessage("§2Chat message disabled !");
                }else{
                    Main.guildChat.put(player, true);
                    player.sendMessage("§2Chat message enabled !");
                }
            }else if(args[0].equals("claim")){                      // CLAIM SECTION
                if(new GuildManager().isPlayerInAnyGuild(player)) {
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        Chunk chunk = player.getLocation().getChunk();
                        if((guild.getClaims().size() * 5) < guild.getPower()){
                            if(!guild.getClaims().contains(chunk)){
                                if(!Main.claimedChunks.contains(chunk)){
                                    guild.addClaim(chunk);
                                    new GuildManager().saveGuilds();
                                    player.sendMessage("§2You've successfully claimed this chunk !");
                                }else{
                                    player.sendMessage("§4This chunk has already been claimed by another guild !");
                                }
                            }else{
                                player.sendMessage("§4This chunk already belong to your guild !");
                            }
                        }else{
                            player.sendMessage("§4Your reputation isn't height enough !!");
                        }
                    }
                }
            }else if(args[0].equals("unClaim")){
                if(new GuildManager().isPlayerInAnyGuild(player)) {
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getChef().equals(player) || guild.getRank3().contains(player.getUniqueId())){
                        Chunk chunk = player.getLocation().getChunk();
                        if(guild.getClaims().contains(chunk)){
                            guild.removeClaim(chunk);
                            new GuildManager().saveGuilds();
                            player.sendMessage("§2You've successfully un-claimed this chunk !");
                        }else{
                            player.sendMessage("§2This chunk has not been claimed by your guild, therefore, you can not un-claim it !");
                        }
                    }
                }
            }else if(args[0].equals("isClaimed")){
                if(new GuildManager().isPlayerInAnyGuild(player)) {
                    Guild guild = new GuildManager().getPlayerGuild(player);
                    if(guild.getClaims().contains(player.getLocation().getChunk())){
                        player.sendMessage("§2You guild own this chunk !");
                    }else if(Main.claimedChunks.contains(player.getLocation().getChunk())){
                        player.sendMessage("§4Another guild own this chunk !");
                    }else{
                        player.sendMessage("§2This chunk is free !");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 1){
            List<String> completions = new ArrayList<>();
            completions.add("join");
            completions.add("create");
            completions.add("help");
            if(new GuildManager().isPlayerInAnyGuild(player)){
                completions.add("leave");
                completions.add("show");
                completions.add("HDV");
                completions.add("chat");
                completions.add("isClaimed");
                completions.remove("join");
                completions.remove("create");
                Guild guild = new GuildManager().getPlayerGuild(player);
                if(guild.getRank1().contains(player.getUniqueId())){
                    completions.add("invite");
                }
                if(guild.getRank3().contains(player.getUniqueId())){
                    completions.add("kick");
                    completions.add("promote");
                    completions.add("demote");
                    completions.add("setPrivate");
                    completions.add("setHDV");
                    completions.add("setColor");
                    completions.add("invite");
                    completions.add("claim");
                    completions.add("unClaim");
                }
                if(guild.getChef().equals(player)){
                    completions.add("setName");
                    completions.add("setPrivate");
                    completions.add("setHDV");
                    completions.add("disband");
                    completions.add("promote");
                    completions.add("demote");
                    completions.add("setChef");
                    completions.add("setColor");
                    completions.add("kick");
                    completions.add("invite");
                    completions.add("claim");
                    completions.add("unClaim");
                }
            }
            return completions;

        }else if(args[0].equals("create")){
            List<String> completions = new ArrayList<>();
            completions.add("[name]");
            return completions;

        }else if(args[0].equals("join")){
            List<String> completions = new ArrayList<>();
            for (Guild guild : Main.guilds.values()) {
                if(guild.isPublic()){
                    if(!guild.getPlayers().contains(player.getUniqueId())){
                        completions.add(guild.getName());
                    }
                }
            }
            return completions;

        }else if(args[0].equals("setPrivate")){
            Guild guild = new GuildManager().getPlayerGuild(player);
            if(guild.getRank3().contains(player.getUniqueId()) || guild.getChef().equals(player)){
                List<String> completions = new ArrayList<>();
                completions.add("true");
                completions.add("false");
                return completions;
            }
        }else if(args[0].equals("setName")){
            Guild guild = new GuildManager().getPlayerGuild(player);
            if(guild.getChef().equals(player)){
                List<String> completions = new ArrayList<>();
                completions.add("[name]");
                return completions;
            }
        }else if(args[0].equals("promote")){
            List<String> completions = new ArrayList<>();
            Guild guild = new GuildManager().getPlayerGuild(player);
            if(guild.getRank3().contains(player.getUniqueId()) || guild.getChef().equals(player)){
                for(UUID uudis : guild.getPlayers()){
                    completions.add(Objects.requireNonNull(Bukkit.getPlayer(uudis)).getDisplayName());
                }
                completions.remove(player.getDisplayName());
                return completions;
            }
        }else if(args[0].equals("demote") || args[0].equals("setChef") || args[0].equals("kick")){
            List<String> completions = new ArrayList<>();
            Guild guild = new GuildManager().getPlayerGuild(player);
            for(UUID uudis : guild.getPlayers()){
                completions.add(Objects.requireNonNull(Bukkit.getPlayer(uudis)).getDisplayName());
            }
            completions.remove(player.getDisplayName());
            return completions;

        }else if(args[0].equals("setColor")){
            List<String> completions = new ArrayList<>();
            Guild guild = new GuildManager().getPlayerGuild(player);
            if(guild.getRank3().contains(player.getUniqueId()) || guild.getChef().equals(player)){
                completions.add("&4 - dark_red");
                completions.add("&c - red");
                completions.add("&6 - gold");
                completions.add("&e - yellow");
                completions.add("&2 - dark_green");
                completions.add("&a - green");
                completions.add("&b - aqua");
                completions.add("&3 - dark_aqua");
                completions.add("&1 - dark_blue");
                completions.add("&9 - blue");
                completions.add("&d - light_purple");
                completions.add("&5 - dark_purple");
                completions.add("&f - white");
                completions.add("&7 - gray");
                completions.add("&8 - dark_gray");
                completions.add("&0 - black");
                completions.add("&l - bold");
                completions.add("&o - italic");
                completions.add("&n - underline");
                completions.add("&m - crossed");
                completions.add("&k - randomised characters");
                return completions;
            }
        }else if(args[0].equals("invite")){
            List<String> completions = new ArrayList<>();
            for(Player playerFor : Bukkit.getOnlinePlayers()){
                if(!new GuildManager().isPlayerInAnyGuild(playerFor)){
                    completions.add(player.getDisplayName());
                }
            }
            return completions;
        }
        return null;
    }
}
