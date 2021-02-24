package me.hughjph.duelMod.Commands;

import me.hughjph.duelMod.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class acceptChallenge implements CommandExecutor {

    @Override public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can use this command");
            return true;
        }


        Player p = (Player) sender;

        if(label.equalsIgnoreCase("a")){

            if(duelPlayer.challengedPlayers.contains(p.getName())){
                //getting the challenger of the duel
                Player Challenger = Bukkit.getPlayer(duelPlayer.challengingPlayers.get(duelPlayer.challengedPlayers.indexOf(p.getName())).toString());

                //checking if there is a free duel spot
                if(Main.locationArray.size() == Main.locationsTaken.size()){
                    sender.sendMessage("§5There are currently no free duel spots!");
                    Challenger.sendMessage("§5There are currently no free duel spots!");
                    return true;
                }

                //accepting challenge messages
                p.sendMessage("You have accepted the challenge!");
                Challenger.sendMessage("The challenge has been accepted!");

                //starting the duel
                duelPlayer.DuelStart(Challenger, p);
            }else{
                p.sendMessage("You have not been challenged!");
            }
        }

        return true;


    }

}
