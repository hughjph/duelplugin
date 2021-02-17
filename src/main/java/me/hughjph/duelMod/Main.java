package me.hughjph.duelMod;

import me.hughjph.duelMod.Commands.duelPlayer;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){
        getCommand("duel").setExecutor(new duelPlayer());
    }

}
