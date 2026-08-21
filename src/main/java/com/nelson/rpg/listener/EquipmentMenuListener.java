package com.nelson.rpg.listener;

import com.nelson.rpg.command.EquipmentMenuCommand;
import com.nelson.rpg.factory.EquipmentItemFactory;
import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.Equipment;
import com.nelson.rpg.model.EquipmentType;
import com.nelson.rpg.model.RPGCharacter;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class EquipmentMenuListener implements Listener {

    private final PlayerManager playerManager;
    private final EquipmentMenuCommand equipmentMenuCommand;

    public EquipmentMenuListener(PlayerManager playerManager, EquipmentMenuCommand equipmentMenuCommand, EquipmentItemFactory equipmentItemFactory) {
        this.playerManager = playerManager;
        this.equipmentMenuCommand = equipmentMenuCommand;
    }

    private boolean isEquipmentMenu(String title) {

        return title.equals(ChatColor.DARK_PURPLE + "✦ " + ChatColor.LIGHT_PURPLE + "Seus Equipamentos" + ChatColor.DARK_PURPLE + " ✦");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!isEquipmentMenu(event.getView().getTitle())) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (event.getRawSlot() != 13) {
            return;
        }

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());

        if (character == null) {
            return;
        }

        Equipment equipment = character.getEquipped(EquipmentType.WEAPON);

        if (equipment == null) {
            return;
        }

        character.unequip(EquipmentType.WEAPON);

        player.getInventory().addItem(createItem(equipment));

        player.sendMessage(ChatColor.YELLOW + "Você desequipou " + equipment.getName() + "!");

        int strength = character.getAttributes().getFinalValue(AttributeType.STRENGTH, character.getEquippedItems());

        player.sendMessage(ChatColor.RED + "Força: " + strength);

        equipmentMenuCommand.openMenu(player);
    }

    private org.bukkit.inventory.ItemStack createItem(Equipment equipment) {

        org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(org.bukkit.Material.IRON_SWORD);

        org.bukkit.inventory.meta.ItemMeta meta = item.getItemMeta();

        meta.setItemName(ChatColor.RED + "⚔ " + equipment.getName());

        meta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Uma espada simples.", "", ChatColor.WHITE + "Bônus:", ChatColor.RED + "+" + equipment.getAttributeBonus() + " " + equipment.getAttributeType()));

        item.setItemMeta(meta);

        return item;
    }
}