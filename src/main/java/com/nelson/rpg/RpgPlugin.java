package com.nelson.rpg;

import com.nelson.rpg.command.*;
import com.nelson.rpg.listener.PlayerJoinListener;
import com.nelson.rpg.listener.SkillListener;
import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.service.*;
import com.nelson.rpg.task.ManaRegenerationTask;
import org.bukkit.plugin.java.JavaPlugin;
import com.nelson.rpg.listener.CombatListener;
import com.nelson.rpg.listener.AttributeMenuListener;


public class RpgPlugin extends JavaPlugin {

    private PlayerManager playerManager;

    @Override
    public void onEnable() {

        playerManager = new PlayerManager();

        ProgressionService progressionService = new ProgressionService();

        AttributeService attributeService = new AttributeService();

        AttributeMenuCommand attributeMenuCommand = new AttributeMenuCommand(playerManager);

        CombatService combatService = new CombatService();

        ManaService manaService = new ManaService();

        SkillService skillService = new SkillService(manaService);

        ManaRegenerationTask manaRegenerationTask = new ManaRegenerationTask(playerManager, manaService);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new CombatListener(playerManager, combatService), this);
        getServer().getPluginManager().registerEvents(new AttributeMenuListener(playerManager, attributeService,attributeMenuCommand), this);
        getServer().getPluginManager().registerEvents(new SkillListener(this, playerManager, skillService), this);
        getServer().getScheduler().runTaskTimer(this, manaRegenerationTask, 40L, 40L);

        getCommand("rpg").setExecutor(new RpgCommand(playerManager));

        getCommand("addxp").setExecutor(new AddXpCommand(playerManager, progressionService));

        getCommand("attribute").setExecutor(new AttributeCommand(playerManager, attributeService));

        getCommand("attributes").setExecutor(new AttributeMenuCommand(playerManager));

        getCommand("skill").setExecutor(new SkillCommand(this, playerManager, skillService));

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