package com.nelson.rpg.task;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.HealthService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HealthRegenerationTask implements Runnable {

    private final PlayerManager playerManager;
    private final HealthService healthService;

    public HealthRegenerationTask(
            PlayerManager playerManager,
            HealthService healthService
    ) {
        this.playerManager = playerManager;
        this.healthService = healthService;
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            RPGCharacter character =
                    playerManager.getCharacter(
                            player.getUniqueId()
                    );

            if (character == null) {
                continue;
            }

            if (!character.isAlive()) {
                continue;
            }

            if (character.getHealth() >= character.getMaxHealth()) {
                continue;
            }

            healthService.restoreHealth(character, 2.5);

            double health = character.getHealth();
            double maxHealth = character.getMaxHealth();

            double minecraftHealth =
                    (health / maxHealth) * 20.0;

            player.setHealth(minecraftHealth);
        }
    }
}