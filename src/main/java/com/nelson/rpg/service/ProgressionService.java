package com.nelson.rpg.service;

import com.nelson.rpg.model.RPGCharacter;
import com.nelson.rpg.model.ProgressionResult;

public class ProgressionService {

    public ProgressionResult addExperience(RPGCharacter character, int amount) {
        character.addExperience(amount);
        int levelsGained = 0;

        while (character.getExperience() >= 100) {
            character.levelUp();
            character.addAttributePoints(3);

            character.removeExperience(100);
            levelsGained++;
        }

        return new ProgressionResult(amount, levelsGained);
    }

}