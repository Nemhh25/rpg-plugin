package com.nelson.rpg.service;

import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.model.SkillType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillService {

    private final Map<UUID, Map<SkillType, Long>> cooldowns = new HashMap<>();

    private final ManaService manaService;

    public SkillService(ManaService manaService) {
        this.manaService = manaService;
    }

    public boolean useSkill(RPGCharacter character, SkillType skillType, UUID playerId) {

        if (isOnCooldown(playerId, skillType)) {
            return false;
        }

        boolean consumed = manaService.consumeMana(character, skillType.getManaCost());

        if (!consumed) {
            return false;
        }

        setCooldown(playerId, skillType, skillType.getCooldown());

        return true;
    }

    public double calculateSkillDamage(RPGCharacter character, SkillType skillType) {

        int attributeValue = character.getAttributes().getValue(skillType.getScalingAttribute());

        return skillType.getBaseDamage() + (attributeValue - 5);
    }

    public boolean isOnCooldown(UUID playerId, SkillType skillType) {

        Map<SkillType, Long> playerCooldowns = cooldowns.get(playerId);

        if (playerCooldowns == null) {
            return false;
        }

        Long cooldownEnd = playerCooldowns.get(skillType);

        if (cooldownEnd == null) {
            return false;
        }

        if (System.currentTimeMillis() >= cooldownEnd) {

            playerCooldowns.remove(skillType);

            if (playerCooldowns.isEmpty()) {
                cooldowns.remove(playerId);
            }

            return false;
        }

        return true;
    }

    private void setCooldown(UUID playerId, SkillType skillType, long duration) {

        Map<SkillType, Long> playerCooldowns = cooldowns.computeIfAbsent(playerId, id -> new HashMap<>());

        playerCooldowns.put(skillType, System.currentTimeMillis() + duration);
    }

    public long getRemainingCooldown(UUID playerId, SkillType skillType) {

        Map<SkillType, Long> playerCooldowns = cooldowns.get(playerId);

        if (playerCooldowns == null) {
            return 0;
        }

        Long cooldownEnd = playerCooldowns.get(skillType);

        if (cooldownEnd == null) {
            return 0;
        }

        long remaining = cooldownEnd - System.currentTimeMillis();

        if (remaining <= 0) {

            playerCooldowns.remove(skillType);

            if (playerCooldowns.isEmpty()) {
                cooldowns.remove(playerId);
            }

            return 0;
        }

        return remaining;
    }
}