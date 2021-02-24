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
    public static List locationsTaken = new ArrayList();
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
            //getting the JSON data from the json file
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

                    int x1 = Integer.parseInt(locData.get("x1").toString());
                    int y1 = Integer.parseInt(locData.get("y1").toString());
                    int z1 = Integer.parseInt(locData.get("z1").toString());

                    int x2= Integer.parseInt(locData.get("x2").toString());
                    int y2 = Integer.parseInt(locData.get("y2").toString());
                    int z2 = Integer.parseInt(locData.get("z2").toString());




                    locationArray.add(new PlayerLocation(x1, y1, z1, x2, y2, z2));


                }
            }

        }catch(NullPointerException | ParseException | IOException e){
            e.printStackTrace();
        }
    }




}
