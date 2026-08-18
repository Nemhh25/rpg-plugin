package com.nelson.rpg.listener;


import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.CombatService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {

    private final PlayerManager playerManager;
    private final CombatService combatService;

    public CombatListener(PlayerManager playerManager, CombatService combatService) {
        this.playerManager = playerManager;
        this.combatService = combatService;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player attacker) {

            RPGCharacter character = playerManager.getCharacter(attacker.getUniqueId());

            if (character != null) {

                double damage = combatService.calculateDamage(character, event.getDamage());

                attacker.sendMessage("§7Dano causado: §c" + damage);

                event.setDamage(damage);

            }

        }


        if (event.getEntity() instanceof Player victim) {

            RPGCharacter character = playerManager.getCharacter(victim.getUniqueId());

            if (character != null) {

                double damage = combatService.calculateDamageTaken(character, event.getDamage());

                victim.sendMessage("§7Dano recebido: §c" + damage);

                event.setDamage(damage);

            }

        }

    }


}
