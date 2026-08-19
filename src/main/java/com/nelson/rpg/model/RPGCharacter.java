package com.nelson.rpg.model;

public class RPGCharacter {

    private final String name;
    private int level;
    private int experience;
    private final Attributes attributes;
    private int attributePoints;
    private double mana;

    public RPGCharacter(String name) {

        this.name = name;
        this.level = 1;
        this.experience = 0;
        this.attributes = new Attributes();
        this.attributePoints = 0;
        this.mana = 100;

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

    public double getMana() {
        return mana;
    }

    public double getMaxMana() {
        return 100 + ((attributes.getIntelligence() - 5) * 10);
    }

    public boolean consumeMana(double amount) {

        if (mana < amount) {

            return false;

        }

        mana -= amount;

        return true;

    }

    public void restoreMana(double amount) {

        this.mana = Math.min(getMaxMana(), this.mana + amount);

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