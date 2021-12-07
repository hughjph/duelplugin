package me.hughjph.duelMod.events;

import com.jcabi.aspects.Async;
import me.hughjph.duelMod.Commands.duelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class accept implements Listener {

    private HashMap<UUID,Long> map = new HashMap<UUID,Long>();

    @EventHandler
    public static void chatEvent(AsyncPlayerChatEvent e){
        /*
        Player p = e.getPlayer();
        String msg = e.getMessage();
        p.sendMessage("Chat incoming");
        p.sendMessage(duelPlayer.challengedPlayers.toString());
        p.sendMessage(msg);
        boolean player = duelPlayer.challengedPlayers.contains(p.getName());
        p.sendMessage(player + "");
        if(duelPlayer.challengedPlayers.contains(p.getName())){
            p.sendMessage("Challenger verified");
            if(msg == "a"){
                p.sendMessage("You have accepted the challenge");
                Player Challenger = Bukkit.getPlayer(duelPlayer.challengingPlayers.get(duelPlayer.challengedPlayers.indexOf(p.getName())).toString());
                duelPlayer.DuelStart(Challenger, p);
            }

        }*/
    }



}
