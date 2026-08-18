package com.nelson.rpg.listener;

import com.nelson.rpg.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerManager playerManager;


    public PlayerJoinListener(PlayerManager playerManager) {

        this.playerManager = playerManager;

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        var player = event.getPlayer();


        if (!playerManager.hasCharacter(player.getUniqueId())) {

            playerManager.createCharacter(
                    player.getUniqueId(),
                    player.getName()
            );

            player.sendMessage("§aSeu personagem foi criado!");

        }

    }
}