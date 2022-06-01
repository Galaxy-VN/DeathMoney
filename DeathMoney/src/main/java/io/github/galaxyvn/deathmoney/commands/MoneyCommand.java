package io.github.galaxyvn.deathmoney.commands;

import io.github.galaxyvn.deathmoney.DeathMoney;
import io.github.galaxyvn.deathmoney.DeathMoneyFiles;
import io.github.galaxyvn.deathmoney.PluginData;
import io.github.galaxyvn.deathmoney.menus.Menus;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MoneyCommand extends DeathCommand {

    private DeathMoney plugin;

    public MoneyCommand(DeathMoney plugin) {
        super("deathmoney");
        this.plugin = plugin;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            msg("");
            msg("&7&m----------&c&l Death&6&lMoney &7&m----------");
            msg("");
            msg(new PluginData.Colors().secondary("/deathmoney settings")
                    .message(" - Opens a GUI to change settings."));
            msg(new PluginData.Colors().secondary("/deathmoney reload")
                    .message(" - Reloads all config files"));
            msg("");
            msg("&7&m--------------------------------");
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            msg(PluginData.PREFIX + "Reloading files...");
            plugin.getRootConfig().reload();
            DeathMoneyFiles.loadConfigurations();
            msg(PluginData.PREFIX + "Reloaded config files");
        } else if (args[0].equalsIgnoreCase("settings")) {
            if (wasPlayer()) {
                plugin.getEngine().getMenuManager()
                        .getMenu(Menus.SETTINGS.getId()).open((Player) getSender());
            } else {
                msg("Settings can only be accessed in game.");
            }
        }
    }

    @Override
    public List<String> tabComplete(String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "settings");
        }
        return super.tabComplete(args);
    }
}
