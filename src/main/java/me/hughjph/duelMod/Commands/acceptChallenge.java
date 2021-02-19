package me.hughjph.duelMod.Commands;

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
        p.sendMessage("GOLOLOGO");
        if(label.equalsIgnoreCase("a")){
            p.sendMessage("Command Executed");
            if(duelPlayer.challengedPlayers.contains(p.getName())){
                p.sendMessage("You have accepted the challenge");
                Player Challenger = Bukkit.getPlayer(duelPlayer.challengingPlayers.get(duelPlayer.challengedPlayers.indexOf(p.getName())).toString());
                duelPlayer.DuelStart(Challenger, p);
            }else{
                p.sendMessage("You have not been challenged");
            }
        }

        return true;


    }

}
