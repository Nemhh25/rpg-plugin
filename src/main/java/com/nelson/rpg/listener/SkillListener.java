package com.nelson.rpg.listener;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.model.SkillType;
import com.nelson.rpg.service.SkillService;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityExplodeEvent;

public class SkillListener implements Listener {

    private final JavaPlugin plugin;
    private final PlayerManager playerManager;
    private final SkillService skillService;
    private final NamespacedKey fireballKey;

    public SkillListener(JavaPlugin plugin, PlayerManager playerManager, SkillService skillService) {
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.skillService = skillService;

        this.fireballKey = new NamespacedKey(plugin, "rpg_fireball");
    }

    @EventHandler
    public void onFireballExplode(EntityExplodeEvent event) {

        if (!(event.getEntity() instanceof Fireball fireball)) {
            return;
        }

        boolean isRpgFireball = fireball.getPersistentDataContainer().has(fireballKey, PersistentDataType.BYTE);

        if (!isRpgFireball) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        if (!(projectile instanceof Fireball fireball)) {
            return;
        }

        boolean isRpgFireball = fireball.getPersistentDataContainer().has(fireballKey, PersistentDataType.BYTE);

        if (!isRpgFireball) {
            return;
        }

        if (!(fireball.getShooter() instanceof Player player)) {
            return;
        }

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());

        if (character == null) {
            return;
        }

        double damage = skillService.calculateSkillDamage(character, SkillType.FIREBALL);

        double radius = 3.0;

        Location explosionLocation = fireball.getLocation();
        for (Entity entity : fireball.getNearbyEntities(radius, radius, radius)) {

            if (!(entity instanceof Damageable damageable)) {
                continue;
            }

            if (entity.equals(player)) {
                continue;
            }

            double distance = entity.getLocation().distance(explosionLocation);

            double damageMultiplier;

            if (distance <= 2.0) {

                damageMultiplier = 1.0;

            } else if (distance <= 3.0) {

                damageMultiplier = 0.75;

            } else {

                damageMultiplier = 0.5;
            }

            double finalDamage = damage * damageMultiplier;

            damageable.damage(finalDamage);

            player.sendMessage("§7Dano causado: §c" + finalDamage + " §7em §f" + entity.getName());
        }
    }
}