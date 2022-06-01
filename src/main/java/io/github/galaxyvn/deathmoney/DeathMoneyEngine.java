package io.github.galaxyvn.deathmoney;

import io.github.galaxyvn.deathmoney.config.Configurable;
import io.github.galaxyvn.deathmoney.config.Configuration;
import io.github.galaxyvn.deathmoney.menus.MenuManager;
import io.github.galaxyvn.deathmoney.menus.settings.MessagesMenu;
import io.github.galaxyvn.deathmoney.menus.settings.MessagesSettingsMenu;
import io.github.galaxyvn.deathmoney.menus.settings.SettingsMenu;
import org.bukkit.configuration.ConfigurationSection;

public class DeathMoneyEngine implements Configurable {

    private static boolean enabled = false;
    private DeathMoney plugin;
    private MenuManager menuManager;


    @Override
    public void updateConfig(ConfigurationSection section) {}

    private DeathMoneyEngine() {}

    DeathMoneyEngine(DeathMoney plugin) {
        if (enabled) return;
        enabled  = true;
        this.plugin = plugin;
        this.menuManager = new MenuManager(plugin);

        Configuration config = plugin.getRootConfig();

        plugin.registerListeners(menuManager);
        menuManager.register(
                new SettingsMenu(plugin, menuManager),
                new MessagesSettingsMenu(plugin, menuManager),
                new MessagesMenu(plugin, menuManager));
//                new TempSettingsMenu(plugin, menuManager),
//                new TempScanningMenu(plugin, menuManager),
//                new LangSettingsMenu(plugin, menuManager),
//                new LeafParticleMenu(plugin, menuManager),
//                new OtherSettingsMenu(plugin, menuManager));
        menuManager.getMenus().forEach(config::subscribe);
    }

    public DeathMoney getPlugin() {
        return plugin;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    DeathMoneyEngine start() {
        return this;
    }

    void shutdown() {
    }
}
