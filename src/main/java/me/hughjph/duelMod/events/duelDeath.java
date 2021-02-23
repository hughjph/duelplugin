package me.hughjph.duelMod.events;

import me.hughjph.duelMod.Commands.duelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class duelDeath implements Listener {

    //Checks if the player in the duel died
    @EventHandler
    public static void onDuelDeath(PlayerDeathEvent event){

        Player dead = event.getEntity();
        //Checking if the player who died is in the duel
        if(duelPlayer.duelingPlayers.contains(dead.getName())){
            //getting the duel winner
            Player winner = dead.getKiller();

            //removing them from the currently duelling list
            duelPlayer.duelingPlayers.remove(dead.getName());
            duelPlayer.duelingPlayers.remove(winner.getName());

            //Sending them their win/lose messages
            dead.sendMessage("§6You Lost");
            winner.sendMessage("§6You Win!");

            //killing off the winner to get him out of the duel arena
            winner.setHealth(0);
        }
    }


}
