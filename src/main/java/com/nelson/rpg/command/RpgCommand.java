package com.nelson.rpg.command;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RpgCommand implements CommandExecutor {

    private final PlayerManager playerManager;


    public RpgCommand(PlayerManager playerManager) {

        this.playerManager = playerManager;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (!(sender instanceof Player player)) {

            sender.sendMessage("Apenas jogadores podem usar esse comando.");

            return true;

        }


        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());


        if (character == null) {

            player.sendMessage("§cVocê ainda não possui um personagem.");

            return true;

        }


        player.sendMessage("§6===== RPGCore =====");
        player.sendMessage("§eNome: §f" + character.getName());
        player.sendMessage("§eNível: §f" + character.getLevel());
        player.sendMessage("§eExperiência: §f" + character.getExperience());
        player.sendMessage("§ePontos de atributo: §f" + character.getAttributePoints());

        player.sendMessage("§6--- Atributos ---");
        player.sendMessage("§eForça: §f" + character.getAttributes().getStrength());
        player.sendMessage("§eDefesa: §f" + character.getAttributes().getDefense());
        player.sendMessage("§eInteligência: §f" + character.getAttributes().getIntelligence());


        return true;
    }
}