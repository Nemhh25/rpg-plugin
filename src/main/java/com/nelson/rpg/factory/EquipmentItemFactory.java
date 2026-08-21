package com.nelson.rpg.factory;

import com.nelson.rpg.model.Equipment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class EquipmentItemFactory {

    private final NamespacedKey equipmentKey;

    public EquipmentItemFactory(JavaPlugin plugin) {
        this.equipmentKey = new NamespacedKey(plugin, "rpg_equipment");
    }

    public ItemStack createItem(Equipment equipment) {

        ItemStack item;

        if (equipment.getName().equals("Espada de Ferro")) {

            item = new ItemStack(Material.IRON_SWORD);

        } else {

            return null;
        }

        ItemMeta meta = item.getItemMeta();

        meta.setItemName(ChatColor.RED + "⚔ " + equipment.getName());

        meta.setLore(Arrays.asList(ChatColor.GRAY + "Uma espada simples.", "", ChatColor.WHITE + "Bônus:", ChatColor.RED + "+" + equipment.getAttributeBonus() + " " + equipment.getAttributeType()));

        meta.getPersistentDataContainer().set(equipmentKey, PersistentDataType.STRING, "iron_sword");

        item.setItemMeta(meta);

        return item;
    }
}