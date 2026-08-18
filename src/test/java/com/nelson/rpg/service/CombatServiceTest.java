package com.nelson.rpg.service;

import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.RPGCharacter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombatServiceTest {

    @Test
    void shouldCalculateBaseDamageWithDefaultStrength() {

        RPGCharacter character = new RPGCharacter("Teste");

        CombatService service = new CombatService();

        double damage = service.calculateDamage(character, 10);

        assertEquals(10.0, damage);

    }


    @Test
    void shouldIncreaseDamageWithHigherStrength() {

        RPGCharacter character = new RPGCharacter("Teste");

        character.addAttributePoints(2);

        AttributeService attributeService = new AttributeService();

        attributeService.increaseAttribute(character, AttributeType.STRENGTH);

        attributeService.increaseAttribute(character, AttributeType.STRENGTH);


        CombatService service = new CombatService();

        double damage = service.calculateDamage(character, 10);

        assertEquals(12.0, damage);

    }

    @Test
    void shouldReduceDamageWithHigherDefense() {

        RPGCharacter character = new RPGCharacter("Teste");

        character.addAttributePoints(2);

        AttributeService attributeService = new AttributeService();

        attributeService.increaseAttribute(character, AttributeType.DEFENSE);

        attributeService.increaseAttribute(character, AttributeType.DEFENSE);


        CombatService service = new CombatService();

        double damageTaken = service.calculateDamageTaken(character, 10);

        assertEquals(8.0, damageTaken);

    }

    @Test
    void shouldTakeFullDamageWithDefaultDefense() {

        RPGCharacter character = new RPGCharacter("Teste");

        CombatService service = new CombatService();

        double damageTaken =
                service.calculateDamageTaken(character, 10);

        assertEquals(10.0, damageTaken);

    }

}