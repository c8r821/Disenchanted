package me.haileykins.disenchanted.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryCloseListener implements Listener {

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase("Disenchantment Menu")) {
            return;
        }

        for (ItemStack item : event.getInventory().getContents()) {
            if (item == null) {
                continue;
            }

            if (item.getType() == Material.ENCHANTED_BOOK) {
                continue;
            }

            event.getPlayer().getInventory().addItem(item);

        }
    }
}