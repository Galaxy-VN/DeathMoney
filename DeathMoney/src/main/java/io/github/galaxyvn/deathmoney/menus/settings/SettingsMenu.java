package io.github.galaxyvn.deathmoney.menus.settings;

import io.github.galaxyvn.deathmoney.DeathMoney;
import io.github.galaxyvn.deathmoney.DeathMoneyFiles;
import io.github.galaxyvn.deathmoney.PluginData;
import io.github.galaxyvn.deathmoney.config.Configuration;
import io.github.galaxyvn.deathmoney.core.item.ItemBuilder;
import io.github.galaxyvn.deathmoney.core.item.SkullIndex;
import io.github.galaxyvn.deathmoney.libary.xseries.XMaterial;
import io.github.galaxyvn.deathmoney.menus.Button;
import io.github.galaxyvn.deathmoney.menus.GlobalMenu;
import io.github.galaxyvn.deathmoney.menus.MenuManager;
import io.github.galaxyvn.deathmoney.menus.Menus;
import io.github.galaxyvn.deathmoney.utils.Hex;
import io.github.galaxyvn.deathmoney.utils.StringUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class SettingsMenu extends GlobalMenu {

    private final DeathMoney PLUGIN;
    private final MenuManager MANAGER;
    private final Configuration CONFIG;
//    private VersionCheckInfo versionInfo;

    //
    // Configuration
    //
    private boolean checkUpdates;
    private boolean notifyUpdates;
    private boolean messEnabled;

    protected static final ItemStack BORDER = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setName("&r").build();

    public SettingsMenu(DeathMoney plugin, MenuManager menuManager) {
        super(Menus.SETTINGS, 5);
        this.CONFIG = plugin.getRootConfig();
        this.MANAGER = menuManager;
        this.PLUGIN = plugin;
    }

    @Override
    public void updateConfig(ConfigurationSection section) {
        checkUpdates = section.getBoolean("general.check-for-updates");
        notifyUpdates = section.getBoolean("general.notify-update-join");
        messEnabled = section.getBoolean("global.money.message-enabled");
        build();
    }

    @Override
    public String getTitle() {
        return DeathMoneyFiles.getMessages().getString("Gui.main.title");
    }

    @Override
    public void build() {
        setButton(new Button(asSlot(0, 4), () -> {
            String version = PLUGIN.getDescription().getVersion();

            return new ItemBuilder(SkullIndex.DEATHMONEY)
                    .setName("&eDeathMoney")
                    .addDescription("A plugin to make gamemode more harder.")
                    .addLore("&7Developed by &f" + StringUtil.fromStringArray(PLUGIN.getDescription().getAuthors(), ", "),
                            "&7Version: &e" + version)
                    .addDescription("Click to get help links to the project",
                            PluginData.Colors.ACTION, false)
                    .build();
        }).onClick(player -> {
            player.closeInventory();
            ComponentBuilder builder = new ComponentBuilder("");
            builder.append("\n       ")
                    .append(Hex.colorify("&#9946B2DeathMoney"));
            builder.append("\n       ").bold(false)
                    .append("\n");
            builder.append("    Download Page ", ComponentBuilder.FormatRetention.NONE)
                    .color(PluginData.Colors.MESSAGE);
            builder.append("VISIT").color(PluginData.Colors.TERTIARY).bold(true).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new Text("\u00A7eClick to visit the plugins download page\non minecraftvn.net.")))
                    .event(new ClickEvent(ClickEvent.Action.OPEN_URL, PluginData.PLUGIN_PAGE));
            builder.append("\n");

            player.spigot().sendMessage(builder.create());
        }));
        setButton(new Button(asSlot(2, 2), () -> new ItemBuilder(SkullIndex.MESSAGE)
                .setName("&eMessages Settings")
                .addDescription(DeathMoneyFiles.getMessages().getString("Gui.main.messages-description"))
                .addSwitchView("&7Currently:", messEnabled)
                .addAction(DeathMoneyFiles.getMessages().getString("Gui.common.edit"))
                .build()
        ).onClick(player -> {
            MANAGER.getMenu(Menus.SETTINGS_MESSAGES).open(player);
        }));
        setButton(new Button(asSlot(4, 4), () -> new ItemBuilder(SkullIndex.CLOSE)
                .setName("&cClose")
                .build()
        ).onClick(player -> {
            player.closeInventory();
        }));
        for (int y = 0; y < 9; y++) {
            if(y == 4)
                continue;
            setButton(new Button(asSlot(0, y), () -> BORDER
            ));
            setButton(new Button(asSlot(4, y), () -> BORDER
            ));
        }
    }
}
