package com.nelson.rpg.listener;

import com.nelson.rpg.command.AttributeMenuCommand;
import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.AttributeService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;


public class AttributeMenuListener implements Listener {

    private final PlayerManager playerManager;
    private final AttributeService attributeService;
    private final AttributeMenuCommand attributeMenuCommand;

    public AttributeMenuListener(PlayerManager playerManager, AttributeService attributeService, AttributeMenuCommand attributeMenuCommand) {
        this.playerManager = playerManager;
        this.attributeService = attributeService;
        this.attributeMenuCommand = attributeMenuCommand;
    }

    private boolean isAttributeMenu(String title) {
        return title.equals(ChatColor.DARK_PURPLE + "✦ " + ChatColor.LIGHT_PURPLE + "Seus Atributos" + ChatColor.DARK_PURPLE + " ✦");
    }


    private void increaseAttribute(Player player, AttributeType attributeType) {

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());

        if (character == null) {
            return;
        }

        boolean success = attributeService.increaseAttribute(character, attributeType, 1);

        if (!success) {

            player.sendMessage(ChatColor.RED + "Você não possui pontos de atributo.");

            return;
        }

        player.sendMessage(ChatColor.GREEN + "Atributo aumentado!");
        attributeMenuCommand.openMenu(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!isAttributeMenu(event.getView().getTitle())) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (event.getRawSlot() == 11) {

            increaseAttribute(player, AttributeType.STRENGTH);

        } else if (event.getRawSlot() == 14) {

            increaseAttribute(player, AttributeType.DEFENSE);

        } else if (event.getRawSlot() == 17) {

            increaseAttribute(player, AttributeType.INTELLIGENCE);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {

        if (!isAttributeMenu(event.getView().getTitle())) {
            return;
        }

        event.setCancelled(true);
    }
}