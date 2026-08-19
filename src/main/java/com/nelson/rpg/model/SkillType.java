package com.nelson.rpg.model;

public enum SkillType {

    FIREBALL(
            "Fireball",
            30,
            5000,
            AttributeType.INTELLIGENCE,
            10
    );

    private final String displayName;
    private final double manaCost;
    private final long cooldown;
    private final AttributeType scalingAttribute;
    private final double baseDamage;

    SkillType(
            String displayName,
            double manaCost,
            long cooldown,
            AttributeType scalingAttribute,
            double baseDamage
    ) {
        this.displayName = displayName;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.scalingAttribute = scalingAttribute;
        this.baseDamage = baseDamage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getManaCost() {
        return manaCost;
    }

    public long getCooldown() {
        return cooldown;
    }

    public AttributeType getScalingAttribute() {
        return scalingAttribute;
    }

    public double getBaseDamage() {
        return baseDamage;
    }
}