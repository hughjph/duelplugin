package me.hughjph.duelMod.Commands;

import me.hughjph.duelMod.Main;
import me.hughjph.duelMod.classes.PlayerLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class duelPlayer implements CommandExecutor {

    public static List challengedPlayers = new ArrayList();
    public static List challengingPlayers = new ArrayList();
    public static List duelingPlayers = new ArrayList();


    public static Location p1Spawn = null;
    public static Location p2Spawn = null;


    //Taking the command /duel <player>
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //making sure only a player is sending the command
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can use that command");
            return true;
        }

        //if players are duelling it wont run
        if(Main.locationArray.size() == Main.locationsTaken.size()){
            sender.sendMessage("§5There are currently no free duel spots!");
            return true;
        }

        Player p = (Player) sender;

        //checking if a player has been challenged with the duel command
        if(args.length != 1){
            sender.sendMessage("You need to challenge a player");
            return false;
        }




        if(label.equalsIgnoreCase("duel")){

            if(args.length == 1){
                //getting the target player
                Player targetPlayer = p.getServer().getPlayer(args [0]);

                //making sure that they aren't trying to duel themselves
                if(targetPlayer.getName() == p.getName()){
                    p.sendMessage("You can't duel yourself!");
                    return true;
                }

                //sending duel messages
                p.sendMessage("Sent duel request");
                targetPlayer.sendMessage("§c"+ p.getName() + " has challenged you to a duel! Type §5/a §cto accept");

                //adding them to the challenged players pool
                challengedPlayers.add(targetPlayer.getName());
                challengingPlayers.add(p.getName());

                //start the countdown for how long the timer will last
                countdown(targetPlayer, p);

            }
        }
        return true;

    }

    //Counting down time until a player accepts, or doesn't accept a challenge
    public void countdown(Player targetPlayer, Player player){

        //Referencing the plugin
        Plugin plugin = Main.getPlugin(Main.class);

        new BukkitRunnable() {
            //seconds until the timer is up
            int count = 60;

            @Override
            public void run() {
                count--;

                if(count == 0){
                    //Removing the players from the challenged players list so they can't accept it after the minute is up
                    if(challengedPlayers.contains(targetPlayer.getName())){
                        targetPlayer.sendMessage("Challenge Expired");
                        player.sendMessage("Challenge Expired");
                        challengedPlayers.remove(targetPlayer.getName());
                        challengingPlayers.remove(player.getName());
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
        //20 is amount of minecraft ticks per second so you have 20 ticks before count decreases



    }

    public static int FindLocation(){
        for(int i = 0; i<Main.locationArray.size(); i++){
            if(Main.locationsTaken.contains(i)){
                System.out.println(i + " taken");
            }
            else{
                System.out.println("Location found at " + i);
                return i;
            }

        }
        return -1;
    }


    //Function for starting the duel
    @SuppressWarnings("unchecked")
    public static void DuelStart(Player Challenger, Player Challenged){
        int LocationNum = FindLocation();

        if(LocationNum == -1){
            Challenged.sendMessage("There are no free duel slots try again later!");
            Challenger.sendMessage("There are no free duel slots try again later!");
            return;
        }

        Main.locationsTaken.add(LocationNum);

        //Removing players from the list of players requested to duel
        challengingPlayers.remove(Challenger.getName());
        challengedPlayers.remove(Challenged.getName());

        //Adding players to the list of players who are currently duelling
        duelingPlayers.add(Challenged.getName());
        duelingPlayers.add(Challenger.getName());

        //Giving players 3 splash potions of regeneration
        ItemStack healingPotion = new ItemStack(Material.SPLASH_POTION, 3);
        PotionMeta potionMeta = (PotionMeta) healingPotion.getItemMeta();
        PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 1000, 1);
        potionMeta.addCustomEffect(regen, true);
        potionMeta.setDisplayName("Splash Potion of Regeneration");
        healingPotion.setItemMeta(potionMeta);

        //Declaring the sword and giving it sharpness
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD, 1);
        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);

        //Declaring the Items and the armour for the player
        ItemStack[] duelKit = {sword, new ItemStack(healingPotion)};
        //The armour ItemStack[] has to be in that order for the armour pieces to be in the right armour slot
        ItemStack[] armour = {new ItemStack(Material.NETHERITE_BOOTS), new ItemStack(Material.NETHERITE_LEGGINGS)
        ,new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.NETHERITE_HELMET)};

        //Applying protection to the player's armour
        for(int i = 0; i < armour.length; i++){
            armour[i].addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        }

        //Spawn locations for the duel, only supports one duel at a time



        PlayerLocation loc = (PlayerLocation) Main.locationArray.get(LocationNum);

        Challenged.sendMessage("§aSending you to " + loc.wName);
        Challenger.sendMessage("§aSending you to " + loc.wName);
        //Teleporting the players to the relevant spawn point

        initLoc1(LocationNum, Challenger);
        initLoc2(LocationNum, Challenged);



        //Clearing the inventories and adding the armour and item kits
        Challenger.getInventory().clear();
        Challenger.getInventory().addItem(duelKit);
        Challenger.getInventory().setArmorContents(armour);

        Challenged.getInventory().clear();
        Challenged.getInventory().addItem(duelKit);
        Challenged.getInventory().setArmorContents(armour);

        //Setting health to 20 so they start the fight on full health
        Challenged.setHealth(20);
        Challenger.setHealth(20);


    }


    static void initLoc1(int index, Player player){
        PlayerLocation loc = (PlayerLocation) Main.locationArray.get(index);
        System.out.println("mvtp " + player.getName() + " "+ loc.wName + " "+ loc.x1 + " "+ loc.y1 + " "+ loc.z1);
        player.teleport(new Location(Bukkit.getWorld(loc.wName), loc.x1, loc.y1, loc.z1));
        //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + player.getName() + " "+ loc.wName + " "+ loc.x1 + " "+ loc.y1 + " "+ loc.z1);
        return;

    }

    static void initLoc2(int index, Player player){
        PlayerLocation loc = (PlayerLocation) Main.locationArray.get(index);
        player.teleport(new Location(Bukkit.getWorld(loc.wName), loc.x1, loc.y1, loc.z1));
        //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + player.getName() + " " + " "+ loc.wName + " "+ loc.x2 + " "+ loc.y2 + " "+ loc.z2);
        return;

    }


}



