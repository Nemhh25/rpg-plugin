package com.nelson.rpg;

import com.nelson.rpg.command.*;
import com.nelson.rpg.listener.*;
import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.service.*;
import com.nelson.rpg.task.HealthRegenerationTask;
import com.nelson.rpg.task.ManaRegenerationTask;
import org.bukkit.plugin.java.JavaPlugin;
import com.nelson.rpg.service.PlayerDataService;
import com.nelson.rpg.command.ResetCharacterCommand;
import com.nelson.rpg.listener.EquipmentListener;
import com.nelson.rpg.command.EquipmentMenuCommand;
import com.nelson.rpg.factory.EquipmentItemFactory;

public class RpgPlugin extends JavaPlugin {


    private PlayerManager playerManager;
    EquipmentMenuCommand equipmentMenuCommand = new EquipmentMenuCommand(playerManager);

    @Override
    public void onEnable() {

        playerManager = new PlayerManager();
        PlayerDataService playerDataService = new PlayerDataService(this);

        ProgressionService progressionService = new ProgressionService();

        AttributeService attributeService = new AttributeService();

        AttributeMenuCommand attributeMenuCommand = new AttributeMenuCommand(playerManager);

        CombatService combatService = new CombatService();

        ManaService manaService = new ManaService();

        SkillService skillService = new SkillService(manaService);

        ManaRegenerationTask manaRegenerationTask = new ManaRegenerationTask(playerManager, manaService);

        HealthService healthService = new HealthService();

        HealthRegenerationTask healthRegenerationTask = new HealthRegenerationTask(playerManager, healthService);

        ResetCharacterCommand resetCharacterCommand = new ResetCharacterCommand(playerManager, playerDataService);

        EquipmentMenuCommand equipmentMenuCommand = new EquipmentMenuCommand(playerManager);

        EquipmentItemFactory equipmentItemFactory = new EquipmentItemFactory(this);

        getServer().getPluginManager().registerEvents(new CombatListener(playerManager, combatService), this);

        getServer().getPluginManager().registerEvents(new AttributeMenuListener(playerManager, attributeService, attributeMenuCommand), this);

        getServer().getPluginManager().registerEvents(new SkillListener(this, playerManager, skillService), this);

        getServer().getPluginManager().registerEvents(new PlayerDataListener(playerManager, playerDataService), this);

        getServer().getPluginManager().registerEvents(new EquipmentListener(this, playerManager), this);

        getServer().getPluginManager().registerEvents(new EquipmentMenuListener(playerManager, equipmentMenuCommand, equipmentItemFactory), this);

        getServer().getPluginManager().registerEvents(new EquipmentMenuListener(playerManager, equipmentMenuCommand, equipmentItemFactory), this);

        getServer().getPluginManager().registerEvents(new CombatListener(playerManager, combatService), this);

        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this, playerManager), this);

        getServer().getPluginManager().registerEvents(new ExperienceListener(playerManager, progressionService), this);

        getServer().getScheduler().runTaskTimer(this, manaRegenerationTask, 40L, 40L);

        getServer().getScheduler().runTaskTimer(this, healthRegenerationTask, 40L, 40L);


        getCommand("rpg").setExecutor(new RpgCommand(playerManager));

        getCommand("addxp").setExecutor(new AddXpCommand(playerManager, progressionService));

        getCommand("attribute").setExecutor(new AttributeCommand(playerManager, attributeService));

        getCommand("attributes").setExecutor(new AttributeMenuCommand(playerManager));

        getCommand("skill").setExecutor(new SkillCommand(this, playerManager, skillService));

        getCommand("resetcharacter").setExecutor(resetCharacterCommand);

        getCommand("giveequipment").setExecutor(new GiveEquipmentCommand(this, equipmentItemFactory));

        getCommand("equipment").setExecutor(equipmentMenuCommand);


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