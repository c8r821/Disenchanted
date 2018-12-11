package me.haileykins.disenchanted.utils;

import me.haileykins.disenchanted.Disenchanted;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigUtils {

    private Disenchanted plugin;

    public ConfigUtils(Disenchanted pl) {
        plugin = pl;
    }

    public boolean useEconomy;
    List<String> disabledEnchants;
    public List<String> bannedItems;
    private String prefix;
    public String reloadMsg;
    String cantAffordMsg;
    public String noPermMsg;
    String cantDisenchantMsg;
    String noSpace;
    String successfulBuy;
    String bannedItem;

    public void loadConfig() {
        plugin.saveDefaultConfig();
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        useEconomy = config.getBoolean("use-economy");
        disabledEnchants = config.getStringList("disabled-enchants");
        bannedItems = config.getStringList("banned-items");
        prefix = config.getString("prefix");
        reloadMsg = config.getString("reload-message");
        cantAffordMsg = config.getString("cant-afford-message");
        noPermMsg = config.getString("no-permission-message");
        cantDisenchantMsg = config.getString("cant-disenchant-message");
        noSpace = config.getString("no-inventory-space");
        successfulBuy = config.getString("purchase-successful");
        bannedItem = config.getString("banned-item");

        plugin.saveConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        loadConfig();
        plugin.getConfig();
    }

    public String prefColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
    }

    String getCommonName(String enchantment) {
        String commonName = null;

        if (enchantment.equals("ARROW_DAMAGE")) {
            commonName = "power";
        }

        if (enchantment.equals("ARROW_FIRE")) {
            commonName = "flame";
        }

        if (enchantment.equals("ARROW_INFINITE")) {
            commonName = "infinity";
        }

        if (enchantment.equals("ARROW_KNOCKBACK")) {
            commonName = "punch";
        }

        if (enchantment.equals("BINDING_CURSE")) {
            commonName = "binding_curse";
        }

        if (enchantment.equals("CHANNELING")) {
            commonName = "channeling";
        }

        if (enchantment.equals("DAMAGE_ALL")) {
            commonName = "sharpness";
        }

        if (enchantment.equals("DAMAGE_ARTHROPODS")) {
            commonName = "bane_of_arthropods";
        }

        if (enchantment.equals("DAMAGE_UNDEAD")) {
            commonName = "smite";
        }

        if (enchantment.equals("DEPTH_STRIDER")) {
            commonName = "depth_strider";
        }

        if (enchantment.equals("DIG_SPEED")) {
            commonName = "efficiency";
        }

        if (enchantment.equals("DURABILITY")) {
            commonName = "unbreaking";
        }

        if (enchantment.equals("FIRE_ASPECT")) {
            commonName = "fire_aspect";
        }

        if (enchantment.equals("FROST_WALKER")) {
            commonName = "frost_walker";
        }

        if (enchantment.equals("IMPALING")) {
            commonName = "impaling";
        }

        if (enchantment.equals("KNOCKBACK")) {
            commonName = "knockback";
        }

        if (enchantment.equals("LOOT_BONUS_BLOCKS")) {
            commonName = "fortune";
        }

        if (enchantment.equals("LOOT_BONUS_MOBS")) {
            commonName = "looting";
        }

        if (enchantment.equals("LOYALTY")) {
            commonName = "loyalty";
        }

        if (enchantment.equals("LUCK")) {
            commonName = "luck_of_the_sea";
        }

        if (enchantment.equals("LURE")) {
            commonName = "lure";
        }

        if (enchantment.equals("MENDING")) {
            commonName = "mending";
        }

        if (enchantment.equals("OXYGEN")) {
            commonName = "respiration";
        }

        if (enchantment.equals("PROTECTION_ENVIRONMENTAL")) {
            commonName = "protection";
        }

        if (enchantment.equals("PROTECTION_EXPLOSIONS")) {
            commonName = "blast_protection";
        }

        if (enchantment.equals("PROTECTION_FALL")) {
            commonName = "feather_falling";
        }

        if (enchantment.equals("PROTECTION_FIRE")) {
            commonName = "fire_protection";
        }

        if (enchantment.equals("PROTECTION_PROJECTILE")) {
            commonName = "projectile_protection";
        }

        if (enchantment.equals("RIPTIDE")) {
            commonName = "riptide";
        }

        if (enchantment.equals("SILK_TOUCH")) {
            commonName = "silk_touch";
        }

        if (enchantment.equals("SWEEPING_EDGE")) {
            commonName = "sweeping_edge";
        }

        if (enchantment.equals("THORNS")) {
            commonName = "thorns";
        }

        if (enchantment.equals("VANISHING_CURSE")) {
            commonName = "vanishing_curse";
        }

        if (enchantment.equals("WATER_WORKER")) {
            commonName = "aqua_affinity";
        }

        return commonName;
    }

    int getEnchantmentCost(String enchantment, int level) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection enchants = config.getConfigurationSection("enchantments");

        String commonName = getCommonName(enchantment);

        return enchants.getInt(commonName + "." + level);
    }
}
