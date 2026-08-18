package com.nelson.rpg;

import com.nelson.rpg.command.AttributeCommand;
import com.nelson.rpg.listener.PlayerJoinListener;
import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.command.RpgCommand;
import com.nelson.rpg.command.AddXpCommand;
import com.nelson.rpg.service.AttributeService;
import com.nelson.rpg.service.ProgressionService;
import org.bukkit.plugin.java.JavaPlugin;
import com.nelson.rpg.listener.CombatListener;
import com.nelson.rpg.service.CombatService;


public class RpgPlugin extends JavaPlugin {

    private PlayerManager playerManager;

    @Override
    public void onEnable() {

        playerManager = new PlayerManager();
        ProgressionService progressionService = new ProgressionService();
        AttributeService attributeService = new AttributeService();
        CombatService combatService = new CombatService();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new CombatListener(playerManager, combatService), this);

        getCommand("rpg").setExecutor(new RpgCommand(playerManager));

        getCommand("addxp").setExecutor(new AddXpCommand(playerManager, progressionService));

        getCommand("attribute").setExecutor(new AttributeCommand(playerManager, attributeService));

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