package com.nelson.rpg.model;

import javax.management.Attribute;
import com.nelson.rpg.model.Equipment;
import com.nelson.rpg.model.EquipmentType;

import java.util.Map;

public class Attributes {

    private int strength;
    private int defense;
    private int intelligence;

    public Attributes() {
        this.strength = 5;
        this.defense = 5;
        this.intelligence = 5;
    }

    public Attributes(int strength, int defense, int intelligence) {
        this.strength = strength;
        this.defense = defense;
        this.intelligence = intelligence;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefense() {
        return defense;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public Attributes getAttributes() {
        return this;
    }

    public void increase(AttributeType attributeType) {

        switch (attributeType) {

            case STRENGTH -> strength++;

            case DEFENSE -> defense++;

            case INTELLIGENCE -> intelligence++;

        }
    }

    public int getValue(AttributeType attributeType) {

        return switch (attributeType) {

            case STRENGTH -> strength;

            case DEFENSE -> defense;

            case INTELLIGENCE -> intelligence;

        };
    }

    public int getFinalValue(AttributeType attributeType, Map<EquipmentType, Equipment> equippedItems) {

        int baseValue = getValue(attributeType);
        int bonus = 0;

        for(Equipment equipment : equippedItems.values()) {

            if (equipment.getAttributeType() == attributeType) {
                bonus += equipment.getAttributeBonus();
            }
        }

        return baseValue + bonus;
    }
}