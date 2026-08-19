package com.nelson.rpg.service;

import com.nelson.rpg.model.RPGCharacter;

public class ManaService {

    public double getMaxMana(RPGCharacter character) {
        int intelligence = character.getAttributes().getIntelligence();

        int bonusPoints = intelligence - 5;

        return 100 + (bonusPoints * 10);
    }

    public boolean consumeMana(RPGCharacter character, double amount) {
        return character.consumeMana(amount);
    }

    public void restoreMana(RPGCharacter character, double amount) {
        character.restoreMana(amount);
    }
}
