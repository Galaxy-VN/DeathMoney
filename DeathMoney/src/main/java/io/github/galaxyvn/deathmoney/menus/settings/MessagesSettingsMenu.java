package io.github.galaxyvn.deathmoney.menus.settings;

import io.github.galaxyvn.deathmoney.DeathMoney;
import io.github.galaxyvn.deathmoney.DeathMoneyFiles;
import io.github.galaxyvn.deathmoney.config.Configurable;
import io.github.galaxyvn.deathmoney.config.Configuration;
import io.github.galaxyvn.deathmoney.core.item.ItemBuilder;
import io.github.galaxyvn.deathmoney.libary.xseries.XMaterial;
import io.github.galaxyvn.deathmoney.menus.Button;
import io.github.galaxyvn.deathmoney.menus.GlobalMenu;
import io.github.galaxyvn.deathmoney.menus.MenuManager;
import io.github.galaxyvn.deathmoney.menus.Menus;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

@Configurable.Path("global.money")
public class MessagesSettingsMenu extends GlobalMenu {

    private MenuManager menuManager;
    private Configuration CONFIG;

    private boolean enabled;

    public MessagesSettingsMenu(DeathMoney plugin, MenuManager menuManager) {
        super(Menus.SETTINGS_MESSAGES, 5);
        this.CONFIG = plugin.getRootConfig();
        this.menuManager = menuManager;
    }

    @Override
    public void updateConfig(ConfigurationSection section) {
        enabled = section.getBoolean("message-enabled");
    }

    @Override
    public String getTitle() {
        return DeathMoneyFiles.getMessages().getString("Gui.messages.title");
    }

    @Override
    public void build() {
        // Back button
        addBackButton(menuManager, Menus.SETTINGS);

        setButton(new Button(asSlot(1, 2), () -> new ItemBuilder(XMaterial.REDSTONE_TORCH.parseMaterial())
                .setName(DeathMoneyFiles.getMessages().getString("Gui.messages.enabled.title"))
                .addDescription(DeathMoneyFiles.getMessages().getString("Gui.messages.enabled.description"))
                .addSwitch("&7Currently:", enabled)
                .build()).setToggle(DeathMoneyFiles.getMessages().getString("Gui.messages.enabled.title"),
                        () -> enabled)
                .onClick(player -> {
                    CONFIG.toggle("global.money.message-enabled");
                    CONFIG.save().reload();
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                }));
        
        setButton(new Button(asSlot(1, 4), () -> new ItemBuilder(XMaterial.PAPER.parseMaterial())
                .setName(DeathMoneyFiles.getMessages().getString("Gui.messages.messages.title"))
                .addDescription(DeathMoneyFiles.getMessages().getString("Gui.messages.messages.description"))
                .addAction(DeathMoneyFiles.getMessages().getString("Gui.common.edit"))
                .build()
        ).onClick(player -> menuManager.getMenu(Menus.MESSAGES).open(player)));
    }
}
