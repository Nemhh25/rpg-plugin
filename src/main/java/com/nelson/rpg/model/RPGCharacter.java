package com.nelson.rpg.model;

public class RPGCharacter {

    private final String name;
    private int level;
    private int experience;
    private final Attributes attributes;
    private int attributePoints;

    public RPGCharacter(String name) {

        this.name = name;
        this.level = 1;
        this.experience = 0;
        this.attributes = new Attributes();
        this.attributePoints = 0;

    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public int getAttributePoints() {

        return attributePoints;

    }

    public void addExperience(int amount) {

        this.experience += amount;

    }

    public void addAttributePoints(int amount) {

        this.attributePoints += amount;

    }

    public void removeAttributePoints(int amount) {

        this.attributePoints = Math.max(0, this.attributePoints - amount);

    }

    public void levelUp() {
        this.level++;
    }

    public void removeExperience(int amount) {

        this.experience = Math.max(0, this.experience - amount);

    }


}