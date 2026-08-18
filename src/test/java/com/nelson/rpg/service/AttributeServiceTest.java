package com.nelson.rpg.service;

import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.RPGCharacter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttributeServiceTest {

    @Test
    void shouldIncreaseAttributeWhenCharacterHasPoints() {

        RPGCharacter character = new RPGCharacter("Teste");
        character.addAttributePoints(3);

        AttributeService service = new AttributeService();

        boolean result = service.increaseAttribute(character, AttributeType.STRENGTH);

        assertTrue(result);
        assertEquals(6, character.getAttributes().getStrength());
        assertEquals(2, character.getAttributePoints());

    }

    @Test
    void shouldNotIncreaseAttributeWithoutPoints() {

        RPGCharacter character = new RPGCharacter("Teste");

        AttributeService service = new AttributeService();

        boolean result = service.increaseAttribute(character, AttributeType.STRENGTH);

        assertFalse(result);
        assertEquals(5, character.getAttributes().getStrength());
        assertEquals(0, character.getAttributePoints());

    }

}