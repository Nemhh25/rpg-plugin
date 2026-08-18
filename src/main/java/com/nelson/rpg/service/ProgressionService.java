package com.nelson.rpg.service;

import com.nelson.rpg.model.RPGCharacter;

public class ProgressionService {


    public void addExperience(RPGCharacter character, int amount) {

        character.addExperience(amount);


        while (character.getExperience() >= 100) {

            character.levelUp();

            character.removeExperience(100);

        }

    }

}