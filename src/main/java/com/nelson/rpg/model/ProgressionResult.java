package com.nelson.rpg.model;

public class ProgressionResult {

    private final int experienceGained;
    private final int levelsGained;

    public ProgressionResult(int experienceGained, int levelsGained) {
        this.experienceGained = experienceGained;
        this.levelsGained = levelsGained;

    }

    public int getExperienceGained() {
        return experienceGained;
    }

    public int getLevelsGained() {
        return levelsGained;
    }
}
