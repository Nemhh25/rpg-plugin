package com.nelson.rpg.task;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.ManaService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ManaRegenerationTask implements Runnable {

    private final PlayerManager playerManager;
    private final ManaService manaService;

    public ManaRegenerationTask(
            PlayerManager playerManager,
            ManaService manaService
    ) {
        this.playerManager = playerManager;
        this.manaService = manaService;
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

            manaService.restoreMana(character, 5);

        }

    }
}