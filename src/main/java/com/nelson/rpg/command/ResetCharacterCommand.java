package com.nelson.rpg.command;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.service.PlayerDataService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ResetCharacterCommand implements CommandExecutor {

    private final PlayerManager playerManager;
    private final PlayerDataService playerDataService;

    public ResetCharacterCommand(PlayerManager playerManager, PlayerDataService playerDataService) {
        this.playerManager = playerManager;
        this.playerDataService = playerDataService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {

            sender.sendMessage("Apenas jogadores podem usar esse comando.");

            return true;
        }

        UUID uuid = player.getUniqueId();

        if (args.length != 1 || !args[0].equalsIgnoreCase("confirm")) {

            player.sendMessage("§cAtenção! Isso apagará todo o progresso do seu personagem.");
            player.sendMessage("§eDigite §f/resetcharacter confirm §epara confirmar.");

            return true;
        }

        playerDataService.deleteCharacter(uuid);

        playerManager.removeCharacter(uuid);

        playerManager.createCharacter(uuid, player.getName());

        player.sendMessage("§aSeu personagem foi resetado com sucesso!");

        return true;


    }
}