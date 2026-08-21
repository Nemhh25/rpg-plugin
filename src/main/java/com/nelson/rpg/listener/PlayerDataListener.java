package com.nelson.rpg.listener;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.PlayerDataService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerDataListener implements Listener {

    private final PlayerManager playerManager;
    private final PlayerDataService playerDataService;

    public PlayerDataListener(PlayerManager playerManager, PlayerDataService playerDataService) {
        this.playerManager = playerManager;
        this.playerDataService = playerDataService;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();

        RPGCharacter character = playerDataService.loadCharacter(uuid);

        if (character != null) {

            playerManager.loadCharacter(uuid, character);

            if (!character.isAlive()) {
                character.restoreFullHealth();
            }

        } else {

            playerManager.createCharacter(uuid, player.getName());

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a Seu personagem foi criado!"));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        UUID uuid = event.getPlayer().getUniqueId();

        RPGCharacter character = playerManager.getCharacter(uuid);

        if (character == null) {
            return;
        }

        playerDataService.saveCharacter(uuid, character);
        playerManager.removeCharacter(uuid);
    }
}