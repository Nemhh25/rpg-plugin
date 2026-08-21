package com.nelson.rpg.service;

import com.nelson.rpg.model.RPGCharacter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import com.nelson.rpg.model.Attributes;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import java.util.UUID;

public class PlayerDataService {

    private final JavaPlugin plugin;

    public PlayerDataService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void saveCharacter(UUID uuid, RPGCharacter character) {

        File folder = new File(plugin.getDataFolder(), "players");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, uuid + ".yml");

        YamlConfiguration config = new YamlConfiguration();

        config.set("name", character.getName());
        config.set("level", character.getLevel());
        config.set("experience", character.getExperience());

        config.set("attribute-points", character.getAttributePoints());

        config.set("mana", character.getMana());

        config.set("attributes.strength", character.getAttributes().getStrength());
        config.set("attributes.defense", character.getAttributes().getDefense());
        config.set("attributes.intelligence", character.getAttributes().getIntelligence());

        try {

            config.save(file);

        } catch (IOException e) {

            plugin.getLogger().severe("Não foi possível salvar o personagem " + uuid);

            e.printStackTrace();
        }
    }

    public RPGCharacter loadCharacter(UUID uuid) {

        File folder = new File(plugin.getDataFolder(), "players");

        File file = new File(folder, uuid + ".yml");

        if (!file.exists()) {
            return null;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String name = config.getString("name");

        int level = config.getInt("level");
        int experience = config.getInt("experience");

        int attributePoints = config.getInt("attribute-points");

        double mana = config.getDouble("mana");

        int strength = config.getInt("attributes.strength");
        int defense = config.getInt("attributes.defense");
        int intelligence = config.getInt("attributes.intelligence");

        Attributes attributes = new Attributes(strength, defense, intelligence);

        return new RPGCharacter(name, level, experience, attributes, attributePoints, mana);
    }

    public void deleteCharacter(UUID uuid) {

        File folder = new File(plugin.getDataFolder(), "players");

        File file = new File(folder, uuid + ".yml");

        if (file.exists()) {
            file.delete();
        }
    }
}