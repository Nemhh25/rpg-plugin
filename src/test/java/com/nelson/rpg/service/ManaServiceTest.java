package com.nelson.rpg.service;

import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.RPGCharacter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManaServiceTest {

    @Test
    void shouldReturnBaseManaWithDefaultIntelligence() {

        RPGCharacter character = new RPGCharacter("Teste");

        ManaService service = new ManaService();

        double maxMana = service.getMaxMana(character);

        assertEquals(100.0, maxMana);

    }

    @Test
    void shouldIncreaseMaxManaWithHigherIntelligence() {

        RPGCharacter character = new RPGCharacter("Teste");

        character.addAttributePoints(3);

        AttributeService attributeService = new AttributeService();

        attributeService.increaseAttribute(character, AttributeType.INTELLIGENCE);

        attributeService.increaseAttribute(character, AttributeType.INTELLIGENCE);

        attributeService.increaseAttribute(character, AttributeType.INTELLIGENCE);

        ManaService service = new ManaService();

        double maxMana = service.getMaxMana(character);

        assertEquals(130.0, maxMana);

    }

}