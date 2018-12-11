package me.haileykins.disenchanted.commands;

import me.haileykins.disenchanted.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private ConfigUtils cfgUtils;

    public ReloadCommand(ConfigUtils configUtils) {
        cfgUtils = configUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender.hasPermission("disenchanted.reload")) {
            cfgUtils.reloadConfig();
            sender.sendMessage(cfgUtils.prefColor(cfgUtils.reloadMsg));
            return true;
        }

        sender.sendMessage(cfgUtils.prefColor(cfgUtils.noPermMsg));
        return true;
    }
}
