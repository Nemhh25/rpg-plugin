package com.nelson.rpg.service;

import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.model.SkillType;

import java.util.UUID;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SkillServiceTest {

    UUID playerId = UUID.randomUUID();

    @Test
    void shouldUseFireballWhenCharacterHasEnoughMana() {

        RPGCharacter character = new RPGCharacter("Teste");

        ManaService manaService = new ManaService();

        SkillService skillService = new SkillService(manaService);

        boolean result = skillService.useSkill(character, SkillType.FIREBALL, playerId);

        assertTrue(result);
        assertEquals(70, character.getMana());

    }

    @Test
    void shouldNotUseFireballWithoutEnoughMana() {

        RPGCharacter character = new RPGCharacter("Teste");

        character.consumeMana(80);

        ManaService manaService = new ManaService();

        SkillService skillService = new SkillService(manaService);

        boolean result = skillService.useSkill(character, SkillType.FIREBALL,  playerId);

        assertFalse(result);
        assertEquals(20, character.getMana());

    }

    @Test
    void shouldCalculateFireballDamageBasedOnIntelligence() {

        RPGCharacter character = new RPGCharacter("Teste");

        ManaService manaService = new ManaService();

        SkillService skillService = new SkillService(manaService);

        double damage = skillService.calculateSkillDamage(character, SkillType.FIREBALL);

        assertEquals(10, damage);
    }

    @Test
    void shouldIncreaseFireballDamageWithIntelligence() {

        RPGCharacter character = new RPGCharacter("Teste");

        character.addAttributePoints(3);

        AttributeService attributeService = new AttributeService();

        attributeService.increaseAttribute(character, AttributeType.INTELLIGENCE, 3);

        ManaService manaService = new ManaService();

        SkillService skillService = new SkillService(manaService);

        double damage = skillService.calculateSkillDamage(character, SkillType.FIREBALL);

        assertEquals(13, damage);
    }

}