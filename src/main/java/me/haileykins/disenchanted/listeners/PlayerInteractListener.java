package me.haileykins.disenchanted.listeners;

import me.haileykins.disenchanted.Disenchanted;
import me.haileykins.disenchanted.utils.ConfigUtils;
import me.haileykins.disenchanted.utils.DisenchantmentMenu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private DisenchantmentMenu disenchantMenu;
    private ConfigUtils configUtils;

    public PlayerInteractListener(DisenchantmentMenu disenchantMenu, ConfigUtils configUtils) {
        this.disenchantMenu = disenchantMenu;
        this.configUtils = configUtils;
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (event.getClickedBlock().getType() != Material.ENCHANTING_TABLE) {
            return;
        }

        if (event.getItem() == null) {
            return;
        }

        if (configUtils.bannedItems.contains(event.getItem().getType().toString())) {
            event.getPlayer().sendMessage(configUtils.prefColor("banned-item"));
            return;
        }

        if (!event.getItem().getItemMeta().hasEnchants()) {
            return;
        }

        if (!event.getPlayer().hasPermission("disenchanted.disenchant")) {
            event.getPlayer().sendMessage(configUtils.prefColor("no-permission-message"));
            return;
        }

        event.getPlayer().openInventory(disenchantMenu.open(event.getItem()));
    }
}
