package me.haileykins.disenchanted;

import me.haileykins.disenchanted.commands.ReloadCommand;
import me.haileykins.disenchanted.listeners.InventoryCloseListener;
import me.haileykins.disenchanted.listeners.PlayerInteractListener;
import me.haileykins.disenchanted.listeners.UpdateListener;
import me.haileykins.disenchanted.utils.ConfigUtils;
import me.haileykins.disenchanted.utils.DisenchantmentMenu;
import me.haileykins.disenchanted.utils.MenuUtils;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Disenchanted extends JavaPlugin {

    private Economy econ = null;

    public void onEnable() {
        //noinspection unused
        Metrics metrics = new Metrics(this);

        ConfigUtils cfgUtils = new ConfigUtils(this);
        MenuUtils menuUtils = new MenuUtils(cfgUtils, this);
        DisenchantmentMenu dm = new DisenchantmentMenu(cfgUtils, menuUtils);

        getServer().getPluginManager().registerEvents(new DisenchantmentMenu(cfgUtils, menuUtils), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(dm, cfgUtils), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new UpdateListener(this, cfgUtils), this);

        getCommand("dereload").setExecutor(new ReloadCommand(cfgUtils));

        cfgUtils.loadConfig();

        if (!setupEconomy()) {
            if (cfgUtils.useEconomy) {
                getLogger().severe("Disabling, Use Economy Is True But There Is No Economy Found!");
                getServer().getPluginManager().disablePlugin(this);
            }
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEconomy() {
        return econ;
    }
}
