package com.nelson.rpg.manager;

import com.nelson.rpg.model.RPGCharacter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private final Map<UUID, RPGCharacter> characters = new HashMap<>();


    public void createCharacter(UUID uuid, String name) {

        RPGCharacter character = new RPGCharacter(name);

        characters.put(uuid, character);

    }


    public RPGCharacter getCharacter(UUID uuid) {

        return characters.get(uuid);

    }

    public boolean hasCharacter(UUID uuid) {

        return characters.containsKey(uuid);

    }

}