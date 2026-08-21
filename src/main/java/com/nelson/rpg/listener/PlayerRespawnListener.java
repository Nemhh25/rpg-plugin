package com.nelson.rpg.listener;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerRespawnListener implements Listener {

    private final PlayerManager playerManager;
    private final JavaPlugin plugin;

    public PlayerRespawnListener(
            JavaPlugin plugin,
            PlayerManager playerManager
    ) {
        this.plugin = plugin;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();

        RPGCharacter character =
                playerManager.getCharacter(player.getUniqueId());

        if (character == null) {
            return;
        }

        // Restaura a vida do personagem RPG
        character.restoreFullHealth();

        // Espera o Minecraft terminar o respawn
        plugin.getServer().getScheduler().runTask(
                plugin,
                () -> {

                    player.setHealth(20.0);

                }
        );
    }
}