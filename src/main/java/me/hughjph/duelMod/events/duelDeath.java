package me.hughjph.duelMod.events;

import me.hughjph.duelMod.Commands.duelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class duelDeath implements Listener {

    @EventHandler
    public static void onDuelDeath(PlayerDeathEvent event){

        Player dead = event.getEntity();

        if(duelPlayer.duelingPlayers.contains(dead.getName())){

            Player winner = dead.getKiller();

            duelPlayer.duelingPlayers.remove(dead.getName());
            duelPlayer.duelingPlayers.remove(winner.getName());


            dead.sendMessage("§6You Lost");
            winner.sendMessage("§6You Win!");

            winner.setHealth(0);



            /*
            winner.getInventory().clear();
            Bukkit.dispatchCommand(winner, "givewand");
            Bukkit.dispatchCommand(winner, "giveclock");

             */
        }
    }


}
