package com.nelson.rpg.service;

import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.RPGCharacter;

public class AttributeService {

    public boolean increaseAttribute(RPGCharacter character, AttributeType attributeType) {
        if (character.getAttributePoints() <= 0) {
            return false;
        }

        character.getAttributes().increase(attributeType);
        character.removeAttributePoints(1);

        return true;
    }

    public boolean increaseAttribute(RPGCharacter character, AttributeType attributeType, int amount) {

        if (amount <= 0) {
            return false;
        }

        if (character.getAttributePoints() < amount) {
            return false;
        }

        for (int i = 0; i < amount; i++) {

            increaseAttribute(character, attributeType);

        }

        return true;
    }
}
