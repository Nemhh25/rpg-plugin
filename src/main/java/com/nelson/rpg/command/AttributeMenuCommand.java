package com.nelson.rpg.command;


import com.nelson.rpg.manager.PlayerManager;
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
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;

public class AttributeMenuCommand implements CommandExecutor {

    private final PlayerManager playerManager;


    public AttributeMenuCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    private ItemStack createAttributeItem(Material material, String name, ChatColor color, String description, int value) {

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        meta.setItemName(color + name);

        meta.setLore(Arrays.asList(ChatColor.GRAY + description, "", ChatColor.WHITE + "Valor atual: " + color + value));

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack createIncreaseButton(ChatColor color, boolean canIncrease) {

        ItemStack item = new ItemStack(canIncrease ? Material.LIME_DYE : Material.GRAY_DYE);

        ItemMeta meta = item.getItemMeta();

        if (canIncrease) {

            meta.setItemName(color + "+ Aumentar");

            meta.setLore(Arrays.asList(ChatColor.GRAY + "Aumenta este atributo em 1.", "", ChatColor.WHITE + "Custo: " + ChatColor.GOLD + "1 ponto"));

        } else {

            meta.setItemName(ChatColor.RED + "✖ Sem pontos");

            meta.setLore(Arrays.asList(ChatColor.GRAY + "Você não possui pontos", ChatColor.GRAY + "de atributo disponíveis."));
        }

        item.setItemMeta(meta);

        return item;
    }


    public void openMenu(Player player) {

        RPGCharacter character = playerManager.getCharacter(player.getUniqueId());

        if (character == null) {
            return;
        }

        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "✦ " + ChatColor.LIGHT_PURPLE + "Seus Atributos" + ChatColor.DARK_PURPLE + " ✦");

        // =========================
        // FUNDO
        // =========================

        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        ItemMeta fillerMeta = filler.getItemMeta();

        fillerMeta.setItemName(ChatColor.DARK_GRAY + " ");

        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        // =========================
        // BORDA
        // =========================

        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        ItemMeta borderMeta = border.getItemMeta();

        borderMeta.setItemName(ChatColor.BLACK + " ");

        border.setItemMeta(borderMeta);

        int[] borderSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25, 26, 9, 17};

        for (int slot : borderSlots) {
            inventory.setItem(slot, border);
        }
        // =========================
        // ATRIBUTOS
        // =========================

        ItemStack strengthItem = createAttributeItem(Material.IRON_SWORD, "⚔ Força", ChatColor.RED, "Aumenta o dano físico.", character.getAttributes().getStrength());

        ItemStack defenseItem = createAttributeItem(Material.SHIELD, "🛡 Defesa", ChatColor.BLUE, "Reduz o dano recebido.", character.getAttributes().getDefense());

        ItemStack intelligenceItem = createAttributeItem(Material.ENCHANTING_TABLE, "🔮 Inteligência", ChatColor.LIGHT_PURPLE, "Aumenta o poder das habilidades.", character.getAttributes().getIntelligence());

        inventory.setItem(10, strengthItem);
        inventory.setItem(13, defenseItem);
        inventory.setItem(16, intelligenceItem);


        // =========================
        // BOTÕES +
        // =========================

        int attributePoints = character.getAttributePoints();

        boolean canIncrease = attributePoints > 0;

        ItemStack strengthButton = createIncreaseButton(ChatColor.RED, canIncrease);

        ItemStack defenseButton = createIncreaseButton(ChatColor.BLUE, canIncrease);

        ItemStack intelligenceButton = createIncreaseButton(ChatColor.LIGHT_PURPLE, canIncrease);

        inventory.setItem(11, strengthButton);
        inventory.setItem(14, defenseButton);
        inventory.setItem(17, intelligenceButton);

        // =========================
// BARRAS DE PROGRESSO
// =========================




        // =========================
        // PONTOS DISPONÍVEIS
        // =========================

        ItemStack pointsItem = new ItemStack(Material.EXPERIENCE_BOTTLE);

        ItemMeta pointsMeta = pointsItem.getItemMeta();

        pointsMeta.setItemName(ChatColor.GOLD + "✦ Pontos de Atributo");

        pointsMeta.setLore(Arrays.asList(ChatColor.GRAY + "Pontos disponíveis para gastar.", "", ChatColor.WHITE + "Disponíveis: " + ChatColor.GOLD + character.getAttributePoints()));

        pointsItem.setItemMeta(pointsMeta);

        inventory.setItem(4, pointsItem);


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
