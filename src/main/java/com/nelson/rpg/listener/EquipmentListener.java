package com.nelson.rpg.listener;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.Equipment;
import com.nelson.rpg.model.EquipmentType;
import com.nelson.rpg.model.RPGCharacter;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class EquipmentListener implements Listener {

    private final PlayerManager playerManager;
    private final NamespacedKey equipmentKey;

    public EquipmentListener(JavaPlugin plugin, PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.equipmentKey = new NamespacedKey(plugin, "rpg_equipment");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        // Ignora a interação da mão secundária
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();

        ItemStack item = event.getItem();

        if (item == null || item.getType().isAir()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        String equipmentId = meta.getPersistentDataContainer().get(equipmentKey, PersistentDataType.STRING);

        if (equipmentId == null) {
            return;
        }

        if (!equipmentId.equals("iron_sword")) {
            return;
        }

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());

        if (character == null) {
            return;
        }

        Equipment equipment = new Equipment("Espada de Ferro", EquipmentType.WEAPON, AttributeType.STRENGTH, 3);

        if (character.getEquipped(EquipmentType.WEAPON) != null) {

            event.setCancelled(true);

            player.sendMessage(ChatColor.RED + "Você já possui uma arma equipada.");

            return;
        }


        event.setCancelled(true);


        character.equip(equipment);


        player.getInventory().setItemInMainHand(null);

        player.sendMessage(ChatColor.GREEN + "Você equipou " + equipment.getName() + "!");

        player.sendMessage(ChatColor.RED + "Força: " + character.getAttributes().getFinalValue(AttributeType.STRENGTH, character.getEquippedItems()));
    }
}