package me.hughjph.duelMod.Commands;

import com.jcabi.aspects.Async;
import jdk.nashorn.internal.parser.JSONParser;
import me.hughjph.duelMod.Main;
import me.hughjph.duelMod.classes.Duel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class duelPlayer implements CommandExecutor {

    public static List challengedPlayers = new ArrayList();
    public static List challengingPlayers = new ArrayList();
    public static List duelingPlayers = new ArrayList();


    public static Location p1Spawn = null;
    public static Location p2Spawn = null;



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can use that command");
            return true;
        }

        Player p = (Player) sender;

        if(args.length != 1){
            sender.sendMessage("You need to challenge a player");
            return false;
        }

        if(label.equalsIgnoreCase("duel")){

            if(args.length == 1){
                Player targetPlayer = p.getServer().getPlayer(args [0]);
                if(targetPlayer.getName() == p.getName()){
                    p.sendMessage("You can't duel yourself!");
                    return true;
                }
                p.sendMessage("Sent duel request");
                targetPlayer.sendMessage("§c"+ p.getName() + " has challenged you to a duel! Type §5/a §cto accept");
                challengedPlayers.add(targetPlayer.getName());
                challengingPlayers.add(p.getName());
                countdown(targetPlayer, p);

            }
        }
        return true;

    }

    public void countdown(Player targetPlayer, Player player){

        Plugin plugin = Main.getPlugin(Main.class);

        new BukkitRunnable() {
            int count = 60;
            @Override
            public void run() {
                count--;
                if(count == 0){
                    if(challengedPlayers.contains(targetPlayer.getName())){
                        targetPlayer.sendMessage("Challenge Expired");
                        player.sendMessage("Challenge Expired");
                        challengedPlayers.remove(targetPlayer.getName());
                        challengingPlayers.remove(player.getName());
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);



    }



    @SuppressWarnings("unchecked")
    public static void DuelStart(Player Challenger, Player Challenged){
        challengingPlayers.remove(Challenger.getName());
        challengedPlayers.remove(Challenged.getName());
        duelingPlayers.add(Challenged.getName());
        duelingPlayers.add(Challenger.getName());

        ItemStack healingPotion = new ItemStack(Material.SPLASH_POTION, 3);
        PotionMeta potionMeta = (PotionMeta) healingPotion.getItemMeta();
        PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 1000, 1);
        potionMeta.addCustomEffect(regen, true);
        healingPotion.setItemMeta(potionMeta);


        ItemStack[] duelKit = {new ItemStack(Material.NETHERITE_SWORD), new ItemStack(healingPotion)};
        ItemStack[] armour = {new ItemStack(Material.NETHERITE_BOOTS), new ItemStack(Material.NETHERITE_LEGGINGS)
        ,new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.NETHERITE_HELMET)};

        p1Spawn = new Location(Challenger.getWorld(), -331, 74, -427);
        p2Spawn = new Location(Challenger.getWorld(), -342, 74, -426);

        Challenged.sendMessage("You will be teleported to " + p2Spawn);
        Challenger.sendMessage("You will be teleported to " + p1Spawn);
        Challenged.teleport(p2Spawn);
        Challenger.teleport(p1Spawn);

        Challenger.getInventory().clear();
        Challenger.getInventory().addItem(duelKit);
        Challenger.getInventory().setArmorContents(armour);

        Challenged.getInventory().clear();
        Challenged.getInventory().addItem(duelKit);
        Challenged.getInventory().setArmorContents(armour);

        Challenged.setHealth(20);
        Challenger.setHealth(20);

        /*
        org.json.simple.parser.JSONParser parser;
        parser = new org.json.simple.parser.JSONParser();
        boolean coords1got = false;


        try (FileReader reader = new FileReader("C:\\Users\\hughj\\OneDrive\\Desktop\\coordinates.json")) {
            Object obj = parser.parse(reader);

            JSONArray coords = (JSONArray) obj;




            coords.forEach(coord -> parseCoords((JSONObject) coord));



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
        }*/
    }


    private static Integer[] parseCoords(JSONObject coord){

        JSONObject coordObj = (JSONObject) coord.get("Player");

        Integer[] coordinates = new Integer[3];

        coordinates[0] = (int) coordObj.get("x");
        coordinates[1] = (int) coordObj.get("y");
        coordinates[2] = (int) coordObj.get("z");

        return coordinates;
    }

}



