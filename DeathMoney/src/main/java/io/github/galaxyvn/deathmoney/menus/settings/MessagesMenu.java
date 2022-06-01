package io.github.galaxyvn.deathmoney.menus.settings;

import io.github.galaxyvn.deathmoney.DeathMoney;
import io.github.galaxyvn.deathmoney.DeathMoneyFiles;
import io.github.galaxyvn.deathmoney.core.item.ItemBuilder;
import io.github.galaxyvn.deathmoney.core.item.SkullIndex;
import io.github.galaxyvn.deathmoney.libary.xseries.XMaterial;
import io.github.galaxyvn.deathmoney.menus.Button;
import io.github.galaxyvn.deathmoney.menus.GlobalMenu;
import io.github.galaxyvn.deathmoney.menus.MenuManager;
import io.github.galaxyvn.deathmoney.menus.Menus;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagesMenu extends GlobalMenu {

    private DeathMoney plugin;
    private MenuManager menuManager;

    protected static final ItemStack BORDER = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).setName("&r").build();

    private int page;

    public MessagesMenu(DeathMoney plugin, MenuManager menuManager) {
        super(Menus.MESSAGES, 6);
        this.plugin = plugin;
        this.menuManager = menuManager;
    }

    @Override
    public void updateConfig(ConfigurationSection section) {
        build();
    }

    @Override
    public String getTitle() {
        return DeathMoneyFiles.getMessages().getString("Gui.messages.messages.title");
    }

    @Override
    public void build() {
        // Back button
        addBackButton(menuManager, Menus.SETTINGS_MESSAGES);
        // Previous button
        if (page > 0) {
            setButton(new Button(asSlot(0, 3), () -> new ItemBuilder(SkullIndex.PREVIOUS)
                    .setName("&7Previous Page")
                    .build()
            ).onClick(player -> {
                page--;
                build();
            }));
        } else {
            setButton(new Button(asSlot(0, 6), () -> BORDER));
        }
        // Next button
        if (page < DeathMoneyFiles.getMessages().getStringList("").size() / 45) {
            setButton(new Button(asSlot(0, 6), () -> new ItemBuilder(SkullIndex.NEXT)
                    .setName("&7Next Page")
                    .build()
            ).onClick(player -> {
                page++;
                build();
            }));
        } else {
            setButton(new Button(asSlot(0, 6), () -> BORDER));
        }


        // Load all messages into menu.
        List<String> messages = DeathMoneyFiles.getMessages().getStringList("Death.messages.content");
        for (int i = 0; i < 45; i++) {
            int index = page * 45 + i;
            if (index < 0) index = 0;

            if (messages.size() <= index) {
                clearSlot(1 + (i / 9), i % 9);
                continue;
            }

            String message = messages.get(index);
            setButton(new Button(asSlot(1 +  (i / 9), i % 9),() -> new ItemBuilder(XMaterial.PAPER.parseMaterial())
                    .setName(message)
                    .build()
            ));
        }

        for (int y = 1; y < 9; y++) {
            setButton(new Button(asSlot(0, y), () -> BORDER));
        }
    }
}
