package com.nelson.rpg.model;

public class Attributes {

    private int strength;
    private int defense;
    private int intelligence;

    public Attributes() {
        this.strength = 5;
        this.defense = 5;
        this.intelligence = 5;
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
}
