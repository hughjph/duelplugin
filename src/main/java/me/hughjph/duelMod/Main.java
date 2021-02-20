package me.hughjph.duelMod;

import me.hughjph.duelMod.Commands.acceptChallenge;
import me.hughjph.duelMod.Commands.duelPlayer;
import me.hughjph.duelMod.events.accept;
import me.hughjph.duelMod.events.duelDeath;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){

        getCommand("duel").setExecutor(new duelPlayer());
        getCommand("a").setExecutor(new acceptChallenge());
        getServer().getPluginManager().registerEvents(new duelDeath(), this);
        //getServer().getPluginManager().registerEvents(new accept(), this);
    }

}
