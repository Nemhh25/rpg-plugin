package com.nelson.rpg.command;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.model.SkillType;
import com.nelson.rpg.service.SkillService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class SkillCommand implements CommandExecutor {

    private final PlayerManager playerManager;
    private final SkillService skillService;
    private final JavaPlugin plugin;

    public SkillCommand(JavaPlugin plugin, PlayerManager playerManager, SkillService skillService

    ) {

        this.plugin = plugin;
        this.playerManager = playerManager;
        this.skillService = skillService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {

            sender.sendMessage("Apenas jogadores podem usar esse comando.");

            return true;
        }

        if (args.length != 1) {

            player.sendMessage("Use: /skill <fireball>");

            return true;
        }

        SkillType skillType;

        try {

            skillType = SkillType.valueOf(args[0].toUpperCase());

        } catch (IllegalArgumentException e) {

            player.sendMessage("Habilidade inválida.");

            return true;
        }

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());

        if (character == null) {

            player.sendMessage("Você não possui personagem.");

            return true;
        }

        if (skillService.isOnCooldown(player.getUniqueId(), skillType)) {

            long remaining = skillService.getRemainingCooldown(player.getUniqueId(), skillType);

            long seconds = (remaining + 999) / 1000;

            player.sendMessage("§c" + skillType.getDisplayName() + " em recarga! Aguarde " + seconds + " segundos.");
            return true;
        }
        boolean success = skillService.useSkill(character, skillType, player.getUniqueId());

        if (!success) {

            player.sendMessage("§cVocê não possui Mana suficiente.");

            return true;
        }

        Fireball fireball = player.launchProjectile(Fireball.class);
        NamespacedKey key = new NamespacedKey(plugin, "rpg_fireball");
        fireball.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);

        player.sendMessage("§a" + skillType.getDisplayName() + "§autilizada!");

        return true;
    }
}