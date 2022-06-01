package io.github.galaxyvn.deathmoney;

import io.github.galaxyvn.deathmoney.commands.DeathCommand;
import io.github.galaxyvn.deathmoney.commands.MoneyCommand;
import io.github.galaxyvn.deathmoney.config.Configurable;
import io.github.galaxyvn.deathmoney.config.Configuration;
import io.github.galaxyvn.deathmoney.event.DamageListener;
import io.github.galaxyvn.deathmoney.event.DeathListener;
import io.github.galaxyvn.deathmoney.hooks.PlaceholderApiHook;
import io.github.galaxyvn.deathmoney.hooks.VaultEconomyHook;
import io.github.galaxyvn.deathmoney.placeholders.DeathMoneyExpansion;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeathMoney extends JavaPlugin {

    private static DeathMoney instance;
    private static String serverVersion;

    private DeathMoneyEngine engine;
    private Configuration config;

    public static DeathMoney getInstance() {
        return instance;
    }
    public static String getServerVersion() {
        return serverVersion;
    }

    public DeathMoneyEngine getEngine() { return engine; }
    public Configuration getRootConfig() { return config; }

    Logger logger = getLogger();

    @Override
    public void onEnable() {
        // Instance
        instance = this;
        // Server Version
        serverVersion = getServer().getClass().getPackage().getName().split("\\.")[3];
        // Config
        config = new Configuration(this, "config").create(true);
        // Files
        DeathMoneyFiles.loadFiles();
        DeathMoneyFiles.loadConfigurations();
        // Wtf is my engine?
        engine = new DeathMoneyEngine(this);
        // Hook
        if (PlaceholderApiHook.isHooked()) {
            logger.log(Level.INFO, "PlaceholderAPI found, hooking...");
        }
        // Command
        registerCommands(new MoneyCommand(this));
        // Listener
        registerListeners(new DeathListener(), new DamageListener());
        // Setup placeholders
        new DeathMoneyExpansion().register();
        // Setup economy
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            logger.log(Level.INFO, "Vault found, hooking...");
            VaultEconomyHook vault = new VaultEconomyHook();
            if (!vault.serviceAvailable()) {
                logger.log(Level.WARNING, "Vault Economy service is not available. Look like you don't have economy plugin :(");
                logger.log(Level.WARNING, "Disabling plugin...");
                getServer().getPluginManager().disablePlugin(this);
                return;
            } else DeathMoneyHelper.economy = vault;
        }
        engine.start();
        config.save();
        config.reload();
        logger.log(Level.INFO, "Plugin has been enabled, thank for using <3");
    }

    @Override
    public void onDisable() {
        if (engine != null) engine.shutdown();
        logger.log(Level.INFO, "[DeathMoney] Plugin has been disabled, thank for using <3");
    }

    private void registerCommands(DeathCommand... commands) {
        for (DeathCommand cmd : commands) {
            PluginCommand pc = Bukkit.getPluginCommand(cmd.getName());
            if (pc == null) continue;
            pc.setExecutor(cmd);
            pc.setTabCompleter(cmd);
        }
    }

    protected void registerListeners(Listener... listeners) {
        PluginManager manager = Bukkit.getPluginManager();
        for (Listener l : listeners) {
            manager.registerEvents(l, this);
            if (Configurable.class.isAssignableFrom(l.getClass())) {
                config.subscribe((Configurable) l);
            }
        }
    }
}
