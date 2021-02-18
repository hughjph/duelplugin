package me.hughjph.duelMod.Commands;

import com.jcabi.aspects.Async;
import me.hughjph.duelMod.classes.Duel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class duelPlayer implements CommandExecutor {

    public static List<Duel> challengedPlayers = new ArrayList();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can use that command");
            return true;
        }

        Player p = (Player) sender;
/*
        if(args.length != 1){
            sender.sendMessage("You need to challenge a player");
            return false;
        }*/

        if(label.equalsIgnoreCase("duel")){

            if(args.length == 1){
                Player targetPlayer = p.getServer().getPlayer(args [0]);
                p.sendMessage("Sent duel request");
                targetPlayer.sendMessage(p.getName() + " has challenged you to a duel! Type /a to accept");
                challengedPlayers.add(new Duel(targetPlayer.getName(), p.getName()));
                challengerCountdown(targetPlayer, p);
            }
        }
        return true;

    }
    @Async
    public void challengerCountdown(Player targetPlayer, Player player){
        boolean accepted = false;
        long sec = 1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(challengedPlayers.contains(targetPlayer.getName())){
            targetPlayer.sendMessage("Challenge Expired");
            player.sendMessage("Challenge Expired");
            challengedPlayers.remove(targetPlayer.getName());
        } else{
            return;
        }


    }


    public static void DuelStart(Player Challenger, Player Challenged){

    }



}



