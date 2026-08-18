package com.nelson.rpg.service;

import com.nelson.rpg.model.RPGCharacter;

public class CombatService {

    public double calculateDamage(RPGCharacter character, double baseDamage) {
        int strength = character.getAttributes().getStrength();

        int bonusPoints = strength - 5;

        double multiplier = 1 + (bonusPoints * 0.10);

        return baseDamage * multiplier;

    }

    public double calculateDamageTaken(RPGCharacter character, double incomingDamage){
        int defense = character.getAttributes().getDefense();

        int bonusPoints = defense - 5;

        double multiplier = 1 - (bonusPoints * 0.10);
        return Math.max(0, incomingDamage * multiplier);
    }
}
