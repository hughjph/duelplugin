package me.hughjph.duelMod.events;

import me.hughjph.duelMod.Commands.duelPlayer;
import me.hughjph.duelMod.Main;
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
            dead.setHealth(20);
            dead.teleport(Bukkit.getWorld("world").getSpawnLocation());

            Player winner = dead.getKiller();

            //finding the index of the map in the locationsTaken array and removing it
            if(duelPlayer.duelingPlayers.indexOf(winner.getName()) % 2 == 0){
                Main.locationsTaken.remove(duelPlayer.duelingPlayers.indexOf(winner.getName()) / 2);
            } else{
                Main.locationsTaken.remove(duelPlayer.duelingPlayers.indexOf(dead.getName()) / 2);
            }
            System.out.println(Main.locationsTaken);

            //removing them from the currently duelling list
            duelPlayer.duelingPlayers.remove(dead.getName());
            duelPlayer.duelingPlayers.remove(winner.getName());

            //Sending them their win/lose messages
            dead.sendMessage("§6You Lost");
            winner.sendMessage("§6You Win!");

            //teleporting winner back to main world
            winner.setHealth(20);
            winner.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }
    }


}
