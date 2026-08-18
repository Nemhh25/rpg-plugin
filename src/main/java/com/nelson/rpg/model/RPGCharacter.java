package com.nelson.rpg.model;

public class RPGCharacter {

    private final String name;
    private int level;
    private int experience;

    public RPGCharacter(String name) {

        this.name = name;
        this.level = 1;
        this.experience = 0;

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

    public void addExperience(int amount) {

        this.experience += amount;

    }

    public void levelUp() {
        this.level++;
    }

    public void removeExperience(int amount) {

        this.experience = Math.max(0, this.experience - amount);

    }
}