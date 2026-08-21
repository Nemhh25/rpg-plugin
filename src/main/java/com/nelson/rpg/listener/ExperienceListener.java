package com.nelson.rpg.listener;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.ProgressionResult;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.ProgressionService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ExperienceListener implements Listener {

    private final PlayerManager playerManager;
    private final ProgressionService progressionService;

    public ExperienceListener(PlayerManager playerManager, ProgressionService progressionService) {
        this.playerManager = playerManager;
        this.progressionService = progressionService;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (!(event.getEntity().getKiller() instanceof Player player)) {
            return;
        }

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());

        if (character == null) {
            return;
        }

        int experience = getExperienceReward(event.getEntity().getType().name());

        if (experience <= 0) {
            return;
        }

        ProgressionResult result = progressionService.addExperience(character, experience);

        player.sendMessage(ChatColor.GREEN + "+" + experience + " XP");

        if (result.getLevelsGained() > 0) {

            player.sendMessage(ChatColor.GOLD + "Parabéns! Você subiu " + result.getLevelsGained() + " nível(is)!");
        }
    }

    private int getExperienceReward(String entityType) {

        return switch (entityType) {

            case "ZOMBIE" -> 10;

            case "SKELETON" -> 12;

            case "CREEPER" -> 15;

            case "SPIDER" -> 10;

            case "ENDERMAN" -> 25;

            default -> 0;
        };
    }
}