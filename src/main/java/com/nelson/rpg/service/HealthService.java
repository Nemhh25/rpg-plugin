package com.nelson.rpg.service;

import com.nelson.rpg.model.RPGCharacter;

public class HealthService {

    public double getMaxHealth(RPGCharacter character) {
        return character.getMaxHealth();
    }

    public void restoreHealth(RPGCharacter character, double amount) {
        character.restoreHealth(amount);
    }

    public void damage(RPGCharacter character, double amount) {
        character.takeDamage(amount);
    }
}