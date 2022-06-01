package io.github.galaxyvn.deathmoney.menus;

import io.github.galaxyvn.deathmoney.DeathMoneyFiles;
import io.github.galaxyvn.deathmoney.PluginData;
import io.github.galaxyvn.deathmoney.core.item.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SimpleButton extends Button {

    public SimpleButton(int slot, Material material) {
        super(slot, new ItemStack(material));
    }

//    public SimpleButton setLangEngine(LanguageEngine engine) {
//        lang = engine;
//        return this;
//    }

    public SimpleButton context(String context) {
        title(context + ".title");
        description(context + ".description");
        return this;
    }

    public SimpleButton title(String index) {
        ItemBuilder i = new ItemBuilder(stack);
        i.setName(DeathMoneyFiles.getMessages().getString(index), PluginData.Colors.PRIMARY);
        stack = i.build();
        return this;
    }

    public SimpleButton description(String index) {
        ItemBuilder i = new ItemBuilder(stack);
        i.addDescription(DeathMoneyFiles.getMessages().getString(index), PluginData.Colors.MESSAGE);
        stack = i.build();
        return this;
    }

    public SimpleButton addSwitchView(String prefix, boolean toggle) {
        ItemBuilder i = new ItemBuilder(stack);
        i.addSwitchView(PluginData.Colors.MESSAGE + prefix, toggle);
        stack = i.build();
        return this;
    }

    public SimpleButton extra(String index, ChatColor color) {
        ItemBuilder i = new ItemBuilder(stack);
        i.addLore(color + DeathMoneyFiles.getMessages().getString(index));
        stack = i.build();
        return this;
    }

    public SimpleButton common(Common c) {
        return extra(c.index, c.color);
    }
}
