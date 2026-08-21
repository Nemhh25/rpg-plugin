package com.nelson.rpg.command;

import com.nelson.rpg.manager.PlayerManager;
import com.nelson.rpg.model.Equipment;
import com.nelson.rpg.model.EquipmentType;
import com.nelson.rpg.model.RPGCharacter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EquipmentMenuCommand implements CommandExecutor {

    private final PlayerManager playerManager;

    public EquipmentMenuCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void openMenu(Player player) {

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());

        if (character == null) {
            return;
        }

        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "✦ " + ChatColor.LIGHT_PURPLE + "Seus Equipamentos" + ChatColor.DARK_PURPLE + " ✦");

        // FUNDO

        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        ItemMeta fillerMeta = filler.getItemMeta();

        fillerMeta.setItemName(ChatColor.DARK_GRAY + " ");

        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        // BORDA

        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        ItemMeta borderMeta = border.getItemMeta();

        borderMeta.setItemName(ChatColor.BLACK + " ");

        border.setItemMeta(borderMeta);

        int[] borderSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25, 26, 9, 17};

        for (int slot : borderSlots) {
            inventory.setItem(slot, border);
        }

        // ARMA EQUIPADA

        Equipment weapon = character.getEquipped(EquipmentType.WEAPON);

        if (weapon != null) {

            ItemStack weaponItem = new ItemStack(Material.IRON_SWORD);

            ItemMeta weaponMeta = weaponItem.getItemMeta();

            weaponMeta.setItemName(ChatColor.RED + "⚔ " + weapon.getName());

            weaponMeta.setLore(Arrays.asList(ChatColor.GRAY + "Arma equipada.", "", ChatColor.WHITE + "Bônus:", ChatColor.RED + "+" + weapon.getAttributeBonus() + " " + weapon.getAttributeType()));

            weaponItem.setItemMeta(weaponMeta);

            inventory.setItem(13, weaponItem);
        }

        player.openInventory(inventory);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {

            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem usar esse comando.");

            return true;
        }

        openMenu(player);

        return true;
    }
}