package me.hughjph.duelMod;

import me.hughjph.duelMod.Commands.duelPlayer;
import me.hughjph.duelMod.events.accept;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){

        getCommand("duel").setExecutor(new duelPlayer());
        getServer().getPluginManager().registerEvents(new accept(), this);
    }

}
