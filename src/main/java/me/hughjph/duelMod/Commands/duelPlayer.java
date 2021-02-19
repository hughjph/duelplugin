package me.hughjph.duelMod.Commands;

import com.jcabi.aspects.Async;
import jdk.nashorn.internal.parser.JSONParser;
import me.hughjph.duelMod.classes.Duel;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

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
                targetPlayer.sendMessage(p.getName() + " has challenged you to a duel! Type a to accept");
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

    @SuppressWarnings("unchecked")
    public static void DuelStart(Player Challenger, Player Challenged){
        org.json.simple.parser.JSONParser parser;
        parser = new org.json.simple.parser.JSONParser();
        boolean coords1got = false;
        Location p1Spawn;
        Location p2Spawn;

        try (FileReader reader = new FileReader("../../../../../../coordinates.json")) {
            Object obj = parser.parse(reader);

            JSONArray coords = (JSONArray) obj;

            for (int i = 0; i < coords.size(); i++){
                JSONObject coordObj = (JSONObject) coords.get(i);

                Integer X = (int) coordObj.get("x");
                Integer Y = (int) coordObj.get("y");
                Integer Z = (int) coordObj.get("z");

                if(coords1got){
                    p2Spawn =  new Location (Challenged.getWorld(), X, Y, Z);
                    Challenged.sendMessage("You will teleport to here " + p2Spawn);

                } else{
                    p1Spawn = new Location (Challenged.getWorld(), X, Y, Z);
                    Challenger.sendMessage("You will teleport to here " + p1Spawn);

                    coords1got = true;
                }
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseCoords(JSONObject coord){
        JSONObject coordObj = (JSONObject) coord.get("Player");

        Integer X = (int) coordObj.get("x");
        Integer Y = (int) coordObj.get("y");
        Integer Z = (int) coordObj.get("z");

    }

}



