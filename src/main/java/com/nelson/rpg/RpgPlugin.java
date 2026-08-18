package com.nelson.rpg;

import com.nelson.rpg.listener.PlayerJoinListener;
import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.command.RpgCommand;
import org.bukkit.plugin.java.JavaPlugin;


public class RpgPlugin extends JavaPlugin {

    private PlayerManager playerManager;

    @Override
    public void onEnable() {

        playerManager = new PlayerManager();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerManager), this);

        getCommand("rpg").setExecutor(new RpgCommand(playerManager));

        getLogger().info("RPG Plugin iniciado!");
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public void onDisable() {

        getLogger().info("RPG Plugin desligado!");

    }
}