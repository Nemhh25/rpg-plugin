package com.nelson.rpg.command;

import com.nelson.rpg.model.ProgressionResult;
import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.ProgressionService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class AddXpCommand implements CommandExecutor {

    private final PlayerManager playerManager;
    private final ProgressionService progressionService;


    public AddXpCommand(PlayerManager playerManager, ProgressionService progressionService) {

        this.playerManager = playerManager;
        this.progressionService = progressionService;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 2) {

            sender.sendMessage("Use: /addxp <jogador> <quantidade>");

            return true;

        }


        Player target = Bukkit.getPlayer(args[0]);


        if (target == null) {

            sender.sendMessage("Jogador não encontrado ou offline.");

            return true;

        }


        int amount;

        try {

            amount = Integer.parseInt(args[1]);

        } catch (NumberFormatException e) {

            sender.sendMessage("Quantidade de XP inválida.");

            return true;

        }


        RPGCharacter character = playerManager.getCharacter(target.getUniqueId());


        if (character == null) {

            sender.sendMessage("Esse jogador não possui personagem.");

            return true;

        }


        ProgressionResult result = progressionService.addExperience(character, amount);


        sender.sendMessage("§aVocê adicionou §e" + amount + " XP para §f" + target.getName());


        if (result.getLevelsGained() > 0) {

            target.sendMessage("§6Parabéns! Você subiu " + result.getLevelsGained() + " nível(is)!");

        }


        return true;
    }
}