package com.nelson.rpg.command;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.AttributeService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public class AttributeCommand implements CommandExecutor {

    private final PlayerManager playerManager;
    private final AttributeService attributeService;

    public AttributeCommand(PlayerManager playerManager, AttributeService attributeService) {
        this.playerManager = playerManager;
        this.attributeService = attributeService;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Use: /attribute <strength | defense | intelligence>");
            return true;
        }

        AttributeType attributeType;

        try {
            attributeType = AttributeType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Atributo inválido");
            return true;
        }

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());
        if (character == null) {
            player.sendMessage(ChatColor.RED + "Você não possui um personagem. ");
            return true;
        }

        boolean success = attributeService.increaseAttribute(character, attributeType);

        if (!success) {
            player.sendMessage(ChatColor.RED + "Você não possui pontos de atributo.");
            return true;
        }

        player.sendMessage("§aAtributo aumentado com sucesso!");
        return true;

    }
}
