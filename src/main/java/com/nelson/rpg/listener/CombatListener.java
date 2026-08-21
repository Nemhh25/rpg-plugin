package com.nelson.rpg.listener;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.service.CombatService;
import org.bukkit.ChatColor;
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

        // ==========================================
        // JOGADOR ATACANDO
        // ==========================================

        if (event.getDamager() instanceof Player attacker) {

            RPGCharacter attackerCharacter = playerManager.getCharacter(attacker.getUniqueId());

            if (attackerCharacter != null) {

                double damage = combatService.calculateDamage(attackerCharacter, event.getDamage());

                event.setDamage(damage);

                attacker.sendMessage(ChatColor.GRAY + "Dano causado: " + ChatColor.RED + String.format("%.1f", damage));
            }
        }

        // ==========================================
        // JOGADOR RECEBENDO DANO
        // ==========================================

        if (event.getEntity() instanceof Player victim) {

            RPGCharacter victimCharacter = playerManager.getCharacter(victim.getUniqueId());

            if (victimCharacter != null) {

                double damageTaken = combatService.calculateDamageTaken(victimCharacter, event.getDamage());

                // Impede o Minecraft de controlar a vida RPG
                event.setCancelled(true);

                // Aplica o dano na vida do personagem
                victimCharacter.takeDamage(damageTaken);

                updateHealthBar(victim, victimCharacter);

                if (!victimCharacter.isAlive()) {

                    victim.sendMessage(ChatColor.RED + "☠ Você morreu!");

                    victim.setHealth(0);
                }


                victim.sendMessage(ChatColor.GRAY + "Dano recebido: " + ChatColor.RED + String.format("%.1f", damageTaken));

                victim.sendMessage(ChatColor.GRAY + "Vida: " + ChatColor.GREEN + String.format("%.1f / %.1f", victimCharacter.getHealth(), victimCharacter.getMaxHealth()));
            }
        }
    }

    private void updateHealthBar(Player player, RPGCharacter character) {

        double maxHealth = character.getMaxHealth();
        double health = character.getHealth();

        double minecraftHealth = (health / maxHealth) * 20.0;

        player.setHealthScale(20.0);
        player.setHealth(Math.max(0.0, minecraftHealth));
    }

}