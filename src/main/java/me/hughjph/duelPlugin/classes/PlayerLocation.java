package me.hughjph.duelMod.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class PlayerLocation{

    public String wName;
    public int x1;
    public int y1;
    public int z1;
    public int x2;
    public int y2;
    public int z2;




    public PlayerLocation(String world, int locx1, int locy1, int locz1, int locx2, int locy2, int locz2){
        wName = world;
        x1 = locx1;
        x2 = locx2;
        y1 = locy1;
        y2 = locy2;
        z1 = locz1;
        z2 = locz2;
    }

}
