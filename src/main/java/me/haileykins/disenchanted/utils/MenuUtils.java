package me.haileykins.disenchanted.utils;

import me.haileykins.disenchanted.Disenchanted;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuUtils {

    private ConfigUtils cfgUtils;
    private Disenchanted plugin;

    public MenuUtils(ConfigUtils configUtils, Disenchanted pl) {
        cfgUtils = configUtils;
        plugin = pl;
    }

    void chargeAndDispense(Enchantment enchant, ItemStack item, Player player, ItemStack eBook, ItemMeta eBookMeta) {
        int cost = cfgUtils.getEnchantmentCost(enchant.getName(), item.getEnchantments().get(enchant));

        if (cfgUtils.useEconomy) {
            if (!plugin.getEconomy().has(player, cost)) {
                player.sendMessage(cfgUtils.prefColor(cfgUtils.cantAffordMsg));
                return;
            }

            plugin.getEconomy().withdrawPlayer(player, cost);
        } else {
            if (player.getLevel() < cost) {
                player.sendMessage(cfgUtils.prefColor(cfgUtils.cantAffordMsg));
                return;
            }

            player.setLevel(player.getLevel() - cost);
        }

        dispense(eBook, eBookMeta, player, enchant);
    }

    private void dispense(ItemStack eBook, ItemMeta eBookMeta, Player player, Enchantment enchant) {
        eBook.setItemMeta(eBookMeta);

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(cfgUtils.prefColor(cfgUtils.noSpace));
            return;
        }

        player.getInventory().addItem(eBook);

        player.getInventory().getItemInMainHand().removeEnchantment(enchant);

        player.sendMessage(cfgUtils.prefColor(cfgUtils.successfulBuy));

        player.closeInventory();
    }
}
