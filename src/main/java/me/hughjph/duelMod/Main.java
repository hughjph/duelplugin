package me.hughjph.duelMod;



import javafx.animation.PathTransition;
import me.hughjph.duelMod.Commands.acceptChallenge;
import me.hughjph.duelMod.Commands.duelPlayer;
import me.hughjph.duelMod.classes.PlayerLocation;
import me.hughjph.duelMod.events.accept;
import me.hughjph.duelMod.events.duelDeath;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class Main extends JavaPlugin {

    public static List locationArray = new ArrayList();
    public File path = new File(this.getDataFolder(), "coordinates.json");


    @Override
    public void onEnable(){
        getCommand("duel").setExecutor(new duelPlayer());
        getCommand("a").setExecutor(new acceptChallenge());
        getServer().getPluginManager().registerEvents(new duelDeath(), this);
        getCoords();
    }


    public void getCoords(){
        if(!path.exists()){
            System.out.println(path);
        }

        try{

            JSONParser jsonParser = new JSONParser();

            Object parsed = jsonParser.parse(new FileReader(path.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray jsonArray = (JSONArray) jsonObject.get("Locations");
            System.out.println(jsonArray.toString());
            for(int i = 0; i<jsonArray.size(); i++){
                JSONObject location = (JSONObject) jsonArray.get(i);
                JSONArray locArray = (JSONArray) location.get("Location");
                for(Object locLines : locArray){
                    JSONObject locData = (JSONObject) locLines;
                    System.out.println(locData.get("x1"));
                }
            }


            /*
            for(int i = 0; i<jsonArray.size(); i++){
                JSONObject location = (JSONObject) jsonArray.get(i);
                System.out.println(location);
                System.out.println(location.get("x1"));
                int x = (int) location.get("x1");
                int y = (int) location.get("y1");
                int z = (int) location.get("z1");


                Location loc1 = new Location(Bukkit.getWorld("world"), x, y, z);
                Bukkit.broadcastMessage(loc1.toString());


                x = (int) location.get("x2");
                y = (int) location.get("y2");
                z = (int) location.get("z2");
                Location loc2 = new Location(Bukkit.getWorld("world"), x ,y, z);
                Bukkit.broadcastMessage(loc2.toString());


                locationArray.add(new PlayerLocation(loc1, loc2));
            }*/

        }catch(NullPointerException | ParseException | IOException e){
            e.printStackTrace();
        }
    }




}
