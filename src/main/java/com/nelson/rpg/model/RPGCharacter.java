package com.nelson.rpg.model;


import com.nelson.rpg.model.Equipment;
import com.nelson.rpg.model.EquipmentType;

import java.util.HashMap;
import java.util.Map;

public class RPGCharacter {

    private final String name;
    private int level;
    private int experience;
    private final Attributes attributes;
    private int attributePoints;
    private double mana;
    private double health;
    private final Map<EquipmentType, Equipment> equippedItems = new HashMap<>();

    public RPGCharacter(String name) {

        this.name = name;
        this.level = 1;
        this.experience = 0;
        this.attributes = new Attributes();
        this.attributePoints = 0;
        this.mana = 100;
        this.health = 100;

    }
    public RPGCharacter(
            String name,
            int level,
            int experience,
            Attributes attributes,
            int attributePoints,
            double mana
    ) {

        this.name = name;
        this.level = level;
        this.experience = experience;
        this.attributes = attributes;
        this.attributePoints = attributePoints;
        this.mana = Math.min(mana, getMaxMana());
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

    public void equip(Equipment equipment) {
        equippedItems.put(equipment.getType(), equipment);
    }

    public void unequip(EquipmentType type) {

        equippedItems.remove(type);
    }

    public Equipment getEquipped(EquipmentType type) {

        return equippedItems.get(type);

    }

    public Map<EquipmentType, Equipment> getEquippedItems() {

        return equippedItems;

    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return 100;
    }

    public void takeDamage(double amount) {
        this.health = Math.max(0, this.health - amount);
    }

    public void restoreHealth(double amount) {
        this.health = Math.min(getMaxHealth(), this.health + amount);
    }

    public void restoreFullHealth() {
        this.health = getMaxHealth();
    }

    public boolean isAlive() {
        return health > 0;
    }


}