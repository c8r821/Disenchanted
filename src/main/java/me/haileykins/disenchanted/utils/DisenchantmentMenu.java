package me.haileykins.disenchanted.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DisenchantmentMenu implements Listener {

    private ConfigUtils cfgUtils;
    private MenuUtils menuUtils;

    public DisenchantmentMenu(ConfigUtils configUtils, MenuUtils menuUtilities) {
        cfgUtils = configUtils;
        menuUtils = menuUtilities;
    }

    public Inventory open(ItemStack item) {
        Inventory inv;

        if (item.getEnchantments().size() <= 5) {
            inv = Bukkit.createInventory(null, InventoryType.HOPPER, "Disenchantment Menu");
        } else if (item.getEnchantments().size() <= 9) {
            inv = Bukkit.createInventory(null, 9, "Disenchantment Menu");
        } else {
            inv = Bukkit.createInventory(null, 18, "Disenchantment Menu");
        }

        int slot = 0;

        for (Enchantment enchantment : item.getEnchantments().keySet()) {
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta bookMeta = book.getItemMeta();

            bookMeta.addEnchant(enchantment, item.getEnchantmentLevel(enchantment), false);

            List<String> lore = new ArrayList<>();

            int price = cfgUtils.getEnchantmentCost(enchantment.getName(), item.getEnchantments().get(enchantment));

            if (cfgUtils.useEconomy) {
                lore.add("$" + price);
            } else {
                lore.add(price + " XP");
            }

            bookMeta.setLore(lore);
            book.setItemMeta(bookMeta);

            inv.setItem(slot, book);

            slot++;
        }

        return inv;
    }

    @EventHandler
    private void inventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (!event.getClickedInventory().getName().equalsIgnoreCase("Disenchantment Menu")) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        ItemStack eBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta eBookMeta = (EnchantmentStorageMeta) eBook.getItemMeta();

        Enchantment enchant = null;

        for (Enchantment enchantment : item.getEnchantments().keySet()) {
            eBookMeta.addStoredEnchant(enchantment, item.getEnchantments().get(enchantment), false);
            enchant = enchantment;
        }

        if (enchant == null) {
            return;
        }

        if (cfgUtils.disabledEnchants.contains(cfgUtils.getCommonName(enchant.getName()))) {
            player.sendMessage(cfgUtils.prefColor(cfgUtils.cantDisenchantMsg));
            return;
        }

        menuUtils.chargeAndDispense(enchant, item, player, eBook, eBookMeta);

        player.openInventory(open(player.getInventory().getItemInMainHand()));
    }
}
