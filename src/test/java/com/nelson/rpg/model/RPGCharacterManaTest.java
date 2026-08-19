package com.nelson.rpg.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RPGCharacterManaTest {

    @Test
    void shouldConsumeMana() {

        RPGCharacter character =
                new RPGCharacter("Teste");

        boolean result =
                character.consumeMana(30);

        assertTrue(result);
        assertEquals(70, character.getMana());

    }

    @Test
    void shouldNotConsumeMoreManaThanAvailable() {

        RPGCharacter character =
                new RPGCharacter("Teste");

        boolean result =
                character.consumeMana(150);

        assertFalse(result);
        assertEquals(100, character.getMana());

    }

    @Test
    void shouldRestoreManaWithoutExceedingMaximum() {

        RPGCharacter character =
                new RPGCharacter("Teste");

        character.consumeMana(30);

        character.restoreMana(20);

        assertEquals(90, character.getMana());

    }

    @Test
    void shouldNotRestoreManaAboveMaximum() {

        RPGCharacter character =
                new RPGCharacter("Teste");

        character.restoreMana(50);

        assertEquals(100, character.getMana());

    }

}