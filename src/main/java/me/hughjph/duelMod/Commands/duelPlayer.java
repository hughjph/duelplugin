package me.hughjph.duelMod.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class duelPlayer implements CommandExecutor {
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
            p.sendMessage("kljdlaskds");
            if(args.length == 1){
                Player targetPlayer = p.getServer().getPlayer(args [0]);
                p.sendMessage("Sent duel request");
                targetPlayer.sendMessage(p.getName() + " has challenged you to a duel!");
            }
        }
        return true;

    }
}
