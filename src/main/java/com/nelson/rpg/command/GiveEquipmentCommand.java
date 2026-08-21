package com.nelson.rpg.command;

import com.nelson.rpg.factory.EquipmentItemFactory;
import com.nelson.rpg.model.AttributeType;
import com.nelson.rpg.model.Equipment;
import com.nelson.rpg.model.EquipmentType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveEquipmentCommand implements CommandExecutor {

    private final EquipmentItemFactory equipmentItemFactory;

    public GiveEquipmentCommand(JavaPlugin plugin, EquipmentItemFactory equipmentItemFactory) {
        this.equipmentItemFactory = equipmentItemFactory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        System.out.println(">>> GiveEquipmentCommand EXECUTOU!");
        if (!(sender instanceof Player player)) {

            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem usar esse comando.");

            return true;
        }

        Equipment equipment = new Equipment("Espada de Ferro", EquipmentType.WEAPON, AttributeType.STRENGTH, 3);

        ItemStack item = equipmentItemFactory.createItem(equipment);

        player.getInventory().addItem(item);

        player.sendMessage(ChatColor.GREEN + "Você recebeu uma Espada de Ferro RPG!");

        return true;
    }
}