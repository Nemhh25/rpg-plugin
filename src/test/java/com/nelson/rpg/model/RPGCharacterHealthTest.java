package com.nelson.rpg.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RPGCharacterHealthTest {

    @Test
    void personagemDeveComecarComVidaMaxima() {

        RPGCharacter character = new RPGCharacter("Nelson");

        assertEquals(100, character.getHealth());
        assertEquals(100, character.getMaxHealth());
    }

    @Test
    void personagemDeveReceberDano() {

        RPGCharacter character = new RPGCharacter("Nelson");

        character.takeDamage(30);

        assertEquals(70, character.getHealth());
    }

    @Test
    void vidaNaoPodeFicarAbaixoDeZero() {

        RPGCharacter character = new RPGCharacter("Nelson");

        character.takeDamage(150);

        assertEquals(0, character.getHealth());
        assertFalse(character.isAlive());
    }

    @Test
    void personagemDeveRecuperarVida() {

        RPGCharacter character = new RPGCharacter("Nelson");

        character.takeDamage(40);
        character.restoreHealth(20);

        assertEquals(80, character.getHealth());
    }

    @Test
    void vidaNaoPodeUltrapassarVidaMaxima() {

        RPGCharacter character = new RPGCharacter("Nelson");

        character.takeDamage(40);
        character.restoreHealth(100);

        assertEquals(100, character.getHealth());
    }

    @Test
    void personagemDevePoderSerCuradoCompletamente() {

        RPGCharacter character = new RPGCharacter("Nelson");

        character.takeDamage(70);
        character.restoreFullHealth();

        assertEquals(100, character.getHealth());
        assertTrue(character.isAlive());
    }
}