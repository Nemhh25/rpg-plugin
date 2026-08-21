package com.nelson.rpg.model;

public class Equipment {

    private final String name;
    private final EquipmentType type;
    private final AttributeType attributeType;
    private final int attributeBonus;

    public Equipment(
            String name,
            EquipmentType type,
            AttributeType attributeType,
            int attributeBonus
    ) {
        this.name = name;
        this.type = type;
        this.attributeType = attributeType;
        this.attributeBonus = attributeBonus;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public int getAttributeBonus() {
        return attributeBonus;
    }
}